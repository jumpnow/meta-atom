SUMMARY = "A basic Qt5 qwidgets dev image"

require console-image.bb

QT_DEV_TOOLS = " \
    qtbase-dev \
    qtbase-mkspecs \
    qtbase-plugins \
    qtbase-tools \
"

QT_TOOLS = " \
    qtbase \
    qt5-env \
"

FONTS = " \
    fontconfig \
    fontconfig-utils \
    cantarell-fonts \
    liberation-fonts \
    ttf-bitstream-vera \
    ttf-hunky-sans \
    ttf-hunky-serif \
    ttf-ubuntu-mono \
    ttf-ubuntu-sans \
"

TEST_APPS += " \
    qfontchooser-tools \
    qeditor-tools \
    tspress-tools \
"

IMAGE_INSTALL += " \
    gui-init \
    ${FONTS} \
    ${QT_DEV_TOOLS} \
    ${QT_TOOLS} \
    ${TEST_APPS} \
"

export IMAGE_BASENAME = "qt5-image"
