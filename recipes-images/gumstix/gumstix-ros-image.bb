DESCRIPTION = "The Gumstix Console Robot OS (ROS) image"

require gumstix-console-image.bb

ROS_INSTALL = "packagegroup-core-ssh-openssh cmake \
  python-modules python-misc python-empy python-setuptools \
  boost boost-dev python-dev libtinyxml libtinyxml-dev \
  log4cxx log4cxx-dev libbz2-dev \
  python-argparse python-rosdep python-wstool \
  roslaunch \
"

IMAGE_INSTALL += " \
  ${ROS_INSTALL} \
 "
