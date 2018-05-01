do_configure_append(){
    if [ -f "/usr/bin/javac" ] || [ -f "/usr/bin/java" ]
    then
        ln -f -s /usr/bin/javac ${WORKDIR}/recipe-sysroot-native/usr/bin/
        ln -f -s /usr/bin/java ${WORKDIR}/recipe-sysroot-native/usr/bin/
    else
        bberror "JDK not installed"
    fi
}

