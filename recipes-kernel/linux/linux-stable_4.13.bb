require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "atom32|genericx86|genericx86-64|s10e"

LINUX_VERSION = "4.13"
LINUX_VERSION_EXTENSION = "-jumpnow"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-4.13:"

S = "${WORKDIR}/git"

PR = "r3"

PV = "4.13.4"
SRCREV = "6eb9c0fc1bca163dd084da77d77bb11c4b1639bc"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-4.13.y \
    file://defconfig \
"
