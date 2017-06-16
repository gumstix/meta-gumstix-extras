DESCRIPTION = "Implements VDPAU library"
HOMEPAGE = "http://people.freedesktop.org"
LICENSE = "MIT"
DEPENDS = "xtrans libx11 libxext libice libsm libxscrnsaver libxt \
	   libxmu libxpm libxau libxfixes libxcomposite libxrender \
	   libxcursor libxdamage libfontenc libxfont libxft libxi \
	   libxinerama libxrandr libxres libxtst libxv libxvmc \
	   libxxf86dga libxxf86vm libdmx libpciaccess libxkbfile \
	   dri2proto \
	   "
LIC_FILES_CHKSUM = "file://COPYING;md5=83af8811a28727a13f04132cc33b7f58"

SRC_URI = "http://people.freedesktop.org/~aplattner/vdpau/libvdpau-${PV}.tar.gz"
SRC_URI[md5sum] = "38d362869f1da5516f0f927db4d606c3"
SRC_URI[sha256sum] = "aea4e783f220bf26ba2139ccd866a0ee5005fa03af5e08c41fbc939118263919"

inherit autotools pkgconfig

S = "${WORKDIR}/libvdpau-${PV}"

FILES_${PN} += "${libdir}/vdpau/libvdpau_nouveau${SOLIBS} \
		${libdir}/vdpau/libvdpau_r600${SOLIBS} \
		${libdir}/vdpau/libvdpau_radeonsi${SOLIBS} \
		${libdir}/vdpau/libvdpau_trace${SOLIBS} \
	       "

FILES_${PN}-dev += "${libdir}/vdpau/libvdpau_nouveau${SOLIBSDEV} \
		    ${libdir}/vdpau/libvdpau_nouveau.la \
		    ${libdir}/vdpau/libvdpau_r600${SOLIBSDEV} \
		    ${libdir}/vdpau/libvdpau_r600.la \
		    ${libdir}/vdpau/libvdpau_radeonsi${SOLIBSDEV} \
		    ${libdir}/vdpau/libvdpau_radeonsi.la \
		    ${libdir}/vdpau/libvdpau_trace${SOLIBSDEV} \
		    ${libdir}/vdpau/libvdpau_trace.la \
		   "

FILES_${PN}-dbg += "${libdir}/vdpau/.debug"

EXTRA_OECONF += "--enable-dri2"

# This bbappend file is for fixing exsiting error only
# If it does not apply to the newer version, it can be removed
# The error here is missing package for vlc for OE
