SUMMARY = "A basic Qt5 qwidgets dev image"
HOMEPAGE = "http://www.jumpnowtek.com"

require console-image.bb

QT_DEV_TOOLS = " \
    qtbase-dev \
    qtbase-mkspecs \
    qtbase-tools \
    qtserialport-dev \
    qtserialport-mkspecs \
"

QT_LIBS = " \
    qtbase \
    qtbase-plugins \
    qtserialport \
    qt5-env \
"

QT_APPS = " \
    tspress \
"

FONTS = " \
    fontconfig \
    fontconfig-dev \
    fontconfig-utils \
    ttf-bitstream-vera \
"

IMAGE_INSTALL += " \
    ${FONTS} \
    ${QT_DEV_TOOLS} \
    ${QT_LIBS} \
    ${QT_APPS} \
"

export IMAGE_BASENAME = "qt5-image"
