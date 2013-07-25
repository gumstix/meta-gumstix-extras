PRINC := "${@int(PRINC) + 1}"

pkg_postinst_smart-pm () {
     #!/bin/sh -e
     smart channel --add gumstix type=rpm-md name="Gumstix Package Repository" baseurl=http://package-cache.gumstix.org/
}
