DESCRIPTION = "A Gumstix console image with ROS (Robot OS) installed"

IMAGE_FEATURES += "tools-sdk dev-pkgs"

require gumstix-console-image.bb


IMAGE_INSTALL += " \
  packagegroup-ros-comm \
  python-wstool \
  python-modules \
  python-misc \
  python-setuptools \
  python-dev \
  log4cxx-dev \
  libbz2-dev \
  libtinyxml-dev \
"
