SUMMARY = "Convert raw Bayer images to netpbm format"
HOMEPAGE = "http://gitorious.org/raw2rgbpnm"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI[md5sum] = "acd36eab89d500cd2c56a408890539b0"
SRC_URI[sha256sum] = "dccc91554f8a6ee7f9ee56c74f51bf77edc21d79ae97f82bb3b56d7de02eb79e"

SRC_URI = "https://s3.amazonaws.com/gumstix-misc/raw2rgbpnm.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 raw2rgbpnm ${D}${bindir}
}
