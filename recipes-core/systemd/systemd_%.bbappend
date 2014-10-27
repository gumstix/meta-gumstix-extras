PACKAGECONFIG_append = " networkd"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://00-wireless-dhcp.network"

FILES_${PN} += "${sysconfdir}/systemd/network/00-wireless-dhcp.network"

do_install_append() {
	install -m 0644 ${WORKDIR}/00-wireless-dhcp.network ${D}${sysconfdir}/systemd/network/00-wireless-dhcp.network
}
