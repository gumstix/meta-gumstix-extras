DESCRIPTION = "A Gumstix console image with some hardware testing utilities."

require gumstix-console-image.bb

IMAGE_FEATURES += "tools-testapps"

IMAGE_INSTALL += " \
  e2eaudiotest \
  spidev-test \
  utouch-mtview \
"
