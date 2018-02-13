require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "atom32|genericx86|genericx86-64|s10e"

LINUX_VERSION = "4.15"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-${LINUX_VERSION}:"

S = "${WORKDIR}/git"

PV = "4.15.3"
SRCREV = "e6e2d12fa46bd5869576c4801ff4d80a4981107d"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-${LINUX_VERSION}.y \
    file://defconfig \
    file://0001-Revert-ssb-Disable-PCI-host-for-PCI_DRIVERS_GENERIC.patch \
    file://0002-Revert-bcma-Fix-allmodconfig-and-BCMA-builds-on-MIPS.patch \
"
