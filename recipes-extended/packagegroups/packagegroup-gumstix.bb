SUMMARY = "Build extra packages for the package repository"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=b97a012949927931feb7793eee5ed924"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "packagegroup-gumstix"

RDEPENDS_packagegroup-gumstix = " \
    apache2 \
    boost \
    cronie \
    devmem2 \
    dtc \
    ethtool \
    fb-test \
    git \
    gstreamer1.0-meta-audio \
    gstreamer1.0-meta-base \
    gstreamer1.0-meta-debug \
    gstreamer1.0-meta-video \
    gstreamer1.0-meta-x11-base \
    i2c-tools \
    iperf3 \
    kernel-devsrc \
    lowpan-tools \
    mavlink-socket \
    nano \
    ofono \
    omap3-writeprom \
    opencv \
    openjdk-8 \
    openocd \
    openssh \
    packagegroup-core-eclipse-debug \
    packagegroup-core-sdk \
    packagegroup-core-standalone-sdk-target \
    packagegroup-core-tools-debug \
    packagegroup-core-tools-profile \
    packagegroup-core-tools-testapps \
    php \
    php-cgi \
    php-cli \
    picodlp-control \
    python \
    python-pip \
    python-pyserial \
    python3 \
    python3-pip \
    python3-pyserial \
    robovero-examples \
    robovero-python \
    rtklibexplorer \
    screen \
    spidev-test \
    sshd \
    uart-loop-test \
    vlc \
"
