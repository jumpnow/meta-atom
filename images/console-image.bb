SUMMARY = "A console development image with some C/C++ dev tools"
HOMEPAGE = "http://www.jumpnowtek.com"
LICENSE = "MIT"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit core-image

CORE_OS = " \
    openssh openssh-keygen openssh-sftp-server \
    psplash \
    tzdata \
"

KERNEL_EXTRA_INSTALL = " \
    kernel-modules \
    linux-firmware \
    linux-firmware-b43 \
"

WIFI_SUPPORT = " \
    crda \
    iw \
    wireless-tools \
    wpa-supplicant \
"

DEV_SDK_INSTALL = " \
    binutils \
    binutils-symlinks \
    coreutils \
    cpp \
    cpp-symlinks \
    diffutils \
    file \
    g++ \
    g++-symlinks \
    gcc \
    gcc-symlinks \
    gettext \
    git \
    ldd \
    libstdc++ \
    libstdc++-dev \
    libtool \
    make \
    pkgconfig \
"

DEV_EXTRAS = " \
    grub \
    grub-editenv \
    ntp \
    ntp-tickadj \
"

EXTRA_TOOLS_INSTALL = " \
    acpid \
    bzip2 \
    dosfstools \
    e2fsprogs-mke2fs \
    e2fsprogs-tune2fs \
    ethtool \
    findutils \
    i2c-tools \
    iperf3 \
    iptables \
    less \
    links \
    netcat \
    openvpn \
    openvpn-sample \
    parted \
    pciutils \
    procps \
    root-upgrader \
    sysfsutils \
    tcpdump \
    unzip \
    util-linux \
    util-linux-blkid \
    vim \
    wget \
    zip \
"

SECURITY_TOOLS = " \
    aircrack-ng \
    nikto \
    nmap \
    scapy \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_SDK_INSTALL} \
    ${DEV_EXTRAS} \
    ${EXTRA_TOOLS_INSTALL} \
    ${KERNEL_EXTRA_INSTALL} \
    ${SECURITY_TOOLS} \
    ${WIFI_SUPPORT} \
"

set_local_timezone() {
    ln -sf /usr/share/zoneinfo/EST5EDT ${IMAGE_ROOTFS}/etc/localtime
}

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    disable_bootlogd ; \
 "

export IMAGE_BASENAME = "console-image"

