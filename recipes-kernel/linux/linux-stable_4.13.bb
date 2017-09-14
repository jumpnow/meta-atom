require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "atom32|genericx86|genericx86-64|s10e"

LINUX_VERSION = "4.13"
LINUX_VERSION_EXTENSION = "-jumpnow"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-4.13:"

S = "${WORKDIR}/git"

PR = "r1"

PV = "4.13.2"
SRCREV = "07dd6cc1fff160143e82cf5df78c1db0b6e03355"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-4.13.y \
    file://defconfig \
"
