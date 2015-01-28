DESCRIPTION = "All members off sudo group are allowed to modify login1 settings"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

require ${COREBASE}/meta-openembedded/meta-oe/recipes-extended/polkit/polkit-group-rule.inc

SRC_URI = "file://50-org.freedesktop.login1.rules"

do_install() {
        install -m 0755 ${WORKDIR}/50-org.freedesktop.login1.rules ${D}${sysconfdir}/polkit-1/rules.d
}

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system sudo"
