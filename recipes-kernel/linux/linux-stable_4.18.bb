# require recipes-kernel/linux/linux-yocto.inc
require linux-stable.inc

COMPATIBLE_MACHINE = "atom32|genericx86|s10e"

LINUX_VERSION = "4.18"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-${LINUX_VERSION}:"

S = "${WORKDIR}/git"

PV = "4.18.11"
SRCREV = "2f411a0873a9daa20ae16cf5879d11bbea267582"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-${LINUX_VERSION}.y \
    file://defconfig \
"
