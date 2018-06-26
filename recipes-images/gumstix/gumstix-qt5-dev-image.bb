DESCRIPTION = "A Gumstix console image with Qt5 development tools"

require gumstix-console-image.bb

IMAGE_FEATURES += "tools-debug tools-sdk"

QT_INSTALL = " \
  qtbase \
  qtbase-dev \
  qtbase-mkspecs \
  qtbase-plugins \
  qtbase-tools \
  packagegroup-qt5-qtcreator-debug \
  packagegroup-qt5-toolchain-target  \
  ttf-dejavu-common \
"

IMAGE_INSTALL += " \
  ${QT_INSTALL} \
"

QT_INSTALL_remove_rpi += " \
  packagegroup-qt5-qtcreator-debug \
"

# For Pepper SGX-Qt5
# IMAGE_INSTALL_append_pepper = " \
#   libgles-omap3 \
#   libgles-omap3-rawdemos \
#   omap3-sgx-modules \
#   qt5-demo-extrafiles \
#   qt5everywheredemo \
# "

# For Overo SGX-Qt5
# IMAGE_INSTALL_append_overo = " \
#   libgles-omap3 \
#   libgles-omap3-rawdemos \
#   omap3-sgx-modules \
#   qt5-demo-extrafiles \
#   qt5everywheredemo \
# "
