SUMMARY = "Script to support an A/B rootfs upgrade on x86 systems"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://upgrade \
           file://switch \
          "

PR = "r3"

S = "${WORKDIR}"

do_install_append () {
    install -d ${D}${bindir}
    install -m 0755 upgrade ${D}${bindir}/upgrade
    install -m 0755 switch ${D}${bindir}/switch
}

FILES_${PN} = "${bindir}"

RDEPENDS_${PN} = "coreutils e2fsprogs-mke2fs grub-editenv util-linux"
