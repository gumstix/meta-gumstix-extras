# Gumstix WPA Supplicant Systemd Service

inherit systemd

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "file://wpa_supplicant@.service \
	    file://wpa_supplicant.conf-sane"

SYSTEMD_SERVICE_wpa_supplicant = "wpa_supplicant@.service"

iface = "${GUMSTIX_WIFI_IFACE}"

FILES_${PN} += "${sysconfdir}/wpa_supplicant/wpa_supplicant-${iface}.conf"

do_install_append() {

	echo "DO_INSTALL: install WPA Supplicant systemd service"
	install -d 0644 ${D}${sysconfdir}/wpa_supplicant
        install -m 0644 ${WORKDIR}/wpa_supplicant.conf-sane ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-${iface}.conf
}
