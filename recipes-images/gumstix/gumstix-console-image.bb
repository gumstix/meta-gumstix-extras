DESCRIPTION = "A basic console image for Gumstix boards."
LICENSE = "MIT"

IMAGE_FEATURES += "splash package-management ssh-server-openssh"
IMAGE_LINGUAS = "en-us"

inherit core-image

# Gumstix machines individually RDEPEND on the firware they need but we repeat
# it here as we might want to use the same image on multiple different machines.
FIRMWARE_INSTALL = " \
  linux-firmware-sd8686 \
  linux-firmware-sd8787 \
  linux-firmware-wl18xx \
"

SYSTEM_TOOLS_INSTALL = " \
  alsa-utils \
  cpufrequtils \
  systemd-analyze \
  tzdata \
"

DEV_TOOLS_INSTALL = " \
  memtester \
  mtd-utils-ubifs \
  u-boot-mkimage \
"

NETWORK_TOOLS_INSTALL = " \
  curl \
  init-ifupdown \
  iputils \
  iw \
  ntp \
"

MEDIA_TOOLS_INSTALL = " \
  media-ctl \
  raw2rgbpnm \
  v4l-utils \
  yavta \
"

GRAPHICS_LIBS = " \
  mtdev \ 
  tslib \
"  

UTILITIES_INSTALL = " \
  coreutils \
  diffutils \
  findutils \
  grep \
  gzip \
  less \
  nano \
  packagegroup-cli-tools \
  packagegroup-cli-tools-debug \
  sudo \
  tar \
  vim \
  wget \
  zip \
"
 
IMAGE_INSTALL += " \
  ${FIRMWARE_INSTALL} \
  ${SYSTEM_TOOLS_INSTALL} \
  ${DEV_TOOLS_INSTALL} \
  ${NETWORK_TOOLS_INSTALL} \
  ${MEDIA_TOOLS_INSTALL} \
  ${GRAPHICS_LIBS} \
  ${UTILITIES_INSTALL} \
"

set_gumstix_user() {
	#To allow shutdown/restart
	echo "%sudo ALL=(ALL) ALL" >> ${IMAGE_ROOTFS}/etc/sudoers
}

ROOTFS_POSTPROCESS_COMMAND =+ "set_gumstix_user;"
