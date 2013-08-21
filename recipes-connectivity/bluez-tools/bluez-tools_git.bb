DESCRIPTION = "Bluez Tools"
HOMEPAGE = "http://code.google.com/p/bluez-tools/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

RDEPENDS_${PN} = "bluez4 obexd"
DEPENDS = "glib-2.0 dbus-glib"

PR = "r2+gitr${SRCPV}"

SRCREV = "171181b6ef6c94aefc828dc7fd8de136b9f97532"

S = "${WORKDIR}/git"

SRC_URI = "git://gitorious.org/bluez-tools/bluez-tools.git;protocol=git"

inherit autotools

do_install() {
       install -d ${D}${bindir}
       install -m 755 ${S}/src/bt-adapter ${D}${bindir}
       install -m 755 ${S}/src/bt-agent   ${D}${bindir}
       install -m 755 ${S}/src/bt-audio   ${D}${bindir}
       install -m 755 ${S}/src/bt-device  ${D}${bindir}
       install -m 755 ${S}/src/bt-input   ${D}${bindir}
       install -m 755 ${S}/src/bt-monitor ${D}${bindir}
       install -m 755 ${S}/src/bt-network ${D}${bindir}
       install -m 755 ${S}/src/bt-obex    ${D}${bindir}
       install -m 755 ${S}/src/bt-serial  ${D}${bindir}
}
