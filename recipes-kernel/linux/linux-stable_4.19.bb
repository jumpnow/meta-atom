# require recipes-kernel/linux/linux-yocto.inc
require linux-stable.inc

COMPATIBLE_MACHINE = "atom32|genericx86|s10e"

LINUX_VERSION = "4.19"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-${LINUX_VERSION}:"

S = "${WORKDIR}/git"

PV = "4.19.74"
SRCREV = "dbc29aff8d04f134553326a0c533a442a1774041"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-${LINUX_VERSION}.y \
    file://defconfig \
"
