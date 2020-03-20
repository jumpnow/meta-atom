SUMMARY = "Start a gui at boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://init"

S = "${WORKDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "gui-init"
INITSCRIPT_PARAMS = "start 95 5 ."

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 init ${D}${sysconfdir}/init.d/gui-init
}

FILES_${PN} = "${sysconfdir}"

# currently the gui we want
RDEPENDS_${PN} = "qfontchooser-tools"
