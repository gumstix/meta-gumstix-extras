DESCRIPTION = "Python client library for Gumstix RoboVero"
HOMEPAGE = "https://github.com/robovero/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/COPYING;md5=52b7490557d2fd1a2c673d32aa5d4b8a"
SRCREV = "a0c5719086348af7d1a9c91f04a255f512bb38d4"
SRC_URI = "git://github.com/robovero/python.git;branch=master"
DEPENDS = "python python-pyserial"
RDEPENDS_${PN} = "python-pyserial"

PACKAGES += "${PN}-examples"

inherit python-dir

S = "${WORKDIR}/git"

SITE_PACKAGES="${D}/${PYTHON_SITEPACKAGES_DIR}"
SHARE="/usr/share"

do_install() {
	install -d ${D}${SHARE}/robovero-python
        install -d ${SITE_PACKAGES}/robovero
	install -m 755 ${S}/*.py ${D}${SHARE}/robovero-python/
	install -m 755 ${S}/robovero/*.py ${SITE_PACKAGES}/robovero/
}

FILES_${PN} += "${PYTHON_SITEPACKAGES_DIR} "
FILES_${PN}-examples += "${SHARE}/robovero-python"
