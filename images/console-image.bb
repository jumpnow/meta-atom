SUMMARY = "A console development image with some C/C++ dev tools"
HOMEPAGE = "http://www.jumpnowtek.com"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit image

PACKAGE_EXCLUDE = " rng-tools"

SPLASH = "psplash-jumpnow"

CORE_OS = " \
    grub grub-editenv \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    psplash psplash-jumpnow \
    tzdata \
"

KERNEL_EXTRA = " \
    cryptodev-module \
    kernel-modules \
    linux-firmware \
    linux-firmware-b43 \
    load-modules \
"

WIREGUARD = " \
    wireguard-init \
    wireguard-module \
    wireguard-tools \
"

WIFI_TOOLS = " \
    crda \
    iw \
    wpa-supplicant \
"

DEV_SDK = " \
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
    python3-modules \
    strace \
"

EXTRA_TOOLS = " \
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

SECURITY_TOOLS = " \
    checksec \
    ncrack \
    nikto \
    python3-scapy \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_SDK} \
    ${EXTRA_TOOLS} \
    ${KERNEL_EXTRA} \
    ${SECURITY_TOOLS} \
    ${WIFI_TOOLS} \
    ${WIREGUARD} \
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

