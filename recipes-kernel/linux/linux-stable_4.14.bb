require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "atom32|genericx86|genericx86-64|s10e"

LINUX_VERSION = "4.14"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-${LINUX_VERSION}:"

S = "${WORKDIR}/git"

PV = "4.14.102"
SRCREV = "5461ace2e2f7b0ebdeca502c743ea819106ecc52"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-${LINUX_VERSION}.y \
    file://defconfig \
"
