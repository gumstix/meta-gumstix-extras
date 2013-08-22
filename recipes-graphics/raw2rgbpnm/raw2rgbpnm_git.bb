SUMMARY = "Convert raw Bayer images to netpbm format"
HOMEPAGE = "http://gitorious.org/raw2rgbpnm"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://gitorious.org/raw2rgbpnm/raw2rgbpnm.git;protocol=git"
SRCREV = "9be9e6be8c3b8586e96288c49d64dead9145165b"
S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 raw2rgbpnm ${D}${bindir}
}
