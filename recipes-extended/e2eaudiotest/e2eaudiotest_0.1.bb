DESCRIPTION = "Audio Loopback Tool for Testing"
SECTION = "testing"
# See http://bazaar.launchpad.net/~linaro-validation/lava-test/trunk/view/head:/lava_test/test_definitions/e2eaudiotest.py
LICENSE = "GPL-1.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-1.0;md5=e9e36a9de734199567a4d769498f743d"
PR = "r1"

DEPENDS = "fftw"
REDEPENDS = "alsa-utils"

SRC_URI = "git://git.linaro.org/people/kurt-r-taylor/e2eaudiotest.git;protocol=git;tag=d1669a6974a189d8c2f8df66068781cff1692f19 \
           file://0001-Assume-testfreq-is-on-the-systempath.patch"

S = "${WORKDIR}/git/"

do_compile() {
    ${CC} testfreq.c utils_alsa.c -lm -lasound -lfftw3 -o testfreq
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 testfreq ${D}${bindir}
    install -m 0755 e2eaudiotest.sh ${D}${bindir}
}

