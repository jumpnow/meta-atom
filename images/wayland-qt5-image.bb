SUMMARY = "A basic weston/wayland qt5 dev image"
HOMEPAGE = "http://www.jumpnowtek.com"

require qt5-image.bb

ALSA += " \
    libasound \
    libavcodec \
    libavdevice \
    libavfilter \
    libavformat \
    libavutil \
    libpostproc \
    libswresample \
    libswscale \
    alsa-conf \
    alsa-utils \
    alsa-utils-scripts \
"

WESTON = " \
    libweston-2 \
    weston \
    weston-config \
    weston-dev \
    weston-examples \
    weston-init \
"

IMAGE_INSTALL += " \
    ${ALSA} \
    qtwayland \
    ${WESTON} \
"

export IMAGE_BASENAME = "wayland-qt5-image"
