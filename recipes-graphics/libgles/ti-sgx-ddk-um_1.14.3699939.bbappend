do_install_append() {
    touch ${D}${libdir}/ES5.0/ld-linux.so.3
    touch ${D}${libdir}/ES5.0/ld-linux.so.3\(GLIBC_2.4\)
    cp -pPR ${S}/gfx_rel_es5.x/libews.so ${D}${libdir}/ES5.0/
}

RPROVIDES_${PN}-es5 += "ld-linux.so.3 ld-linux.so.3(GLIBC_2.4) libews.so"

pkg_postinst_${PN}-es5() {
rm -f $D${libdir}/ES5.0/ld-linux.so.3*
rm -f $D${libdir}/ES5.0/libews.so
}
