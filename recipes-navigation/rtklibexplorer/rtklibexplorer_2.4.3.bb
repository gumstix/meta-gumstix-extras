SUMMARY = "RTKLib GPS real-time kinematic open-source software."
DESCRIPTION = "Open-source software to get accurate gps position information."
HOMEPAGE = "http://www.rtklib.com"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://readme.txt;beginline=104;endline=141;md5=d41d8cd98f00b204e9800998ecf8427e"

SRCREV = "${AUTOREV}"
PV = "2.4.3+git${SRCPV}"

SRC_URI = " \
    git://github.com/rtklibexplorer/RTKLIB.git;branch=demo5 \
    file://rtkrcv.conf \
    file://rtkrcv-options.conf \
    file://rtklib-rtkrcv.service \
    file://neo7_usb_10hz_raw.cmd \
    file://neo7_usb_1hz_raw.cmd \
"

inherit systemd

SYSTEMD_SERVICE_${PN} = "rtklib-rtkrcv.service"

S = "${WORKDIR}/git"
# CUI apps listed in makeall.sh
APPS = "pos2kml str2str rnx2rtkp convbin rtkrcv"

do_configure[noexec] = "1"

CFLAGS += "-I${S}/src"
do_compile() {
    for APP in ${APPS}; do
        oe_runmake -C ${S}/app/${APP}/gcc/
    done
}

do_install() {
    install -d ${D}${bindir}
    for APP in ${APPS}; do
        install -m 0755 ${B}/app/${APP}/gcc/${APP} ${D}${bindir}
    done

    # install config file to /etc/rtklib/
    install -d ${D}${sysconfdir}/rtklib/
    install -m 0644 ${WORKDIR}/rtkrcv.conf ${D}${sysconfdir}/rtklib/
    install -m 0644 ${WORKDIR}/rtkrcv-options.conf ${D}${sysconfdir}/rtklib/

    # copy data files to /etc/rtklib/data
    install -d ${D}${sysconfdir}/rtklib/data
    install -m 0644 ${WORKDIR}/neo7_usb_10hz_raw.cmd ${D}${sysconfdir}/rtklib/data/
    install -m 0644 ${WORKDIR}/neo7_usb_1hz_raw.cmd ${D}${sysconfdir}/rtklib/data/
    install -m 0644 ${S}/data/* ${D}${sysconfdir}/rtklib/data/

    # install service
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/rtklib-rtkrcv.service ${D}${systemd_unitdir}/system/
}

CONFFILES_${PN} += "${sysconfdir}/rtklib/rtkrcv.conf \
                    ${sysconfdir}/rtklib/rtkrcv-options.conf \
"

