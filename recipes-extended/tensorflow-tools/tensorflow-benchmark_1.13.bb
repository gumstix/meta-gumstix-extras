SUMMARY = "Tensorflow benchmark scripts"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

SRCREV = "${AUTOREV}" 
SRC_URI = "git://github.com/tensorflow/benchmarks.git;branch=cnn_tf_v1.13_compatible \
          "

S = "${WORKDIR}/git"

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
        install -d ${D}/usr/local/tensorflow-tools/benchmarks/scripts
	cp -r ${S}/scripts/* ${D}/usr/local/tensorflow-tools/benchmarks/scripts
}

FILES_${PN} += "/usr/local/tensorflow-tools/benchmarks/scripts/* "
