SUMMARY = "Expands rootfs to take up remaining disk space on boot - see https://github.com/96boards/96boards-tools"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "file://resize-disk \
           file://resize-rootfs \
           file://resize-rootfs.service \
           file://LICENSE \
"

S = "${WORKDIR}"

inherit systemd allarch update-rc.d

do_install () {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/resize-disk ${D}${sysconfdir}/init.d/
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${S}/resize-rootfs.service ${D}${systemd_unitdir}/system
    install -d ${D}${sbindir}
    install -m 0755 ${S}/resize-rootfs ${D}${sbindir}
}

INITSCRIPT_NAME = "resize-disk"
INITSCRIPT_PARAMS = "start 99 5 2 . stop 20 0 1 6 ."

SYSTEMD_SERVICE_${PN} = "resize-rootfs.service"
RDEPENDS_${PN} += "e2fsprogs-resize2fs gptfdisk parted util-linux udev"
