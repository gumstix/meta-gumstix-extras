DESCRIPTION = "UART Loopback Tool for Testing"
SECTION = "testing"
# Taken from http://elinux.org/images/b/b7/Uart-loopback.c
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "file://uart_loopback.c"

S = "${WORKDIR}"

do_compile() {
    ${CC} uart_loopback.c -o uart_loopback
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 uart_loopback ${D}${bindir}
}

