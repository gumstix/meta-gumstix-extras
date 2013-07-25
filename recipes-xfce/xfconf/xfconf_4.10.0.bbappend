PRINC := "${@int(PRINC) + 1}"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
EXTRA_OECONF = "--disable-perl-bindings"
