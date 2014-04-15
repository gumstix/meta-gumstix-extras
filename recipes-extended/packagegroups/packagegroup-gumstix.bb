SUMMARY = "Build extra packages for the package repository"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

inherit packagegroup

PACKAGES = "packagegroup-gumstix"

RDEPENDS_packagegroup-gumstix = " \
    ${ROS_INSTALL} \
    sshd \
    git \
    nano \
    i2c-tools \
    python \
    devmem2 \
    opencv \
    boost \
    packagegroup-core-tools-debug \
    packagegroup-core-tools-profile \
    packagegroup-core-tools-testapps \
    packagegroup-core-sdk \
    packagegroup-core-standalone-sdk-target \
    packagegroup-core-qt-demoapps \
"

ROS_INSTALL = " \
    packagegroup-ros-comm \
    python-wstool \
    python-email \
    python-distutils \
    git \
    python-rosinstall \
    rospy-tutorials \
    roscpp-tutorials \
"

