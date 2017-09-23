/*
 * imst_pepper.h
 *
 *  Created on: September 9, 2017 
 *      Author: smithandrewc
 *
 *  Modified imst_rpi.h
 *      Author: gonzalocasas
 */

#ifndef _IMST_PEPPER_H_
#define _IMST_PEPPER_H_

/* Human readable platform definition */
#define DISPLAY_PLATFORM "IMST + Gumstix Pepper"

/* parameters for native spi */
#define SPI_SPEED		8000000
#define SPI_DEV_PATH	"/dev/spidev1.0"
#define SPI_CS_CHANGE   0

/* parameters for a FT2232H */
#define VID		        0x0403
#define PID		        0x6014

#endif /* _IMST_PEPPER_H_ */
