Yocto layer for images and extra recipes for Gumstix products.

This repository is developed in the environment provided by the
[Gumstix Yocto manifest][yocto-manifest] repository.  New code is
developed on the *next* branch corresponding to the *dev* branch of the
manifest and, when appropriate, merged back into the current release
branch called out by the *master* branch of the manifest. Older
releases are tagged by name in the manifest repository and use the
release-specific branches found in this repository.

This repository is discussed on the [Gumstix Mailing List][mailing-list]
and feature-requests/issues can be raised against the top-level
[manifest][yocto-manifest] project.

[yocto-manifest]: https://github.com/gumstix/yocto-manifest
[mailing-list]: https://lists.sourceforge.net/lists/listinfo/gumstix-users

This layer depends on:

URI: git://git.yoctoproject.org/poky.git
branch: krogoth
revision: HEAD

URI: git://git.openembedded.org/meta-openembedded.git
branch: krogoth
revision: HEAD

URI: git://github.com/gumstix/meta-gumstix.git
branch: krogoth
revision: HEAD

URI: git://github.com/bmwcarit/meta-ros.git
branch: master
revision: eb4f9edf2af38a854a2a8d28e45ea02f6bdb7f12

URI: git://git.yoctoproject.org/meta-java.git
branch: master
revision: 33ddb28a6428b02ddcc82d1954ecf27cd426fbb5

URI: git://git.yoctoproject.org/meta-ti.git
branch: krogoth
revision: HEAD

URI: git://github.com/meta-qt5/meta-qt5.git
branch: krogoth
revision: HEAD

URI: git://github.com/meta-qt5/meta-qt4.git
branch: krogoth
revision: HEAD

URI: git://github.com/OSSystems/meta-browser.git
branch: krogoth
revision: 393d2aa15da21ffa532c3cd77d8cb91de997cd31

URI: git://github.com/Freescale/meta-fsl-arm-extra.git
branch: krogoth
revision: HEAD

URI: git://github.com/Freescale/meta-fsl-demos.git
branch: krogoth
revision: HEAD

URI: git://git.yoctoproject.org/meta-fsl-arm.git
branch: krogoth
revision: HEAD

URI: git://git.freescale.com/imx/meta-fsl-bsp-release.git
branch: krogoth_4.1.15-2.0.1
revision: HEAD

URI: git://git.freescale.com/imx/meta-nxp-imx-scm
branch: imx-4.1.15-2.0.0_ga
revision: HEAD

Layer maintainer: Jerry Hung <jerry@gumstix.com>, Jason Liu <jason.liu@gumstix.com> for Gumstix, Inc.
