PACKAGECONFIG_FB = "linuxfb"

# For Pepper SGX-Qt5
# PACKAGECONFIG_GL_pepper = "gles2 linuxfb"

# For Overo SGX-Qt5
# PACKAGECONFIG_GL_overo = "gles2 linuxfb"

# In Krogoth Branch, this patch has been applied upstream, remove it to build imx6scm qt5 image
SRC_URI_remove = " \
    file://fix-eglfs_kms_egldevice-build-error.patch \
"
