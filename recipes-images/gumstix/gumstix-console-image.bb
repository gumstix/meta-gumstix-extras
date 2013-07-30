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
  ${@base_contains("PACKAGECONFIG", "bluetooth", "bluez4", "", d)} \
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
  strace \
  u-boot-mkimage \
  udisks \
  upower \
  update-alternatives-cworth \
  util-linux \
  which \
 "

# Gumstix machines individually RDEPEND on the firware they need but we repeat
# it here as we might want to use the same image on multiple different machines.
FIRMWARE_INSTALL = " \
  linux-firmware-sd8686 \
  linux-firmware-sd8787 \
 "

NETWORK_INSTALL = " \
  rfkill \
  wireless-tools \
  crda \ 
  ${@base_contains("PACKAGECONFIG", "wifi", "iw wpa-supplicant", "", d)} \
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
  ${NETWORK_INSTALL} \
  ${TOOLS_INSTALL} \
  ${FIRMWARE_INSTALL} \
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

add_custom_smart_config() {
        smart --data-dir=${IMAGE_ROOTFS}/var/lib/smart channel --add gumstix type=rpm-md name="Gumstix Package Repository" baseurl=http://package-cache.gumstix.org/ -y
}

ROOTFS_POSTPROCESS_COMMAND =+ "remove_blacklist_files ; add_custom_smart_config ;"

