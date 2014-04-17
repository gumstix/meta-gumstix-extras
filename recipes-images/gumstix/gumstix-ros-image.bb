DESCRIPTION = "A basic console image for Gumstix boards."
LICENSE = "MIT"
PR = "r1"

IMAGE_FEATURES += "splash package-management ssh-server-openssh"
IMAGE_LINGUAS = "en-us"

inherit core-image

# Gumstix machines individually RDEPEND on the firware they need but we repeat
# it here as we might want to use the same image on multiple different machines.
FIRMWARE_INSTALL = " \
  linux-firmware-sd8686 \
  linux-firmware-sd8787 \
"

TOOLS_INSTALL = " \
  alsa-utils \
  systemd-analyze \
  cpufrequtils \
  grep \
  gzip \
  iputils \
  iw \
  dhcp-client \
  init-ifupdown \
  memtester \
  nano \
  ntp \
  sudo \
  tar \
  tslib \
  u-boot-mkimage \
  vim \
  wget \
  zip \
  media-ctl \
  yavta \
  v4l-utils \
  mtd-utils-ubifs \
  coreutils \
  diffutils \
  findutils \
  less \
  mtdev \ 
  raw2rgbpnm \
"

ROS_INSTALL = " \
  screen \
  turtlebot-create \
  create-node \
  create-description \ 
  create-driver \
  turtlebot \ 
  turtlebot-description \ 
  linux-hardware \ 
  turtlebot-bringup \
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

IMAGE_INSTALL += " \
  packagegroup-cli-tools \
  packagegroup-cli-tools-debug \
  ${ROS_INSTALL} \
  ${FIRMWARE_INSTALL} \
  ${TOOLS_INSTALL} \
"

set_gumstix_user() {
	#To allow shutdown/restart
	echo "%sudo ALL=(ALL) ALL" >> ${IMAGE_ROOTFS}/etc/sudoers
}

ROOTFS_POSTPROCESS_COMMAND =+ "set_gumstix_user"
