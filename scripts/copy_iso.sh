#!/bin/bash

MACHINE=s10e

if [ "x${1}" = "x" ]; then
    echo "Usage: ${0} <block device> [ <image-name> ]\n"
    exit 0
fi

if [ "x${2}" = "x" ]; then
    IMAGE=console
else
    IMAGE=${2}
fi

if [ -z "$OETMP" ]; then
    echo "Working from local directory"
    SRCDIR=.
else
    echo "OETMP: $OETMP"

    if [ ! -d ${OETMP}/deploy/images/${MACHINE} ]; then
        echo "Directory not found: ${OETMP}/deploy/images/${MACHINE}"
        exit 1
    fi

    SRCDIR=${OETMP}/deploy/images/${MACHINE}
fi 

if [ -z "${MACHINE}" ]; then
    echo "Environment variable MACHINE not set"
    echo "Example: export MACHINE=s10e or export MACHINE=atom32"
    exit 1
fi

case "${MACHINE}" in
    "atom32" | "genericx86" | "genericx86-64" | "s10e" )
        echo "MACHINE: ${MACHINE}"
        ;;
    * )
        echo "Unknown MACHINE: ${MACHINE}"
        exit 1
        ;;
esac

echo "IMAGE: ${IMAGE}"

if [ ! -f "${SRCDIR}/${IMAGE}-image-${MACHINE}.iso" ]; then
    echo "File not found: ${SRCDIR}/${IMAGE}-image-${MACHINE}.iso"
    exit 1
fi

if [ -b ${1} ]; then
    DEV=${1}
elif [ -b "/dev/${1}" ]; then
    DEV=/dev/${1}
else
    echo "Block device not found: ${1} or /dev/${1}"
    exit 1
fi

if [ "${DEV}" == "/dev/sda" ]; then
    echo "Not going to overwrite ${DEV}"
    exit 1
fi

echo "Using dd to copy image ${IMAGE}-image-${MACHINE}.iso to ${DEV}"
sudo dd if=${SRCDIR}/${IMAGE}-image-${MACHINE}.iso of=${DEV} bs=1M
sudo sync

echo "Done"
