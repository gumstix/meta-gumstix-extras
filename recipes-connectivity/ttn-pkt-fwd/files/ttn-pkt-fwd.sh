#!/bin/sh

# change this value to whatever GPIO is connected to the reset pin
RESET_GPIO=$(grep -A3 'RESET_GPIO:' /etc/ttn-pkt-fwd/ttn-config.yml | tail -n1 | awk '{ print $2}')

# export the reset gpio
echo $RESET_GPIO > /sys/class/gpio/export
echo out > /sys/class/gpio/gpio$RESET_GPIO/direction

# pull it high and then low to reset the device
echo 1 > /sys/class/gpio/gpio$RESET_GPIO/value
sleep 1
echo 0 > /sys/class/gpio/gpio$RESET_GPIO/value

# start the packet forwarder
/usr/bin/packet-forwarder start --config /etc/ttn-pkt-fwd/ttn-config.yml

