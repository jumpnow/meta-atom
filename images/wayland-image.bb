SUMMARY = "A basic weston/wayland dev image"
HOMEPAGE = "http://www.jumpnowtek.com"

require console-image.bb

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
    ${WESTON} \
"

export IMAGE_BASENAME = "wayland-image"
