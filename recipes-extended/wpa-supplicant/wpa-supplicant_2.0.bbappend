DESCRIPTION = "Gumstix WPA Supplicant Systemd Service"
SECTION = "base"
LICENSE = "MIT"
PR = "r3"

inherit systemd

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "file://wpa_supplicant@.service \
	    file://wpa_supplicant.conf-sane"

SYSTEMD_SERVICE_wpa_supplicant = "wpa_supplicant@.service"
SYSTEMD_AUTO_ENABLE_udhcpc = "enable"

iface = "${GUMSTIX_WIFI_IFACE}"

FILES_${PN} += "${systemd_unitdir}/system/wpa_supplicant@${iface}.service"
FILES_${PN} += "${systemd_unitdir}/system/multi-user.target.wants/wpa_supplicant@${iface}.service"
#FILES_${PN} += "${sysconfdir}/wpa_supplicant.conf"

do_install_append() {

	echo "DO_INSTALL: install WPA Supplicant systemd service"

        install -d ${D}${systemd_unitdir}/system/
        install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
        install -m 0644 ${WORKDIR}/wpa_supplicant@.service ${D}${systemd_unitdir}/system/wpa_supplicant@${iface}.service
	ln -sf ${systemd_unitdir}/system/wpa_supplicant@${iface}.service \
		${D}${systemd_unitdir}/system//multi-user.target.wants/wpa_supplicant@${iface}.service
}
