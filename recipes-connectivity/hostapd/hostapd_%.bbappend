# http://processors.wiki.ti.com/index.php/WiLink8_WLAN_Demo:_Linux
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://hostapd.conf \
    file://ip_forward.conf \
"

# Don't enable this by default
#SYSTEMD_AUTO_ENABLE_${PN} = "enable"

do_install_append() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/hostapd.conf ${D}${sysconfdir}/hostapd.conf

	install -d ${D}${sysconfdir}/sysctl.d
	install -m 0644 ${WORKDIR}/ip_forward.conf ${D}${sysconfdir}/sysctl.d/ip_forward.conf
}
