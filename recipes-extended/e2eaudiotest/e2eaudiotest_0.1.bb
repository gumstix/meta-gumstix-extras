DESCRIPTION = "Audio Loopback Tool for Testing"
SECTION = "testing"
# See http://bazaar.launchpad.net/~linaro-validation/lava-test/trunk/view/head:/lava_test/test_definitions/e2eaudiotest.py
LICENSE = "GPL-1.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-1.0;md5=e9e36a9de734199567a4d769498f743d"

DEPENDS = "fftw alsa-utils"
REDEPENDS = "alsa-utils"

SRC_URI[md5sum] = "8a0cd99fd4df441ea2a60a55a2b76ea1"
SRC_URI[sha256sum] = "4ad1c8a2a30d3926d5c588bb1133c0bc79d877371db0a938e29df4af56f03dbf"
SRC_URI = "http://source-cache.s3.amazonaws.com/e2eaudiotest.tar.gz"

S = "${WORKDIR}"

do_compile() {
    ${CC} testfreq.c utils_alsa.c -lm -lasound -lfftw3 -o testfreq
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 testfreq ${D}${bindir}
    install -m 0755 e2eaudiotest.sh ${D}${bindir}
}

