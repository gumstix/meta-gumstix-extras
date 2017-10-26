# bbappend for removing duplicate patch and then add the patch back
# meta layer and meta-fsl-bsp-release provide the same patch

SRC_URI_remove_mx6 = "file://0012-Fix-gles-configurability.patch"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx6 = " \
    file://0012-Fix-gles-configurability.patch \
"
