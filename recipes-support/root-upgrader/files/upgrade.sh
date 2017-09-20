#!/bin/sh

prompt_to_continue()
{
    partition=${1}
    answer_default="N"
    echo -n "Proceed with upgrade onto partition ${partition} (y/N): "
    read answer
    answer="${answer:-$answer_default}"

    case "${answer}" in
        [Yy]*)
            ;;
        *)
            echo "Aborting upgrade"
            exit 1
            ;;
    esac
}

if [ "x${1}" = "x" ]; then
    echo "Usage: ${0} <path/to/some-img>.tar.xz"
    exit 1
fi

if [ ! -f ${1} ]; then
    echo "Usage: ${0} <path/to/some-img>.tar.xz"
    exit 1
fi

image=${1}

current_root=`findmnt -n -o SOURCE /`

echo "Current root: ${current_root}"

if [ "${current_root}" = "/dev/sda3" ]; then
    upgrade_root="/dev/sda4"
    root_name="rootb"
elif [ "${current_root}" = "/dev/sda4" ]; then
    upgrade_root="/dev/sda3"
    root_name="roota"
else
    echo "Root partition is unexpected. Aborting."
    exit 1
fi

echo "Image: ${image}"
echo "Upgrade root: ${upgrade_root}"
echo "New grub default: ${grub_default}"     
echo "Root part name: ${root_name}"

prompt_to_continue ${upgrade_root}

echo "Formatting ${upgrade_root} as ext4"

mkfs.ext4 -q -F -L ${root_name} ${upgrade_root}

if [ $? -ne 0 ]; then
    echo "Error formatting the new root partition"
    exit 1
fi

echo "Mounting ${upgrade_root} at /mnt"

mount ${upgrade_root} /mnt

if [ $? -ne 0 ]; then
    echo "Error mounting new root partition"
    exit 1
fi

echo "Extracting new root filesystem ${image} to /mnt"

tar -C /mnt -xJf ${image}

if [ $? -ne 0 ]; then
    echo "Error extracting new filesystem"
    umount ${upgrade_root}
    exit 1
fi

echo "Checking for new kernel"

if [ ! -f /mnt/boot/bzImage ]; then
    echo "Failed to find /mnt/boot/bzImage"
    umount ${upgrade_root}
    exit 1
fi

echo "Copying /mnt/boot/bzImage to /mnt/vmlinuz"
cp /mnt/boot/bzImage /mnt/vmlinuz

if [ $? -ne 0 ]; then
    echo "Failed to copy new kernel"
    umount ${upgrade_root}
    exit 1
fi

echo "Copying /etc/fstab to new root"
cp /etc/fstab /mnt/etc/fstab

echo "Copying ssh keys to new root"
cp /etc/ssh/*key* /mnt/etc/ssh/

echo "Copying hostname to new root"
cp /etc/hostname /mnt/etc/hostname

echo "Copying /etc/network/interfaces to new root"
cp /etc/network/interfaces /mnt/etc/network/interfaces

if [ -f /etc/wpa_supplicant.conf ]; then
    echo "Copying /etc/wpa_supplicant.conf to new root"
    cp /etc/wpa_supplicant.conf /mnt/etc/wpa_supplicant.conf
fi

umount ${upgrade_root}

if [ -f /boot/grub/grub.cfg ]; then
    echo "Updating /boot/grub/grub.cfg default"

    if [ "${root_name}" = "roota" ]; then
        sed -i "s/^set default.*/set default=\"0\"/" /boot/grub/grub.cfg
    else
        sed -i "s/^set default.*/set default=\"1\"/" /boot/grub/grub.cfg
    fi
else
    echo "Failed to find /boot/grub/grub.cfg"
    echo "Grub default was not changed"
    echo "Select new system manually at grub prompt"
fi

echo "Reboot to use the new system"
