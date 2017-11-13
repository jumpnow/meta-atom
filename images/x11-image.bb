SUMMARY = "A basic X11 image"
HOMEPAGE = "http://www.jumpnowtek.com"
LICENSE = "MIT"

require console-image.bb

X11_PACKAGES = " \
    xauth \
    xcursor-transparent-theme \
    xf86-input-evdev \
    xf86-input-keyboard \
    xf86-input-mouse \
    xf86-video-fbdev \
    xhost \
    xinit \
    xinput \
    xkbcomp \
    xkeyboard-config \
    xkeyboard-config-locale-en-gb \
    xmodmap \
    xrandr \
    xserver-nodm-init \
    xserver-xf86-config \
    xserver-xorg \
    xset \
"
#    matchbox-panel-2 

MATCHBOX_PACKAGES = " \
    matchbox-config-gtk \
    matchbox-desktop \
    matchbox-session \
    matchbox-terminal \
    matchbox-wm \
"

BROWSERS = " \
    firefox \
"

X11_EXTRAS = " \
    shutdown-desktop \
    x11vnc \
"

IMAGE_INSTALL += " \
    ${BROWSERS} \
    ${MATCHBOX_PACKAGES} \
    ${X11_EXTRAS} \
    ${X11_PACKAGES} \
"

export IMAGE_BASENAME = "x11-image"
