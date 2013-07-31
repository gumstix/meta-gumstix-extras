DESCRIPTION = "The Gumstix XFCE image.  This provides simple desktop \
environment using X11."
LICENSE = "MIT"

IMAGE_FEATURES += "x11"

require gumstix-console-image.bb

IMAGE_INSTALL += " \
  epiphany \
  gdm \
  man \
  man-pages \
  network-manager-applet \
  packagegroup-xfce-extended \
  packagegroup-xfce-multimedia \
  polkit-gnome \
  polkit-group-rule-network \
  polkit-group-rule-datetime \
  polkit-group-rule-shutdown \
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
}


#ROOTFS_POSTPROCESS_COMMAND =+ "set_gumstix_user"
