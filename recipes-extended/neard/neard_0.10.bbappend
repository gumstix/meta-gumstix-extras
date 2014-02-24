DESCRIPTION = "Disable NFC service by default as Gumstix do not have NFC modules"
SECTION = "base"
LICENSE = "MIT"
PR = "1"

SYSTEMD_AUTO_ENABLE_neard = "disable"
