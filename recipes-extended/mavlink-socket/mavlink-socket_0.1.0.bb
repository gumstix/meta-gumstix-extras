SUMMARY = "Expose MAVLink data from serial port over a UDP socket"
HOMEPAGE = "https://github.com/pixhawk/mavconn.git"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "glib-2.0"

SRCREV = "b3a004f181b6b181413fdf9cbe4135b546bb7d24"
SRC_URI = "git://github.com/mavlink/mavlink.git \
           file://mavlink_socket.c \
	   file://mavlink-socket.conf \
	   file://mavlink-socket.service"

S = "${WORKDIR}/git"

inherit systemd

SYSTEMD_SERVICE_${PN} = "mavlink-socket.service"

do_configure() {
    python "${S}/pymavlink/generator/mavgen.py" --output=${STAGING_INCDIR}/mavlink --lang=C --wire-protocol=1.0 ${S}/message_definitions/v1.0/common.xml
}

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} ../mavlink_socket.c -I ${STAGING_INCDIR}/mavlink/common -I ${STAGING_INCDIR}/glib-2.0/ -I ${STAGING_LIBDIR}/glib-2.0/include/ -lglib-2.0 -o mavlink-socket
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 mavlink-socket ${D}${bindir}

	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/mavlink-socket.conf ${D}${sysconfdir}

	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/mavlink-socket.service ${D}${systemd_unitdir}/system
}
