DESCRIPTION = "Auto load module i2c-dev"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

do_install() {
    install -d ${D}/etc/modules-load.d
    echo "i2c-dev" > ${D}/etc/modules-load.d/i2c-dev.conf
}
