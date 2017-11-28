SUMMARY = "Packet forwarder to make the link between a LoRa concentrator and The Things Network's backend."
DESCRIPTION = "This packet forwarder isn't destined to The Things Gateway's users, but for users who already have a gateway from another vendor, and that want to connect it to The Things Network."
HOMEPAGE = "https://www.thethingsnetwork.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c6ca344bf10fb1aea76fb0ab3e92e14d"
GO_PACKAGE_NAME = "github.com/TheThingsNetwork/packet_forwarder"
SRCREV = "${AUTOREV}"
SRC_URI = " \
	git://github.com/ThethingsNetwork/packet_forwarder.git;destsuffix=src/${GO_PACKAGE_NAME};branch=develop \
	file://ttn-config.yml \
	file://ttn-pkt-fwd.service \
	file://ttn-pkt-fwd.sh \
	file://imst_* \
"

S = "${WORKDIR}/src/${GO_PACKAGE_NAME}"

FILES_${PN} = "\
	${bindir}/packet-forwarder \
	${sysconfdir}/ttn-pkt-fwd/ttn-config.yml \
	${sysconfdir}/ttn-pkt-fwd/ttn-pkt-fwd.sh \
	${systemd_unitdir}/system/ttn-pkt-fwd.service \
"	

inherit go
inherit systemd

SYSTEMD_SERVICE_${PN} = "ttn-pkt-fwd.service"

do_configure[noexec] = "1"

DEPENDS += "\
	glibc \
	go-native \
	govendor-native \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
INSANE_SKIP_${PN} = "ldflags already-stripped"

do_compile() {

	export GOPATH="${WORKDIR}"
	cd ${S}
	oe_runmake dev-deps

	export CFLAGS="${CFLAGS} -I${S}/lora_gateway/libloragw/inc -fPIC"
	# check if lora_gateway directory exists
	if [ -d "lora_gateway" ]; then
		rm -fR lora_gateway
	fi
	oe_runmake deps

	# copy imst_{MACHINE} file into appropriate directory
	if [ -f "${WORKDIR}/imst_${MACHINE}.h" ]; then
		cp "${WORKDIR}/imst_${MACHINE}.h" ${S}/lora_gateway/libloragw/inc/
		oe_runmake PLATFORM=imst_${MACHINE} build
	else
		# use rpi setup as default
		oe_runmake PLATFORM=imst_rpi build
	fi
}

do_install() {

	install -d ${D}${bindir}/

	TEMPSTRUC="aarch64"
	# custom machine file means custom binary output
	if [ "${TUNE_FEATURES}" = "$TEMPSTRUC" ]; then
		install -m 0755 ${S}/release/packet-forwarder-linux-arm64-imst_rpi-native ${D}${bindir}/packet-forwarder
	elif [ -f "${WORKDIR}/imst_${MACHINE}.h" ]; then
		install -m 0755 ${S}/release/packet-forwarder-linux-arm-imst_${MACHINE}-native ${D}${bindir}/packet-forwarder
	else
		install -m 0755 ${S}/release/packet-forwarder-linux-arm-imst_rpi-native ${D}${bindir}/packet-forwarder
	fi

	# install config file to /etc/ttn-pkt-fwd/
	install -d ${D}${sysconfdir}/ttn-pkt-fwd/
	install -m 0644 ${WORKDIR}/ttn-config.yml ${D}${sysconfdir}/ttn-pkt-fwd/
	install -m 0755 ${WORKDIR}/ttn-pkt-fwd.sh  ${D}${sysconfdir}/ttn-pkt-fwd/

	# install service
	install -d ${D}${systemd_unitdir}/system/
	install -m 0755 ${WORKDIR}/ttn-pkt-fwd.service ${D}${systemd_unitdir}/system/
}

CONFFILES_${PN} += "${sysconfdir}/ttn-pkt-fwd/ttn-config.yml"
