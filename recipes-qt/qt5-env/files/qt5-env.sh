#!/bin/sh

export PATH=${PATH}:/usr/bin/qt5

#export QT_QPA_PLATFORM=linuxfb

# or for a wayland/weston desktop
export QT_QPA_PLATFORM=wayland-egl

if [ -z "${XDG_RUNTIME_DIR}" ]; then
    export XDG_RUNTIME_DIR=/tmp/user/${UID}

    if [ ! -d ${XDG_RUNTIME_DIR} ]; then
        mkdir -p ${XDG_RUNTIME_DIR}
    fi
fi
