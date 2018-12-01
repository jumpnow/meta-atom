require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "atom32|genericx86|genericx86-64|s10e"

LINUX_VERSION = "4.14"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-${LINUX_VERSION}:"

S = "${WORKDIR}/git"

PV = "4.14.85"
SRCREV = "5ff1ad556aad473952c1caca6092aac4517ac1ae"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-${LINUX_VERSION}.y \
    file://defconfig \
"
