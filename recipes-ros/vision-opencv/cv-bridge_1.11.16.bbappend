do_configure_prepend() {
    sed -i -e 's/PYTHONLIBS_VERSION_STRING VERSION_LESS 3/0/g' ${WORKDIR}/vision_opencv-1.11.16/cv_bridge/CMakeLists.txt
}
