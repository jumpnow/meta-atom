SUMMARY = "A basic Qt5 qwidgets dev image"
HOMEPAGE = "http://www.jumpnowtek.com"

require console-image.bb

QT_TOOLS = " \
    qtbase \
    qtbase-dev \
    qtbase-mkspecs \
    qtbase-plugins \
    qtbase-tools \
    qt5-env \
"

FONTS = " \
    fontconfig \
    fontconfig-dev \
    fontconfig-utils \
    ttf-bitstream-vera \
"

QT_SERIALPORT = " \
    qtserialport-dev \
    qtserialport-mkspecs \
"

IMAGE_INSTALL += " \
    ${FONTS} \
    ${QT_TOOLS} \
    ${QT_SERIALPORT} \
    tspress \
"

export IMAGE_BASENAME = "qt5-image"
