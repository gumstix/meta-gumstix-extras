DESCRIPTION = "Gumstix Busybox UDHCPC Systemd Service"
SECTION = "base"
LICENSE = "MIT"
PR = "r45"

inherit systemd

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "file://udhcpc@.service"

SYSTEMD_SERVICE_udhcpc = "udhcpc@${iface}.service"
SYSTEMD_AUTO_ENABLE_udhcpc = "enable"

iface = "${GUMSTIX_WIFI_IFACE}"

FILES_${PN} += "${systemd_unitdir}/system/udhcpc@.service \
		${systemd_unitdir}/system/udhcpc@${iface}.service \
		${systemd_unitdir}/system/multi-user.target.wants/udhcpc@${iface}.service \
		${systemd_unitdir}/system/multi-user.target.wants/udhcpc@.service "


do_install_append() {

	echo "DO_INSTALL: install dhcpc systemd service"

        install -d ${D}${systemd_unitdir}/system/
        install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
	# generic service file for future use
        install -m 0644 ${WORKDIR}/udhcpc@.service ${D}${systemd_unitdir}/system/udhcpc@.service
	# service file for wifi module on Gumstix COM
        install -m 0644 ${WORKDIR}/udhcpc@.service ${D}${systemd_unitdir}/system/udhcpc@${iface}.service
	sed -i -e s/\@IFACE/\@${iface}/g ${D}${systemd_unitdir}/system/udhcpc@${iface}.service
	ln -sf ${systemd_unitdir}/system/udhcpc@${iface}.service \
		${D}${systemd_unitdir}/system//multi-user.target.wants/udhcpc@${iface}.service
}
