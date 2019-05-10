SUMMARY = "A console development image with some C/C++ dev tools"
HOMEPAGE = "http://www.jumpnowtek.com"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit image

SPLASH = "psplash-jumpnow"

CORE_OS = " \
    grub grub-editenv \
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
    wpa-supplicant \
"

DEV_SDK_INSTALL = " \
    binutils \
    binutils-symlinks \
    coreutils \
    cpp \
    cpp-symlinks \
    diffutils \
    elfutils elfutils-binutils \
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

EXTRA_TOOLS_INSTALL = " \
    acpid \
    bzip2 \
    curl \
    dosfstools \
    e2fsprogs-mke2fs \
    e2fsprogs-tune2fs \
    ethtool \
    findutils \
    firewall \
    grep \
    i2c-tools \
    ifupdown \
    iperf3 \
    iptables \
    less \
    lsof \
    netcat-openbsd \
    ntp ntp-tickadj \
    nmap \
    parted \
    pciutils \
    procps \
    rndaddtoentcnt \
    rng-tools \
    root-upgrader \
    sysfsutils \
    tcpdump \
    unzip \
    util-linux \
    util-linux-blkid \
    wget \
    zip \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_SDK_INSTALL} \
    ${EXTRA_TOOLS_INSTALL} \
    ${KERNEL_EXTRA_INSTALL} \
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

