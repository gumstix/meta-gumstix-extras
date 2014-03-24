DESCRIPTION = "The Gumstix XFCE image.  This provides simple desktop \
environment using X11."
LICENSE = "MIT"

IMAGE_FEATURES += "x11"

require gumstix-console-image.bb

IMAGE_INSTALL += " \
  midori \
  man \
  man-pages \
  network-manager-applet \
  packagegroup-xfce-multimedia \
  polkit-gnome \
  polkit-group-rule-network \
  polkit-group-rule-datetime \
  polkit-group-rule-shutdown \
  mplayer2 \
"


