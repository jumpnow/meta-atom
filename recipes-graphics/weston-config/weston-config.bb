SUMMARY = "Customize the weston desktop"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "file://weston.ini"

PR = "r0"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/xdg/weston
    install -m 0755 weston.ini ${D}${sysconfdir}/xdg/weston
}

FILES_${PN} = "${sysconfdir}"
