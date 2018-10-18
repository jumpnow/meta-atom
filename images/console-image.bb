SUMMARY = "A console development image with some C/C++ dev tools"
HOMEPAGE = "http://www.jumpnowtek.com"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit image

SPLASH = "psplash-jumpnow"

CORE_OS = " \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    psplash psplash-jumpnow \
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
    elfutils \
    file \
    g++ \
    g++-symlinks \
    gcc \
    gcc-symlinks \
    gdb \
    gettext \
    git \
    ldd \
    libstdc++ \
    libstdc++-dev \
    libtool \
    ltrace \
    make \
    nasm \
    perl-modules \
    pkgconfig \
    python-modules \
    python3-modules \
    strace \
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
    curl \
    dosfstools \
    e2fsprogs-mke2fs \
    e2fsprogs-tune2fs \
    ethtool \
    findutils \
    i2c-tools \
    iperf3 \
    iptables \
    less \
    netcat \
    parted \
    pciutils \
    procps \
    root-upgrader \
    sysfsutils \
    tcpdump \
    unzip \
    util-linux \
    util-linux-blkid \
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

add_opt_dir() {
    mkdir -p ${IMAGE_ROOTFS}/opt
}

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    disable_bootlogd ; \
    add_opt_dir; \
"

export IMAGE_BASENAME = "console-image"

