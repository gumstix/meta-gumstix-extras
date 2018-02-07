# use systemd to manage networks
# See http://www.freedesktop.org/software/systemd/man/systemd.network.html
PACKAGECONFIG_append = " networkd resolved"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://eth.network \
    file://wifi.network \
    file://ap.network \
    file://85-mlan.link \
"

FILES_${PN} += "${sysconfdir}/systemd/network/*"

do_install() {
	autotools_do_install
	install -d ${D}/${base_sbindir}
	if ${@bb.utils.contains('PACKAGECONFIG', 'serial-getty-generator', 'false', 'true', d)}; then
		# Provided by a separate recipe
		rm ${D}${systemd_unitdir}/system/serial-getty* -f
	fi

	# Provide support for initramfs
	[ ! -e ${D}/init ] && ln -s ${rootlibexecdir}/systemd/systemd ${D}/init
	[ ! -e ${D}/${base_sbindir}/udevd ] && ln -s ${rootlibexecdir}/systemd/systemd-udevd ${D}/${base_sbindir}/udevd

	# Create machine-id
	# 20:12 < mezcalero> koen: you have three options: a) run systemd-machine-id-setup at install time, b) have / read-only and an empty file there (for stateless) and c) boot with / writable
	touch ${D}${sysconfdir}/machine-id

	install -d ${D}${sysconfdir}/udev/rules.d/
	install -d ${D}${sysconfdir}/tmpfiles.d
	install -m 0644 ${WORKDIR}/*.rules ${D}${sysconfdir}/udev/rules.d/
	install -d ${D}${libdir}/pkgconfig
	install -m 0644 ${B}/src/udev/udev.pc ${D}${libdir}/pkgconfig/

	install -m 0644 ${WORKDIR}/00-create-volatile.conf ${D}${sysconfdir}/tmpfiles.d/

	if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/systemd-udevd
		sed -i s%@UDEVD@%${rootlibexecdir}/systemd/systemd-udevd% ${D}${sysconfdir}/init.d/systemd-udevd
	fi

	chown root:systemd-journal ${D}/${localstatedir}/log/journal

	# Delete journal README, as log can be symlinked inside volatile.
	rm -f ${D}/${localstatedir}/log/README

	install -d ${D}${systemd_unitdir}/system/graphical.target.wants
	install -d ${D}${systemd_unitdir}/system/multi-user.target.wants
	install -d ${D}${systemd_unitdir}/system/poweroff.target.wants
	install -d ${D}${systemd_unitdir}/system/reboot.target.wants
	install -d ${D}${systemd_unitdir}/system/rescue.target.wants

	# Create symlinks for systemd-update-utmp-runlevel.service
	if ${@bb.utils.contains('PACKAGECONFIG', 'utmp', 'true', 'false', d)}; then
		ln -sf ../systemd-update-utmp-runlevel.service ${D}${systemd_unitdir}/system/graphical.target.wants/systemd-update-utmp-runlevel.service
		ln -sf ../systemd-update-utmp-runlevel.service ${D}${systemd_unitdir}/system/multi-user.target.wants/systemd-update-utmp-runlevel.service
		ln -sf ../systemd-update-utmp-runlevel.service ${D}${systemd_unitdir}/system/poweroff.target.wants/systemd-update-utmp-runlevel.service
		ln -sf ../systemd-update-utmp-runlevel.service ${D}${systemd_unitdir}/system/reboot.target.wants/systemd-update-utmp-runlevel.service
		ln -sf ../systemd-update-utmp-runlevel.service ${D}${systemd_unitdir}/system/rescue.target.wants/systemd-update-utmp-runlevel.service
	fi

	# Enable journal to forward message to syslog daemon
	sed -i -e 's/.*ForwardToSyslog.*/ForwardToSyslog=yes/' ${D}${sysconfdir}/systemd/journald.conf
	# Set the maximium size of runtime journal to 64M as default
	sed -i -e 's/.*RuntimeMaxUse.*/RuntimeMaxUse=64M/' ${D}${sysconfdir}/systemd/journald.conf

	# this file is needed to exist if networkd is disabled but timesyncd is still in use since timesyncd checks it
	# for existence else it fails
	if [ -s ${D}${exec_prefix}/lib/tmpfiles.d/systemd.conf ]; then
		${@bb.utils.contains('PACKAGECONFIG', 'networkd', ':', 'sed -i -e "\$ad /run/systemd/netif/links 0755 root root -" ${D}${exec_prefix}/lib/tmpfiles.d/systemd.conf', d)}
	fi
	if ! ${@bb.utils.contains('PACKAGECONFIG', 'resolved', 'true', 'false', d)}; then
		echo 'L! ${sysconfdir}/resolv.conf - - - - ../run/systemd/resolve/resolv.conf' >>${D}${exec_prefix}/lib/tmpfiles.d/etc.conf
		echo 'd /run/systemd/resolve 0755 root root -' >>${D}${exec_prefix}/lib/tmpfiles.d/systemd.conf
		echo 'f /run/systemd/resolve/resolv.conf 0644 root root' >>${D}${exec_prefix}/lib/tmpfiles.d/systemd.conf
		ln -s ../run/systemd/resolve/resolv.conf ${D}${sysconfdir}/resolv-conf.systemd
	else
		sed -i -e "s%^L! /etc/resolv.conf.*$%L! /etc/resolv.conf - - - - ../run/systemd/resolve/resolv.conf%g" ${D}${exec_prefix}/lib/tmpfiles.d/etc.conf
		ln -s ../run/systemd/resolve/resolv.conf ${D}${sysconfdir}/resolv-conf.systemd
	fi
	install -Dm 0755 ${S}/src/systemctl/systemd-sysv-install.SKELETON ${D}${systemd_unitdir}/systemd-sysv-install

	# If polkit is setup fixup permissions and ownership
	# if ${@bb.utils.contains('PACKAGECONFIG', 'polkit', 'true', 'false', d)}; then
	# 	if [ -d ${D}${datadir}/polkit-1/rules.d ]; then
	# 		chmod 700 ${D}${datadir}/polkit-1/rules.d
	# 		chown polkitd:root ${D}${datadir}/polkit-1/rules.d
	# 	fi
	# fi
	install -d ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/*.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${WORKDIR}/*.link ${D}${sysconfdir}/systemd/network/
	ln -s /run/systemd/resolve/resolv.conf ${D}${sysconfdir}/resolv.conf
}

FILES_${PN} += "${sysconfdir}/*"

USERADD_PARAM_${PN} = "--system --home /dev/null systemd-journal-gateway"
