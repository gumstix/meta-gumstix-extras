# http://processors.wiki.ti.com/index.php/WiLink8_WLAN_Demo:_Linux
# IP forwarding is required to route packets from one interface to
# another. To enable/disable:
#  $ sysctl -w net.ipv4.ip_forward=1
#  $ sysctl -w net.ipv4.ip_forward=0
# Equally, systemd-networkd provides a native options:
#   http://cgit.freedesktop.org/systemd/systemd/commit/?id=5a8bcb674f71a20e95df55319b34c556638378ce
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://hostapd.conf"

# Don't enable this by default
#SYSTEMD_AUTO_ENABLE_${PN} = "enable"

do_install_append() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/hostapd.conf ${D}${sysconfdir}/hostapd.conf
}
