!/bin/sh -e
#
# Copyright (C) 2008-2011 Intel
#
# install.sh [device_name] [rootfs_name] [video_mode] [vga_mode]
#

PATH=/sbin:/bin:/usr/sbin:/usr/bin

fstype=ext4

device=/dev/sda

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
echo "disk_size:       ${disk_size}"

bios=${device}1
echo "bios:        ${bios}"
grub=${device}2
echo "grub:        ${grub}"
roota=${device}3
echo "rootfs (A):  ${roota}"
rootb=${device}4
echo "rootfs (B):  ${rootb}"
data=${device}5
echo "data:        ${data}"

echo "disk_size:   ${disk_size}"

bios_size=2
echo "bios_size:   ${bios_size}"

grub_size=32
echo "grub_size:   ${grub_size}"

rootfs_size=2048
echo "rootfs_size: ${rootfs_size}"

grub_start=$((bios_size))
echo "grub_start:  ${grub_start}"
grub_end=$((grub_start + grub_size))
echo "grub_end:    ${grub_end}"

roota_start=$((grub_end))
echo "roota_start: ${roota_start}"
roota_end=$((roota_start + rootfs_size))
echo "roota_end:   ${roota_end}"

rootb_start=$((roota_start + rootfs_size))
echo "rootb_start: ${rootb_start}"
rootb_end=$((rootb_start + rootfs_size))
echo "rootb_end:   ${rootb_end}"

data_start=$((rootb_end))
echo "data_start:  ${data_start}"
data_end=$disk_size
echo "data_end:    ${data_end}"

echo "Proceed with installation to ${device}"
read answer

case "${answer}" in
    "Nn*" )
        exit 1
        ;;
esac

echo "Deleting partition table on ${device} ..."
dd if=/dev/zero of=${device} bs=512 count=64

echo "Creating new partition table on ${device} ..."
parted ${device} mklabel gpt

echo "Creating BIOS boot partition on $bios"
parted ${device} mkpart bios_boot 0% $bios_size
parted ${device} set 1 bios_grub on

echo "Creating boot partition on $grub"
parted ${device} mkpart boot $fstype $grub_start $grub_end

echo "Creating rootfs A partition on $roota"
parted ${device} mkpart roota $fstype $roota_start $roota_end

echo "Creating rootfs B partition on $rootb"
parted ${device} mkpart rootb $fstype $rootb_start $rootb_end

echo "Creating data partition on $data"
parted ${device} mkpart data $fstype $data_start $data_end

parted ${device} print

echo "Formatting $grub to ${fstype}..."
mkfs.${fstype} $grub

echo "Formatting $roota to ${fstype}..."
mkfs.${fstype} $roota

echo "Formatting $rootb to ${fstype}..."
mkfs.${fstype} $rootb

echo "Formatting $data to ${fstype}..."
mkfs.${fstype} $data

mkdir /tgt_root
mkdir /src_root
mkdir -p /boot

# Handling of the target root partition
echo "Mounting $roota at /tgt_root"
mount -t ${fstype} $roota /tgt_root

# mount -o rw,loop,noatime,nodiratime /run/media/sdb/rootfs.img at /src_root
# gives warnings
echo "Mounting /run/media/$1/$2 at /src_root"
mount -o rw,loop,noatime,nodiratime /run/media/$1/$2 /src_root

echo "Copying rootfs files..."
cp -a /src_root/* /tgt_root

if [ -d /tgt_root/etc/ ] ; then
    echo "$grub              /boot            ${fstype}       defaults              1  2" >> /tgt_root/etc/fstab

    # We dont want udev to mount our root device while we're booting...
    if [ -d /tgt_root/etc/udev/ ] ; then
        echo "${device}" >> /tgt_root/etc/udev/mount.blacklist
    fi
fi

umount /tgt_root
umount /src_root

# Handling of the target boot partition
# gives the same warnings
mount $grub /boot
echo "Preparing grub boot partition..."
if [ -f /etc/grub.d/00_header ] ; then
    echo "Preparing custom grub2 menu..."
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
