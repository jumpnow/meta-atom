# require recipes-kernel/linux/linux-yocto.inc
require linux-stable.inc

COMPATIBLE_MACHINE = "atom32|genericx86|s10e"

LINUX_VERSION = "4.18"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-${LINUX_VERSION}:"

S = "${WORKDIR}/git"

PV = "4.18.16"
SRCREV = "6b3252287aa248cb49befa5de2a68fed6413c814"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-${LINUX_VERSION}.y \
    file://defconfig \
"
