DESCRIPTION = "A basic console image for Gumstix boards."
LICENSE = "MIT"
PR = "r0"

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
  u-boot-fw-utils \
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
"

IMAGE_INSTALL += " \
  packagegroup-cli-tools \
  packagegroup-cli-tools-debug \
  ${FIRMWARE_INSTALL} \
  ${TOOLS_INSTALL} \
"

add_custom_smart_config() {
        smart --data-dir=${IMAGE_ROOTFS}/var/lib/smart channel --add gumstix type=rpm-md name="Gumstix Package Repository" baseurl=http://package-cache.s3-website-us-west-2.amazonaws.com/dev/ -y
}
set_gumstix_user() {
	echo "gumstix:x:500:" >> ${IMAGE_ROOTFS}/etc/group
	echo "gumstix:VQ43An5F8LYqc:500:500:Gumstix User,,,:/home/gumstix:/bin/bash"  >> ${IMAGE_ROOTFS}/etc/passwd

	install -d ${IMAGE_ROOTFS}/home/gumstix
	cp -f ${IMAGE_ROOTFS}/etc/skel/.bashrc ${IMAGE_ROOTFS}/etc/skel/.profile ${IMAGE_ROOTFS}/home/gumstix
	chown gumstix:gumstix -R ${IMAGE_ROOTFS}/home/gumstix

	echo "%gumstix ALL=(ALL) ALL" >> ${IMAGE_ROOTFS}/etc/sudoers
	chmod 0440 ${IMAGE_ROOTFS}/etc/sudoers
	chmod u+s ${IMAGE_ROOTFS}/usr/bin/sudo
}

ROOTFS_POSTPROCESS_COMMAND =+ "set_gumstix_user ; add_custom_smart_config ;"
