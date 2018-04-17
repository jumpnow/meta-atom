SUMMARY = "Syntro testing"
HOMEPAGE = "http://www.jumpnowtek.com"
LICENSE = "MIT"

require x11-qt5-image.bb

SYNTRO = " \
    syntrocore \
    syntrolcam \
    syntrolcam-init \
"

IMAGE_INSTALL += " \
    ${SYNTRO} \
"

export IMAGE_BASENAME = "x11-syntro-image"
