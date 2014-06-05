FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Beagleboard is the supported platform that best matches our GL setup
EGLINFO_DEVICE = "beagleboard"

SRC_URI += " \
    file://0001-Cast-back-to-EGLNativeDisplay-type.patch \
"
