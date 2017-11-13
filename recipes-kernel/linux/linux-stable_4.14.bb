require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "atom32|genericx86|genericx86-64|s10e"

LINUX_VERSION = "4.14"
LINUX_VERSION_EXTENSION = "-jumpnow"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-4.14:"

S = "${WORKDIR}/git"

PR = "r0"

PV = "4.14"
SRCREV = "bebc6082da0a9f5d47a1ea2edc099bf671058bd4"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-4.14.y \
    file://defconfig \
"
