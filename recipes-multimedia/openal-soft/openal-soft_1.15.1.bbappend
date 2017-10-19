FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI_append_raspberrypi := " file://0001-Build-NEON-code-with-mfpu-neon.patch"
