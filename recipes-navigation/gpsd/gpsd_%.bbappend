FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://gpsd.service \
            file://gpsd-default"
