# provide override for dnsmasq
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Don't enable this by default
SYSTEMD_AUTO_ENABLE_${PN} = "disable"
