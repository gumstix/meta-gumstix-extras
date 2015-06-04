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
branch: dizzy
revision: 9c4ff467f66428488b1cd9798066a8cb5d6b4c3b

URI: git://git.openembedded.org/meta-openembedded.git
branch: dizzy
revision: 5b6f39ce325d490fc382d5d59c5b8b9d5fa38b38

URI: git://github.com/gumstix/meta-gumstix.git
branch: dizzy
revision: 2a5c6f15f84dbce7a8aba613c6cdd8a1047efc27

URI: git://git.yoctoproject.org/meta-java.git
branch: master
revision: 4aea395deed99b2725b56d4a86fb4712fcf504d7

URI: git://github.com/bmwcarit/meta-ros.git
branch: master
revision: ca9e784210a89dc1c53054e4c597bfb463f72ef0

URI: git://git.yoctoproject.org/meta-ti.git
branch: master
revision: cf929ea6564564458eeabfd4fb4d42a86b20cd85

Layer maintainer: Adam Lee <adam@gumstix.com> for Gumstix, Inc.
