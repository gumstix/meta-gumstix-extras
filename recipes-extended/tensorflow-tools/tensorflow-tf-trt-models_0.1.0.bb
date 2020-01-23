SUMMARY = "Tensorflow models with example scripts, credit to NVIDIA and JK Jung"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=720c330bf4f4d0300ad6642ec1a89893"

SRCREV = "${AUTOREV}" 
SRC_URI = "gitsm://github.com/jkjung-avt/tf_trt_models.git \
           file://0001-Hard-code-the-gstreamer-pipeline-to-use-nvarguscamer.patch \
           file://0002-Update-camera_tf_trt-script-to-allow-headless-stream.patch \
           file://0003-Turn-off-help-menu-when-in-stealth-mode.patch \
          "

S = "${WORKDIR}/git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

RDEPENDS_${PN} += " bash python "

do_install() {
	install -d ${D}/usr/local/tensorflow-tools/tf_trt_models

	cp -r ${S}/data ${D}/usr/local/tensorflow-tools/tf_trt_models
	cp -r ${S}/examples ${D}/usr/local/tensorflow-tools/tf_trt_models
	cp -r ${S}/scripts ${D}/usr/local/tensorflow-tools/tf_trt_models
	cp -r ${S}/tf_trt_models ${D}/usr/local/tensorflow-tools/tf_trt_models
	cp -r ${S}/third_party ${D}/usr/local/tensorflow-tools/tf_trt_models
	cp -r ${S}/utils ${D}/usr/local/tensorflow-tools/tf_trt_models
	cp -r ${S}/camera_tf_trt.py ${D}/usr/local/tensorflow-tools/tf_trt_models
	cp -r ${S}/install.sh ${D}/usr/local/tensorflow-tools/tf_trt_models
	cp -r ${S}/setup.py ${D}/usr/local/tensorflow-tools/tf_trt_models

	rm ${D}/usr/local/tensorflow-tools/tf_trt_models/third_party/models/research/domain_adaptation/domain_separation/_grl_ops.so
	rm ${D}/usr/local/tensorflow-tools/tf_trt_models/third_party/models/research/syntaxnet/tensorflow/tensorflow/compiler/aot/codegen_test_o.golden
}

FILES_${PN} += "/usr/local/tensorflow-tools/tf_trt_models/* "

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
