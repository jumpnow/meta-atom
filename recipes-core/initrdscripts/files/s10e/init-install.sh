#!/bin/sh -e
#
# Copyright (C) 2008-2011 Intel
#
# install.sh [device_name] [rootfs_name] [video_mode] [vga_mode]
#

PATH=/sbin:/bin:/usr/sbin:/usr/bin

FSTYPE=ext4

# We need 20 Mb for the boot partition
boot_size=20

# Get a list of hard drives
hdnamelist=""
live_dev_name=`cat /proc/mounts | grep ${1%/} | awk '{print $1}'`
live_dev_name=${live_dev_name#\/dev/}
# Only strip the digit identifier if the device is not an mmc
case $live_dev_name in
    mmcblk*)
    ;;
    nvme*)
    ;;
    *)
        live_dev_name=${live_dev_name%%[0-9]*}
    ;;
esac

echo "Searching for hard drives ..."

# Some eMMC devices have special sub devices such as mmcblk0boot0 etc
# we're currently only interested in the root device so pick them wisely
devices=`ls /sys/block/ | grep -v mmcblk` || true
mmc_devices=`ls /sys/block/ | grep "mmcblk[0-9]\{1,\}$"` || true
devices="$devices $mmc_devices"

for device in $devices; do
    case $device in
        loop*)
            # skip loop device
            ;;
        sr*)
            # skip CDROM device
            ;;
        ram*)
            # skip ram device
            ;;
        *)
            # skip the device LiveOS is on
            # Add valid hard drive name to the list
            case $device in
                $live_dev_name*)
                # skip the device we are running from
                ;;
                *)
                    hdnamelist="$hdnamelist $device"
                ;;
            esac
            ;;
    esac
done

TARGET_DEVICE_NAME=""
for hdname in $hdnamelist; do
    # Display found hard drives and their basic info
    echo "-------------------------------"
    echo /dev/$hdname
    if [ -r /sys/block/$hdname/device/vendor ]; then
        echo -n "VENDOR="
        cat /sys/block/$hdname/device/vendor
    fi
    if [ -r /sys/block/$hdname/device/model ]; then
        echo -n "MODEL="
        cat /sys/block/$hdname/device/model
    fi
    if [ -r /sys/block/$hdname/device/uevent ]; then
        echo -n "UEVENT="
        cat /sys/block/$hdname/device/uevent
    fi
    echo
done

# Get user choice
while true; do
    echo "Please select an install target or press n to exit ($hdnamelist ): "
    read answer
    if [ "$answer" = "n" ]; then
        echo "Installation manually aborted."
        exit 1
    fi
    for hdname in $hdnamelist; do
        if [ "$answer" = "$hdname" ]; then
            TARGET_DEVICE_NAME=$answer
            break
        fi
    done
    if [ -n "$TARGET_DEVICE_NAME" ]; then
        break
    fi
done

if [ -n "$TARGET_DEVICE_NAME" ]; then
    echo "Installing image on /dev/$TARGET_DEVICE_NAME ..."
else
    echo "No hard drive selected. Installation aborted."
    exit 1
fi

device=/dev/$TARGET_DEVICE_NAME

#
# The udev automounter can cause pain here, kill it
#
rm -f /etc/udev/rules.d/automount.rules
rm -f /etc/udev/scripts/mount*

#
# Unmount anything the automounter had mounted
#
umount ${device}* 2> /dev/null || /bin/true

if [ ! -b /dev/loop0 ] ; then
    mknod /dev/loop0 b 7 0
fi

mkdir -p /tmp
if [ ! -L /etc/mtab ] && [ -e /proc/mounts ]; then
    ln -sf /proc/mounts /etc/mtab
fi

disk_size=$(parted ${device} unit mb print | grep '^Disk .*: .*MB' | cut -d" " -f 3 | sed -e "s/MB//")

# For GRUB 2 we need separate parition to store stage2 grub image
# 2Mb value is chosen to align partition for best performance.
bios_boot_size=2

