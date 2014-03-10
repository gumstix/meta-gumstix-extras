#! /bin/sh
#
# (c) Copyright 2014 Gumstix, Inc.
# Licensed under terms of GPLv2
#
# Based on mk2PartSDCard.sh from Texas Instrument
# http://processors.wiki.ti.com/index.php/How_to_Make_3_Partition_SD_Card
#
# example usage: $ sudo mk2partsd /dev/sdb

DRIVE=$1

dd if=/dev/zero of=$DRIVE bs=1024 count=1024

SIZE=`fdisk -l $DRIVE | grep Disk | awk '{print $5}'`

echo DISK SIZE - $SIZE bytes

CYLINDERS=`echo $SIZE/255/63/512 | bc`

sfdisk --force -D -uS -H 255 -S 63 -C $CYLINDERS $DRIVE << EOF
128,130944,0x0C,*
131072,,,-
EOF

if [ -b ${1}1 ]; then
	mkfs.vfat -F 32 -n "boot" ${DRIVE}1 
else 
	mkfs.vfat -F 32 -n "boot" ${DRIVE}p1
fi

if [ -b ${1}2 ]; then
	mkfs.ext3 -L "rootfs" ${DRIVE}2
else 
	mkfs.ext3 -L "rootfs" ${DRIVE}p2
fi
