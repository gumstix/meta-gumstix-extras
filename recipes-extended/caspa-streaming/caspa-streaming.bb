DESCRIPTION = "Stream video from a Caspa camera to a networked host using gstreamer"
HOMEPAGE = "https://github.com/gumstix/Gumstix-YoctoProject-Repo/wiki/Gstreamer-and-Caspa"
SECTION = "devel"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

RDEPENDS_${PN} += " \
    gstreamer \
    gst-plugins-good-video4linux2 \
    gst-plugins-bad-autoconvert \
    gst-plugins-base-theora \
    gst-plugins-good-rtp \
    gst-plugins-good-udp \
    gst-plugins-base-videorate \
    gst-plugins-good-jpeg \
    gst-plugins-good-multipart \
    gst-plugins-base-tcp \
"

SRC_URI = "file://caspa-stream-raw"

S = "${WORKDIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
        install -d ${D}${bindir}
	install -m 755 ${S}/caspa-stream-raw ${D}${bindir}/
}

#FILES_{PN} += "${D}/${bindir}/*"
