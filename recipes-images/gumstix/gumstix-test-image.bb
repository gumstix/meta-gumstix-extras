DESCRIPTION = "A Gumstix console image with some hardware testing utilities."

require gumstix-console-image.bb


IMAGE_INSTALL += " \
  mplayer2 \
  e2eaudiotest \
  spidev-test \
  utouch-mtview \
"
