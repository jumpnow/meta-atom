SUMMARY = "A basic weston/wayland dev image"
HOMEPAGE = "http://www.jumpnowtek.com"

require console-image.bb

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
    weston-config \
    weston-dev \
    weston-examples \
    weston-init \
"

IMAGE_INSTALL += " \
    ${SDL2} \
    ${WESTON} \
"

export IMAGE_BASENAME = "wayland-image"
