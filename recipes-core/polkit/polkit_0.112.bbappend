pkg_postinst_${PN} () {
	#!/bin/sh -e
	if [ x"$D" = "x" ]; then
		gpasswd -a gumstix network
		gpasswd -a gumstix sudo
	else
		exit 1
	fi
}

