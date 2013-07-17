DESCRIPTION = "Graphical login manager"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "xinput gnome-panel tcp-wrappers libcanberra libxklavier grep consolekit libpam gnome-doc-utils gtk+ xrdb"

PR = "r16"

inherit gnome update-rc.d systemd useradd

do_install_prepend() {
    install -d ${D}/${localstatedir}/lib/gdm/.gconf.mandatory
    install ${WORKDIR}/%gconf-tree.xml ${D}/${localstatedir}/lib/gdm/.gconf.mandatory/
}

do_install_append() {
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/gdm ${D}/${sysconfdir}/init.d/

    install -d ${D}/${sysconfdir}/gdm
    install -m 0644 ${WORKDIR}/gdm.conf ${D}/${sysconfdir}/gdm/

    install -d ${D}/${sysconfdir}/pam.d
    install -m 0755 ${WORKDIR}/gdm-pam       ${D}/${sysconfdir}/pam.d/gdm

    install -d ${D}/${sysconfdir}/gdm/Init
    install -m 0755 ${WORKDIR}/Default ${D}/${sysconfdir}/gdm/Init

    install -d ${D}${systemd_unitdir}/system
    sed -e 's,%sbindir%,${sbindir},g' \
        < ${WORKDIR}/gdm.service.in \
        > ${D}${systemd_unitdir}/system/gdm.service

    chown -R gdm:gdm ${D}${localstatedir}/lib/gdm
    chmod 0750 ${D}${localstatedir}/lib/gdm
}

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --no-create-home --home ${localstatedir}/lib/gdm --user-group gdm"

pkg_postinst_${PN} () {
# Register up as default dm
mkdir -p $D${sysconfdir}/X11/
echo "${bindir}/gdm" > $D${sysconfdir}/X11/default-display-manager
}
