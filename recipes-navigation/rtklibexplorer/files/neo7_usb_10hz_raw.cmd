# Set sample rate low while configuring receiver
!UBX CFG-RATE 1000 1 1

# turn on UBX RXM-RAW messages on USB
!UBX CFG-MSG 2 16 0 0 0 1 0 0
# turn on UBX RXM-SFRB messages on USB
!UBX CFG-MSG 2 17 0 0 0 1 0 0

# turn on UBX TIM TM2 messages on USB
!UBX CFG-MSG 13 3 0 0 0 1 0 0

#!UBX CFG-PRT 1 0 0 2256 115200 7 1 0 0

# turn off extra messages default messages
# NMEA GGA
!UBX CFG-MSG 240 0 0 0 0 0 0 0
# NMEA GLL
!UBX CFG-MSG 240 1 0 0 0 0 0 0
# NMEA GSA
!UBX CFG-MSG 240 2 0 0 0 0 0 0
# NMEA GSV
!UBX CFG-MSG 240 3 0 0 0 0 0 0
# NMEA RMC
!UBX CFG-MSG 240 4 0 0 0 0 0 0
# NMEA VTG
!UBX CFG-MSG 240 5 0 0 0 0 0 0
# NMEA ZDA
!UBX CFG-MSG 240 8 0 0 0 0 0 0
!UBX CFG-MSG 1 3 0 0 0 0 0 0
!UBX CFG-MSG 1 3 0 0 0 0 0 0
!UBX CFG-MSG 1 6 0 0 0 0 0 0
!UBX CFG-MSG 1 18 0 0 0 0 0 0
!UBX CFG-MSG 1 34 0 0 0 0 0 0
!UBX CFG-MSG 1 48 0 0 0 0 0 0
!UBX CFG-MSG 3 15 0 0 0 0 0 0
!UBX CFG-MSG 3 16 0 0 0 0 0 0
!UBX CFG-MSG 12 16 0 0 0 0 0 0
!UBX CFG-MSG 12 49 0 0 0 0 0 0
!UBX CFG-MSG 12 52 0 0 0 0 0 0
!UBX CFG-MSG 04 02 0 0 0 0 0 0
!UBX CFG-MSG 10 38 0 0 0 0 0 0

# Set sample rate to 10 Hz
!UBX CFG-RATE 100 1 1


@
!UBX CFG-RATE 1000 1 1
