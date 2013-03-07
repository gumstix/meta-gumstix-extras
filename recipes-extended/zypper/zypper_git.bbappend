PRINC := "${@int(PRINC) + 1}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://gumstix.repo"

do_install_append() {
	install -d ${D}${sysconfdir}/zypp/repos.d
	install -m 0644 ${WORKDIR}/gumstix.repo ${D}${sysconfdir}/zypp/repos.d/
}

FILES_${PN} += "${sysconfdir}/zypp/repos.d/gumstix.repo"
