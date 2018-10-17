SUMMARY = "Build the go-lang tools govender and golint."
DESCRIPTION = "Build the go-lang tools govendor and golint."
LICENSE = "MIT"

S = "${WORKDIR}/git"

inherit native

DEPENDS += "\
	go-native \
"
do_compile() {
	export GOPATH="${S}/"
	go get -u github.com/kardianos/govendor
	go get -u golang.org/x/lint/golint
}

do_install() {
	mv "${S}/bin/"* "${bindir}/"
}
