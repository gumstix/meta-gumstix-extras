# use systemd to manage networks
# See http://www.freedesktop.org/software/systemd/man/systemd.network.html
PACKAGECONFIG_append = " networkd resolved"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://eth.network \
    file://wifi.network \
    file://ap.network \
    file://85-mlan.link \
"

FILES_${PN} += "${sysconfdir}/systemd/network/*"

do_install_append() {
	install -d ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/*.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/*.link ${D}${sysconfdir}/systemd/network/
}

USERADD_PARAM_${PN} = "--system --home /dev/null systemd-journal-gateway"
