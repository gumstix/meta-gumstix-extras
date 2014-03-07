# Gumstix Serial ttyGS0 Systemd Service for serial console over USB OTG

FILES_${PN} += "${systemd_unitdir}/system/getty.target.wants/serial-getty@ttyGS0.service \
		"

SYSTEMD_AUTO_ENABLE_serial-getty = "enable"

do_install_append() {

	echo "DO_INSTALL: install serial getty ttyGS0 systemd service"
	
	install -m 0644 ${WORKDIR}/serial-getty@.service ${D}${sysconfdir}/systemd/system/serial-getty@ttyGS0.service
	ln -sf ${sysconfdir}/systemd/system/serial-getty@ttyGS0.service ${D}${sysconfdir}/systemd/system/getty.target.wants/serial-getty@ttyGS0.service
}
