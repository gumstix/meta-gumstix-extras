DESCRIPTION = "The Gumstix XFCE image"

require gumstix-console-image.bb

XSERVER +=  "\
  xmodmap \
  xrdb \
  setxkbmap \
 "

XFCE_INSTALL = " \
  packagegroup-xfce-base \
  gdm \
  gnome-keyring \
  polkit-gnome \
  polkit-group-rule-network \
  polkit-group-rule-datetime \
  polkit-group-rule-shutdown \
  "

XFCE_APPS = "\
  epiphany \
  web-webkit \
  network-manager-applet \
  xterm \
 "

XFCE_THEMES = " \
  gnome-icon-theme \
  gtk-engine-clearlooks \
  gtk-theme-clearlooks \
  gtk-tweak \
 "

XFCE_DOCS = " \
  exo-doc \
  thunar-doc \
  xfce4-clipman-plugin-doc \
  xfce4-notifyd-doc \
  xfce4-panel-doc \
  xfce4-screenshooter-doc \
  xfce4-session-doc \
  xfce-terminal-doc \
  xfdesktop-doc \
 "
IMAGE_INSTALL += " \
  ${XSERVER} \
  ${XFCE_INSTALL} \
  ${XFCE_APPS} \
  ${XFCE_THEMES} \
 "

# this section removes remnants of legacy sysvinit support
# for packages installed above
IMAGE_FILE_BLACKLIST += " \
			 /usr/share/xsessions/gnome.desktop \
			/usr/share/gdm/autostart/LoginWindow/at-spi-registryd-wrapper.desktop \
			/usr/share/gdm/autostart/LoginWindow/gnome-mag.desktop \
			/usr/share/gdm/autostart/LoginWindow/gnome-power-manager.desktop \
			/usr/share/gdm/autostart/LoginWindow/gnome-settings-daemon.desktop \
			/usr/share/gdm/autostart/LoginWindow/gok.desktop \
			/usr/share/gdm/autostart/LoginWindow/libcanberra-ready-sound.desktop \
			/usr/share/gdm/autostart/LoginWindow/metacity.desktop \
			/usr/share/gdm/autostart/LoginWindow/orca-screen-reader.desktop \
                       "
set_gumstix_user() {
	echo "gumstix:x:500:" >> ${IMAGE_ROOTFS}/etc/group
	echo "gumstix:VQ43An5F8LYqc:500:500:Gumstix User,,,:/home/gumstix:/bin/bash"  >> ${IMAGE_ROOTFS}/etc/passwd

	install -d ${IMAGE_ROOTFS}/home/gumstix
	cp -f ${IMAGE_ROOTFS}/etc/skel/.bashrc ${IMAGE_ROOTFS}/etc/skel/.profile ${IMAGE_ROOTFS}/home/gumstix
	chown gumstix:gumstix -R ${IMAGE_ROOTFS}/home/gumstix

	echo "%gumstix ALL=(ALL) ALL" >> ${IMAGE_ROOTFS}/etc/sudoers
	chmod 0440 ${IMAGE_ROOTFS}/etc/sudoers
	chmod u+s ${IMAGE_ROOTFS}/usr/bin/sudo

	gpasswd -a gumstix network
	gpasswd -a gumstix sudo

}


ROOTFS_POSTPROCESS_COMMAND =+ "set_gumstix_user"
