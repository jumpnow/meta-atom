# require recipes-kernel/linux/linux-yocto.inc
require linux-stable.inc

COMPATIBLE_MACHINE = "atom32|genericx86|s10e"

LINUX_VERSION = "4.19"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-${LINUX_VERSION}:"

S = "${WORKDIR}/git"

PV = "4.19.32"
SRCREV = "3a2156c839c75c24691e3c672a6d607b24b0c210"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-${LINUX_VERSION}.y \
    file://defconfig \
"