rootfs_size=$((disk_size-bios_boot_size-boot_size))

boot_start=$((bios_boot_size))
rootfs_start=$((bios_boot_size+boot_size))
rootfs_end=$((rootfs_start+rootfs_size))

# MMC devices are special in a couple of ways
# 1) they use a partition prefix character 'p'
# 2) they are detected asynchronously (need rootwait)
rootwait=""
part_prefix=""
if [ ! "${device#/dev/mmcblk}" = "${device}" ] || \
   [ ! "${device#/dev/nvme}" = "${device}" ]; then
    part_prefix="p"
    rootwait="rootwait"
fi

# USB devices also require rootwait
if [ -n `readlink /dev/disk/by-id/usb* | grep $TARGET_DEVICE_NAME` ]; then
    rootwait="rootwait"
fi

bios_boot=${device}${part_prefix}1
bootfs=${device}${part_prefix}2
rootfs=${device}${part_prefix}3

echo "*****************"
echo "BIOS boot partition size: $bios_boot_size MB ($bios_boot)"
echo "Boot partition size:   $boot_size MB ($bootfs)"
echo "Rootfs partition size: $rootfs_size MB ($rootfs)"
echo "*****************"
echo "Deleting partition table on ${device} ..."
dd if=/dev/zero of=${device} bs=512 count=35

echo "Creating new partition table on ${device} ..."
parted ${device} mktable gpt

echo "Creating BIOS boot partition on $bios_boot"
parted ${device} mkpart bios_boot 0% $bios_boot_size
parted ${device} set 1 bios_grub on

echo "Creating boot partition on $bootfs"
parted ${device} mkpart boot ${FSTYPE} $boot_start $boot_size

echo "Creating rootfs partition on $rootfs"
parted ${device} mkpart root ${FSTYPE} $rootfs_start $rootfs_end

parted ${device} print

echo "Formatting $bootfs to ${FSTYPE}..."
mkfs.${FSTYPE} $bootfs

echo "Formatting $rootfs to ${FSTYPE}..."
mkfs.${FSTYPE} $rootfs

mkdir /tgt_root
mkdir /src_root
mkdir -p /boot

# Handling of the target root partition
echo "Mounting $rootfs at /tgt_root"
mount -t ${FSTYPE} $rootfs /tgt_root

echo "Mounting /run/media/$1/$2 at /src_root"
mount -o rw,loop,noatime,nodiratime /run/media/$1/$2 /src_root

echo "Copying rootfs files..."
cp -a /src_root/* /tgt_root
if [ -d /tgt_root/etc/ ] ; then
    bootdev=${bootfs}
    echo "$bootdev              /boot            ${FSTYPE}       defaults              1  2" >> /tgt_root/etc/fstab
    # We dont want udev to mount our root device while we're booting...
    if [ -d /tgt_root/etc/udev/ ] ; then
        echo "${device}" >> /tgt_root/etc/udev/mount.blacklist
    fi
fi
umount /tgt_root
umount /src_root

# Handling of the target boot partition
mount $bootfs /boot
echo "Preparing boot partition..."
if [ -f /etc/grub.d/00_header ] ; then
    echo "Preparing custom grub2 menu..."
    root_part_uuid=$(blkid -o value -s PARTUUID ${rootfs})
    boot_uuid=$(blkid -o value -s UUID ${bootfs})
    GRUBCFG="/boot/grub/grub.cfg"
    mkdir -p $(dirname $GRUBCFG)
    cat >$GRUBCFG <<_EOF
set default="0"
set timeout="5"

menuentry "Linux" {
    set root=(hd0,2)
    linux /vmlinuz consoleblank=0 root=/dev/sda3 rootwait rw
}
_EOF
    chmod 0444 $GRUBCFG
fi
grub-install ${device}

cp /run/media/$1/vmlinuz /boot/

umount /boot

sync

echo "Remove your installation media, and press ENTER"

read enter

echo "Rebooting..."
reboot -f
