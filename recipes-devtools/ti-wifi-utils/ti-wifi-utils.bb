# Recipe copied from meta-arago and tweaked. This is only really needed
# for the calibrator utility to adjust the mac address on Wilink8
# systems e.g.:
#
# $ calibrator set nvs_mac /lib/firmware/ti-connectivity/wl1271-nvs.bin 00:00:00:00:00:00
#
# See http://processors.wiki.ti.com/index.php/WL18xx_Writing_MAC_address 
DESCRIPTION = "The calibrator and other useful utilities for TI wireless solution based on wl12xx driver"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=4725015cb0be7be389cf06deeae3683d"

DEPENDS = "libnl"

# Use the latest version at 21 Jan 2015
SRCREV = "42c49d2bac2dcac3b45beab9971e6763419377a9"
SRC_URI = "git://git.ti.com/wilink8-wlan/18xx-ti-utils.git"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "'CC=${CC}' 'CFLAGS=${CFLAGS} -I${STAGING_INCDIR}/libnl3 -DCONFIG_LIBNL32' 'LDFLAGS=${LDFLAGS}' 'LIBS=-lm -lnl-3 -lnl-genl-3'"

# only install 'calibrator' utility. UIM provided by another recipe.
do_install() {
    install -d ${D}${bindir}
    install -m 0755 calibrator ${D}${bindir}/
}
