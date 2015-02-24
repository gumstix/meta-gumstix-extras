SUMMARY = "Build extra packages for the package repository"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "packagegroup-gumstix"

RDEPENDS_packagegroup-gumstix = " \
    ${ROS_INSTALL} \
    bluez-hcidump \
    boost \
    devmem2 \
    git \
    i2c-tools \
    mavlink-socket \
    nano \
    opencv \
    omap3-writeprom \
    packagegroup-core-qt-demoapps \
    packagegroup-core-qt4e \
    packagegroup-core-sdk \
    packagegroup-core-standalone-sdk-target \
    packagegroup-core-tools-debug \
    packagegroup-core-tools-testapps \
    picodlp-control \
    python \
    python-pip \
    python-pyserial \
    robovero-examples \
    robovero-python \
    screen \
    spidev-test \
    sshd \
    uart-loop-test \
"

RDEPENDS_packagegroup-gumstix_append_overo = " \
    omap3-isp-live \
"

ROS_INSTALL = " \
    packagegroup-ros-comm \
    python-wstool \
    python-email \
    python-distutils \
    git \
    git-perltools \
    python-rosinstall \
    rospy-tutorials \
    roscpp-tutorials \
"

