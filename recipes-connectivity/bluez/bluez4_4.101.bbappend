FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PRINC := "${@int(PRINC) + 1}"

SRC_URI += " file://bluetooth-ttyO1.service"

inherit systemd

SYSTEMD_SERVICE_${PN} = "bluetooth-ttyO1.service"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/bluetooth-ttyO1.service ${D}${systemd_unitdir}/system
}

