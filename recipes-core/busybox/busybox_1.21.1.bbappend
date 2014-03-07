# Gumstix Busybox UDHCPC Systemd Service

inherit systemd

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "file://udhcpc@.service"

SYSTEMD_SERVICE_udhcpc = "udhcpc@${iface}.service"
SYSTEMD_AUTO_ENABLE_udhcpc = "disable"

iface = "${GUMSTIX_WIFI_IFACE}"

FILES_${PN} += "${systemd_unitdir}/system/udhcpc@.service \"


do_install_append() {

	echo "DO_INSTALL: install dhcpc systemd service"

        install -d ${D}${systemd_unitdir}/system/
	# generic service file for future use
        install -m 0644 ${WORKDIR}/udhcpc@.service ${D}${systemd_unitdir}/system/udhcpc@.service
	sed -i -e s/\@IFACE/\@${iface}/g ${D}${systemd_unitdir}/system/udhcpc@.service
}
