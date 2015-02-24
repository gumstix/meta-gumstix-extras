DESCRIPTION = "A Gumstix console image with some hardware testing utilities."

require gumstix-console-image.bb

IMAGE_FEATURES += "tools-testapps"

IMAGE_INSTALL += " \
  bluez-hcidump \
  e2eaudiotest \
  spidev-test \
  uart-loop-test \
  utouch-mtview \
"
