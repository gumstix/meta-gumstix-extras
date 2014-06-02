DESCRIPTION = "OMAP ISP Live Tool"
HOMEPAGE = "git.ideasonboard.org/omap3-isp-live.git"
SECTION = "devel"
PRIORITY = "optional"
LICENSE = "GPLv2"
PR = "r0"

SRC_URI = "git://git.ideasonboard.org/omap3-isp-live.git;protocol=git;branch=master"
SRCREV = "cbd64859e6c1509b0f2ee7728cb66b120d52c45e"

LIC_FILES_CHKSUM = "file://LICENSE;md5=eb723b61539feef013de476e68b5c50a"

S = "${WORKDIR}/git"

inherit autotools-brokensep

EXTRA_OEMAKE = "'CC=${CC}' 'KDIR=${STAGING_KERNEL_DIR}'"

do_install() {
        install -d ${D}${bindir}
	install -d ${D}${libdir}
	install -m 755 ${S}/live ${D}${bindir}/
	install -m 755 ${S}/snapshot ${D}${bindir}/
	install -m 755 ${S}/isp/*.so ${D}${libdir}/
}

FILES_{PN} += "${D}/${bindir}/* \"

PARALLEL_MAKE = ""

COMPATIBLE_MACHINE = "overo"
