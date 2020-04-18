SUMMARY = "A custom console development image with some C/C++ dev tools"
HOMEPAGE = "http://www.jumpnowtek.com"

require images/basic-dev-image.bb

SPLASH = "psplash-jumpnow"

WIFI = " \
    crda \
    iw \
    linux-firmware-b43 \
    wpa-supplicant \
"

IMAGE_INSTALL += " \
    grub grub-editenv \
    psplash psplash-jumpnow \
    root-upgrader \
    ${WIFI} \
    ${SECURITY_TOOLS} \
    ${WIREGUARD} \
"

export IMAGE_BASENAME = "console-image"
