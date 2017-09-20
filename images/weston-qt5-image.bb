SUMMARY = "A basic weston/wayland qt5 dev image"
HOMEPAGE = "http://www.jumpnowtek.com"

require qt5-image.bb

SDL2 = " \
    libsdl2 \
    libsdl2-dev \
    libsdl2-image \
    libsdl2-image-dev \
    sdl2-env \
"

WESTON = " \
    libweston-2 \
    weston \
    weston-dev \
    weston-examples \
    weston-init \
"

IMAGE_INSTALL += " \
    qtwayland \
    ${SDL2} \
    ${WESTON} \
"

export IMAGE_BASENAME = "weston-qt5-image"
