DESCRIPTION = "Small application to control the pico DLP over I2C"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "file://*.c file://*.h file://${PN}"

do_configure() {
	cp ${WORKDIR}/*.[ch] ${WORKDIR}/${PN} ${S}
}

do_compile() {
	${CC} -o bus3-i2c *.c ${CFLAGS} ${LDFLAGS}
}

do_install() {
	install -d ${D}/${bindir}
	install -m 0755 ${S}/${PN} ${D}/${bindir}
	install -m 0755 ${S}/bus3-i2c ${D}/${bindir}
}

