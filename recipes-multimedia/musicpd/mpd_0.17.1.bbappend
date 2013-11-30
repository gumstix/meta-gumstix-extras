FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PRINC := "${@int(PRINC) + 1}"

SRC_URI = " \
    http://www.musicpd.org/download/${BPN}/0.17/${BPN}-${PV}.tar.bz2 \
    file://mpd.conf.in \
"
