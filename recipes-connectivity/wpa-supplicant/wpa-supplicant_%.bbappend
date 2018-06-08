# Provide a sample wpa_supplicant configuration for wifi such that
# systems automatically try to connect to an open network.  Tweak the
# included configuration file for your particular network setup.
# Note: Don't configure systems using NetworkManager here---the generic
# wpa_supplicant.service provides a dbus-activated mechanism for
# configuring networks.
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://wpa_supplicant.conf \
            file://wpa_supplicant@.service"

do_install_append() {
	install -d ${D}${sysconfdir}/wpa_supplicant
	install -m 0644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf

	# link instance-specific units---this is equivalent to calling
        #   $ systemctl enable wpa_supplicant@wlan0
        # on the target system.
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	# copying modified wpa_supplicant@.service file and add symlink
	install -m 644 ${WORKDIR}/wpa_supplicant@.service ${D}/${systemd_unitdir}/system
        ln -s ${systemd_unitdir}/system/wpa_supplicant@.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service
}

CONFFILES_${PN} += "${sysconfdir}/wpa_supplicant/*.conf"
