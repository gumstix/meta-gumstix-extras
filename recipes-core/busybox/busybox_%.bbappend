# Gumstix Busybox UDHCPC Systemd Service

inherit systemd

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://udhcpc@.service \
    file://udhcpc@eth0.service \
    file://yes_mesg.cfg \
"

SYSTEMD_SERVICE_udhcpc = "udhcpc@.service"
SYSTEMD_AUTO_ENABLE_udhcpc = "disable"

iface = "${GUMSTIX_WIFI_IFACE}"

FILES_${PN} += "${systemd_unitdir}/system/udhcpc@.service \
                ${systemd_unitdir}/system/udhcpc@eth0.service \ "

do_install_append() {
	echo "DO_INSTALL: install dhcpc systemd service"

        install -d ${D}${systemd_unitdir}/system/
        install -d ${D}${sysconfdir}/systemd/system/network.target.wants/
	# generic service file for future use
        install -m 0644 ${WORKDIR}/udhcpc@.service ${D}${systemd_unitdir}/system/udhcpc@.service
	sed -i -e s/\@IFACE/\@${iface}/g ${D}${systemd_unitdir}/system/udhcpc@.service

        install -m 0644 ${WORKDIR}/udhcpc@eth0.service ${D}${systemd_unitdir}/system/udhcpc@eth0.service
	ln -sf ${systemd_unitdir}/udhcpc@eth0.service ${D}${sysconfdir}/systemd/system/network.target.wants/udhcpc@eth0.service
}
