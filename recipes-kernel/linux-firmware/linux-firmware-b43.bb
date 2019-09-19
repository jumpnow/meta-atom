SUMMARY = "Wifi firmware for the b43 driver and the Broadcom chip in the Lenovo s10e Ideapad"

# The source for these files was the github.com/OpenELEC/wlan-firmware repo
LICENSE = "CLOSED"

SRC_URI = "file://lp0bsinitvals15.fw \
           file://lp0initvals15.fw \
           file://ucode15.fw \
          "

S = "${WORKDIR}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/b43
    install -m 644 lp0bsinitvals15.fw ${D}${nonarch_base_libdir}/firmware/b43
    install -m 644 lp0initvals15.fw ${D}${nonarch_base_libdir}/firmware/b43
    install -m 644 ucode15.fw ${D}${nonarch_base_libdir}/firmware/b43
}

FILES_${PN} = "${nonarch_base_libdir}"
