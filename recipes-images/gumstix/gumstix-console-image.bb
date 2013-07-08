DESCRIPTION = "The most basic Gumstix image"
LICENSE = "CLOSED"

inherit core-image

IMAGE_FEATURES += "package-management"
IMAGE_INSTALL = "packagegroup-core-boot ${ROOTFS_PKGMANAGE_BOOTSTRAP} ${CORE_IMAGE_EXTRA_INSTALL} "

DEPENDS = "virtual/kernel"

AUDIO_INSTALL = " \
  alsa-utils-aplay \
  alsa-utils-alsactl \
  alsa-utils-alsamixer \
  alsa-utils-amixer \
  alsa-utils-speakertest \
 "

BASE_INSTALL = " \
  ${MACHINE_EXTRA_RRECOMMENDS} \
  ${@base_contains("DISTRO_FEATURES", "bluetooth", "bluez4", "", d)} \
  avahi-utils \
  base-files \
  base-passwd \
  bash \
  coreutils \
  dbus \
  devmem2 \
  man \	
  man-pages \
  memtester \
  netbase \
  net-tools \
  polkit \
  sed \
  shadow tinylogin \
  u-boot-mkimage \
  udisks \
  upower \
  update-alternatives-cworth \
  util-linux \
  which \
 "

FIRMWARE_INSTALL = " \
  linux-firmware-sd8686 \
  linux-firmware-rtl8192cu \
  linux-firmware-rtl8192ce \
  linux-firmware-rtl8192su \
  linux-firmware-wl12xx \
 "
NETWORK_INSTALL = " \
  networkmanager \
  networkmanager-tests \
  rfkill \
  wireless-tools \
  ${@base_contains("DISTRO_FEATURES", "wifi", "iw wpa-supplicant", "", d)} \
 "

TOOLS_INSTALL = " \
  bzip2 \
  cpufrequtils \
  dosfstools \
  e2fsprogs \
  evtest \
  findutils \
  iputils \
  grep \
  gzip \
  htop \
  nano \
  openssh-ssh openssh-keygen openssh-scp \
  sudo \
  tar \
  wget \
  zip \
 "

IMAGE_INSTALL += " \
  ${ROOTFS_PKGMANAGE} \
  ${BASE_INSTALL} \
  ${AUDIO_INSTALL} \
  ${FIRMWARE_INSTALL} \
  ${NETWORK_INSTALL} \
  ${TOOLS_INSTALL} \
 "

# this section removes remnants of legacy sysvinit support
# for packages installed above
IMAGE_FILE_BLACKLIST += " \
                        /etc/init.d/NetworkManager \
                        /etc/init.d/avahi-daemon \
                        /etc/init.d/dbus-1 \
                        /etc/init.d/dnsmasq \
                        /etc/init.d/networking \
                        /etc/init.d/ntpd \
                        /etc/init.d/sshd \
                        /etc/init.d/udev \
                        /etc/init.d/udev-cache \
                       "

remove_blacklist_files() {
        for i in ${IMAGE_FILE_BLACKLIST}; do
	   rm -rf ${IMAGE_ROOTFS}$i
	done

}

ROOTFS_POSTPROCESS_COMMAND =+ "remove_blacklist_files ; "

