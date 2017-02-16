DESCRIPTION = "The Gumstix XFCE image.  This provides simple desktop \
environment using X11."
LICENSE = "MIT"

IMAGE_FEATURES += "x11-base"

require gumstix-console-image.bb

IMAGE_INSTALL += " \
  florence \
  gnome-bluetooth \
  chromium \
  man \
  man-pages \
  network-manager-applet \
  packagegroup-xfce-extended \
  packagegroup-xfce-multimedia \
  polkit-gnome \
  polkit-group-rule-network \
  polkit-group-rule-datetime \
"

# Network Manager manages WPA supplicant---we don't need an interface-specific
# systemd service in this case.
zap_wlan0_wpa() {
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service
}

ROOTFS_POSTPROCESS_COMMAND =+ "zap_wlan0_wpa;"
