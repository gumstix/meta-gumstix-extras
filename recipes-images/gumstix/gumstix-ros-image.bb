DESCRIPTION = "The gumstix console image with ROS preinstalled"
LICENSE = "MIT"

require gumstix-console-image.bb

IMAGE_INSTALL += " \
   packagegroup-ros-world-foxy \
"
