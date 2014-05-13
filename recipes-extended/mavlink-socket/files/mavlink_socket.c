/******************************************************************************
 Copyright (C) 2014  Andrew C. Smith
 Copyright (C) 2010  Bryan Godbolt godbolt ( a t ) ualberta.c
 (c) 2009, 2010 MAVCONN PROJECT  <http://MAVCONN.ethz.ch
 http://qgroundcontrol.org/dev/mavlink_linux_integration_tutorial
 http://github.com/mavlink/mavlink/examples/linux/mavlink_udp.c

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 
 ****************************************************************************/
/*
 This program sends relays data from the DuoVero UART to qgroundcontrol using the mavlink protocol over UDP.
 The sent packets cause qgroundcontrol to respond with heartbeats.  Any settings or custom commands sent from
 qgroundcontrol are printed by this program along with the heartbeats.
  
 I compiled this program sucessfully on the DuoVero with the following command
 
 gcc -I include/common -I /usr/include/glib-2.0/ -I /usr/lib/glib-2.0/include/ -lglib-2.0 -o mavlink_server mavlink_duovero.c
 
 the rt library is needed for the clock_gettime on linux
 */
#include "mavlink.h"
#include <glib.h>

// Standard includes
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <inttypes.h>
// Serial includes
#include <stdio.h>   /* Standard input/output definitions */
#include <string.h>  /* String function definitions */
#include <unistd.h>  /* UNIX standard function definitions */
#include <fcntl.h>   /* File control definitions */
#include <errno.h>   /* Error number definitions */
#include <termios.h> /* POSIX terminal control definitions */
// UDP includes
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#ifdef __linux
#include <sys/ioctl.h>
#endif

// Latency Benchmarking
#include <sys/time.h>
#include <time.h>

int sock;
struct sockaddr_in gcAddr; 
struct sockaddr_in locAddr;
//int debug = 1;
int debug = 0;

/* QNX timer version */
#if (defined __QNX__) | (defined __QNXNTO__)
uint64_t microsSinceEpoch()
{

	struct timespec time;
	
	uint64_t micros = 0;
	
	clock_gettime(CLOCK_REALTIME, &time); 
	micros = (uint64_t)time.tv_sec * 100000 + time.tv_nsec/1000;
	
	return micros;
}
#else
uint64_t microsSinceEpoch()
{
	
	struct timeval tv;
	
	uint64_t micros = 0;
	
	gettimeofday(&tv, NULL); 
	micros =  ((uint64_t)tv.tv_sec) * 1000000 + tv.tv_usec;
	
	return micros;
}
#endif

/**
*
* Open the UDP socket, return -1 on error
*
*/

int open_UDP(char* target_ip)
{
	sock = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP);
	memset(&locAddr, 0, sizeof(locAddr));
	locAddr.sin_family = AF_INET;
	locAddr.sin_addr.s_addr = INADDR_ANY;
	locAddr.sin_port = htons(14551);
//	locAddr.sin_port = htons(14555);
	
	/* Bind the socket to port 14551 - necessary to receive packets from qgroundcontrol */ 
	if (-1 == bind(sock,(struct sockaddr *)&locAddr, sizeof(struct sockaddr)))
	{
		perror("error bind failed");
		close(sock);
		//exit(EXIT_FAILURE);
		return -1;
	} 
	
	/* Attempt to make it non blocking */
	/*if (fcntl(sock, F_SETFL, O_NONBLOCK | FASYNC) < 0)
	{
		fprintf(stderr, "error setting nonblocking: %s\n", strerror(errno));
		close(sock);
		exit(EXIT_FAILURE);
	}*/	
	
	memset(&gcAddr, 0, sizeof(gcAddr));
	gcAddr.sin_family = AF_INET;
	gcAddr.sin_addr.s_addr = inet_addr(target_ip);
	gcAddr.sin_port = htons(14550);

	return 0;
}

/**
 *
 *
 * Returns the file descriptor on success or -1 on error.
 */

int open_port(char* port)
{
	int fd; /* File descriptor for the port */

	// Open serial port
	// O_RDWR - Read and write
	// O_NOCTTY - Ignore special chars like CTRL-C
	fd = open(port, O_RDWR | O_NOCTTY | O_NDELAY);
	if (fd == -1)
	{
		/* Could not open the port. */
		return (-1);
	}
	else
	{
		fcntl(fd, F_SETFL, 0);
	}

	return (fd);
}

int setup_port(int fd, int baud, int data_bits, int stop_bits, int parity, int hardware_control)
{
	//struct termios options;

	struct termios config;
	if (!isatty(fd))
	{
		fprintf(stderr, "\nERROR: file descriptor %d is NOT a serial port\n", fd);
		return -1;//false;
	}
	if (tcgetattr(fd, &config) < 0)
	{
		fprintf(stderr, "\nERROR: could not read configuration of fd %d\n", fd);
		return -1;//false;
	}
	//
	// Input flags - Turn off input processing
	// convert break to null byte, no CR to NL translation,
	// no NL to CR translation, don't mark parity errors or breaks
	// no input parity check, don't strip high bit off,
	// no XON/XOFF software flow control
	//
	config.c_iflag &= ~(IGNBRK | BRKINT | ICRNL | INLCR | PARMRK | INPCK | ISTRIP | IXON);
	//
	// Output flags - Turn off output processing
	// no CR to NL translation, no NL to CR-NL translation,
	// no NL to CR translation, no column 0 CR suppression,
	// no Ctrl-D suppression, no fill characters, no case mapping,
	// no local output processing
	//
	config.c_oflag &= ~(OCRNL | ONLCR | ONLRET | ONOCR | OFILL | OPOST);

#ifdef OLCUC
	config.c_oflag &= ~OLCUC;
#endif

#ifdef ONOEOT
	config.c_oflag &= ~ONOEOT;
#endif

	//
	// No line processing:
	// echo off, echo newline off, canonical mode off,
	// extended input processing off, signal chars off
	//
	config.c_lflag &= ~(ECHO | ECHONL | ICANON | IEXTEN | ISIG);
	//
	// Turn off character processing
	// clear current char size mask, no parity checking,
	// no output processing, force 8 bit input
	//
	config.c_cflag &= ~(CSIZE | PARENB);
	config.c_cflag |= CS8;
	//
	// One input byte is enough to return from read()
	// Inter-character timer off
	//
	config.c_cc[VMIN] = 1;
	config.c_cc[VTIME] = 10; // was 0

	// Get the current options for the port
	//tcgetattr(fd, &options);

	switch (baud)
	{
	case 1200:
		if (cfsetispeed(&config, B1200) < 0 || cfsetospeed(&config, B1200) < 0)
		{
			fprintf(stderr, "\nERROR: Could not set desired baud rate of %d Baud\n", baud);
			return -1;
		}
		break;
	case 1800:
		cfsetispeed(&config, B1800);
		cfsetospeed(&config, B1800);
		break;
	case 9600:
		cfsetispeed(&config, B9600);
		cfsetospeed(&config, B9600);
		break;
	case 19200:
		cfsetispeed(&config, B19200);
		cfsetospeed(&config, B19200);
		break;
	case 38400:
		if (cfsetispeed(&config, B38400) < 0 || cfsetospeed(&config, B38400) < 0)
		{
			fprintf(stderr, "\nERROR: Could not set desired baud rate of %d Baud\n", baud);
			return -1;
		}
		break;
	case 57600:
		if (cfsetispeed(&config, B57600) < 0 || cfsetospeed(&config, B57600) < 0)
		{
			fprintf(stderr, "\nERROR: Could not set desired baud rate of %d Baud\n", baud);
			return -1;
		}
		break;
	case 115200:
		if (cfsetispeed(&config, B115200) < 0 || cfsetospeed(&config, B115200) < 0)
		{
			fprintf(stderr, "\nERROR: Could not set desired baud rate of %d Baud\n", baud);
			return -1;
		}
		break;

		// These two non-standard (by the 70'ties ) rates are fully supported on
		// current Debian and Mac OS versions (tested since 2010).
	case 460800:
		if (cfsetispeed(&config, B460800) < 0 || cfsetospeed(&config, B460800) < 0)
		{
			fprintf(stderr, "\nERROR: Could not set desired baud rate of %d Baud\n", baud);
			return -1;
		}
		break;
	case 921600:
		if (cfsetispeed(&config, B921600) < 0 || cfsetospeed(&config, B921600) < 0)
		{
			fprintf(stderr, "\nERROR: Could not set desired baud rate of %d Baud\n", baud);
			return -1;
		}
		break;
	default:
			fprintf(stderr, "ERROR: Desired baud rate %d could not be set, aborting.\n", baud);
			return -1;

			break;
	}

	//
	// Finally, apply the configuration
	//
	if (tcsetattr(fd, TCSAFLUSH, &config) < 0)
	{
		fprintf(stderr, "\nERROR: could not set configuration of fd %d\n", fd);
		return -1;
	}
	return 0;
}

void close_port(int fd)
{
	close(fd);
}

/**
 * @brief Serial function
 *
 * This function blocks waiting for serial data in it's own thread
 * and forwards the data once received.
 */
void* serial_wait(void* serial_ptr)
{
	int fd = *((int*)serial_ptr);
	mavlink_status_t lastStatus;
	uint8_t msgReceived;
	unsigned char v;
	unsigned int i;
	unsigned int messageLength, bytes_sent;
	uint8_t buffer[MAVLINK_MAX_PACKET_LEN];
	uint8_t cp;
	mavlink_message_t message;
	mavlink_status_t status;

	lastStatus.packet_rx_drop_count = 0;

	// Blocking wait for new data
	while (1)
	{
		// Block until data is available, read only one byte to be able to continue immediately
		msgReceived = 0;
		//tcflush(fd, TCIFLUSH);
		if (read(fd, &cp, 1) > 0)
		{
			// Check if a message could be decoded, return the message in case yes
			msgReceived = mavlink_parse_char(MAVLINK_COMM_1, cp, &message, &status);
			if (lastStatus.packet_rx_drop_count != status.packet_rx_drop_count)
			{
				if (debug)
					printf("ERROR: DROPPED %d PACKETS\n", status.packet_rx_drop_count);
				if (debug)
				{
					v = cp;
					fprintf(stderr, "%02x ", v);
				}
			}
			lastStatus = status;
		}
		else
		{
			if (debug)
			fprintf(stderr, "ERROR: Could not read from port\n");
		}

		// If a message could be decoded, handle it
		if (msgReceived)
		{
			messageLength = mavlink_msg_to_send_buffer(buffer, &message);
			// DEBUG output
			if (debug)
			{
				fprintf(stderr, "Forwarding SERIAL -> UDP: ");
				if (messageLength > MAVLINK_MAX_PACKET_LEN)
				{
					fprintf(stderr, "\nFATAL ERROR: MESSAGE LENGTH IS LARGER THAN BUFFER SIZE\n");
				}
				else
				{
					for (i = 0; i < messageLength; i++)
					{
						v = buffer[i];
						fprintf(stderr, "%02x ", v);
					}
					fprintf(stderr, "\n");
				}
			}

			// send the message over UDP			
			bytes_sent = sendto(sock, buffer, messageLength, 0, (struct sockaddr*)&gcAddr, sizeof(struct sockaddr_in));
		}
	}

	return NULL;
}

/**
 * @brief UDP function
 *
 * This function blocks waiting for udp data in it's own thread
 * and forwards the data once received.
 */
void* udp_wait(void* serial_ptr)
{
	int fd = *((int*)serial_ptr);
	mavlink_message_t message;
	mavlink_status_t status;
	uint8_t msgReceived = 0;
	unsigned char v;
	unsigned int i;
	uint8_t buffer[MAVLINK_MAX_PACKET_LEN];
	uint8_t udp_buffer[MAVLINK_MAX_PACKET_LEN];
	unsigned int messageLength, written, recvLen;

	mavlink_status_t lastStatus;
	lastStatus.packet_rx_drop_count = 0;

	// Blocking wait for new data
	while (1)
	{
		// Block until data is available, read all available data
		recvLen = recv(sock, udp_buffer, MAVLINK_MAX_PACKET_LEN, 0);
		if (recvLen > 0)
		{
			// Check if a message could be decoded, add each byte of packet one at a time and check if it completes the message 
			for (i=0; i < recvLen; i++)
			{
				msgReceived = mavlink_parse_char(MAVLINK_COMM_1, udp_buffer[i], &message, &status);
				if (lastStatus.packet_rx_drop_count != status.packet_rx_drop_count)
				{
					if (debug)
						printf("ERROR: DROPPED %d PACKETS\n", status.packet_rx_drop_count);
					if (debug)
					{
						v = udp_buffer[i];
						fprintf(stderr, "%02x ", v);
					}
				}
				lastStatus = status;

				// If a message could be decoded, handle it
		                if (msgReceived)
                		{
		                        messageLength = mavlink_msg_to_send_buffer(buffer, &message);
                		        // DEBUG output
		                        if (debug)
                		        {
                                		fprintf(stderr, "Forwarding UDP -> SERIAL: ");
		                                if (messageLength > MAVLINK_MAX_PACKET_LEN)
                		                {
                                		        fprintf(stderr, "\nFATAL ERROR: MESSAGE LENGTH IS LARGER THAN BUFFER SIZE\n");
		                                }
                		                else
		                                {
                		                        for (i = 0; i < messageLength; i++)
                                		        {
                                                		v = buffer[i];
		                                                fprintf(stderr, "%02x ", v);
                		                        }
                                		        fprintf(stderr, "\n");
						}
					}

		                        // relay the message over serial
                		        if (debug)
                                		printf("Writing %d bytes\n", messageLength);

		                        written = write(fd, (char*)buffer, messageLength);
                		        tcflush(fd, TCOFLUSH);

		                        if (messageLength != written)
                		                fprintf(stderr, "ERROR: Wrote %d bytes but should have written %d\n", written, messageLength);

		                }
			}
		}
		else
		{
			if (debug)
			fprintf(stderr, "ERROR: Could not read from udp port\n");
		}
	}

	return NULL;
}

int main(int argc, char* argv[])
{
	int fd;	
	char help[] = "--help";	
	char target_ip[100];
	char serial_port[128];
	int baud = 115200;
	GThread* serial_thread;
	GThread* udp_thread;
	GError* err;
	
	// Check if --help flag was used
	if ((argc == 2) && (strcmp(argv[1], help) == 0))
	{
		printf("\n");
		printf("\tUsage:\n\n");
		printf("\t");
		printf("%s", argv[0]);
		printf(" <ip address of QGroundControl> <serial port of MAV> <baud rate>\n");
		printf("\tDefault for localhost: udp-server 127.0.0.1 /dev/ttyS2 115200\n\n");
		exit(EXIT_FAILURE);
	}
	
	// Change the target ip if parameter was given
	strcpy(target_ip, "127.0.0.1");
	if (argc >= 2)
	{
		strcpy(target_ip, argv[1]);
	}

	// change the source serial port if parameter was given
	strcpy(serial_port, "/dev/ttyS2");
	if (argc >= 3)
	{
		strcpy(serial_port, argv[2]);
	}
	
	// check for baud rate input
	if (argc == 4)
	{
		baud = atoi(argv[3]);
	}

	// SETUP SERIAL PORT
	// Exit if opening port failed
	// Open the serial port.
	if (debug)
		printf("Trying to connect to %s.. ", serial_port);

	fd = open_port(serial_port);
	
	// problem opening port
	if (fd == -1)
	{
		if (debug)
			fprintf(stderr, "failure, could not open serial port.\n");
		exit(EXIT_FAILURE);
	}
	else
	{
		if (debug)
			printf("success opening port.\n");
	}
  
	// configure the port
	if (debug)
		printf("Trying to configure %s.. ", serial_port);

	if (setup_port(fd, baud, 8, 1, 0, 0) == -1)
	{
		if (debug)
			fprintf(stderr, "failure, could not configure port.\n");
		exit(EXIT_FAILURE);
	}
	else
	{
		if (debug)
			printf("success configuring serial port.\n");
	}

	// open the UDP port to qgroundcontrol
	if (open_UDP(target_ip) < 0)
	{
		if (debug)
			fprintf(stderr, "failure, could not open UDP port to %s.\n", target_ip);
		exit(EXIT_FAILURE);
	}
	else
	{
		if (debug)
			printf("success configuring udp socket.\n");
	}
	
	// start up blocking threads for serial and UDP channels
	// Run indefinitely while the UDP and serial threads handle the data
	if (debug)
		printf("\nREADY, waiting for serial and UDP data.\n");

	if ((serial_thread = g_thread_new("serial_thread",(GThreadFunc)serial_wait, (void *)&fd)) == NULL)
	{
		printf("Failed to create serial handling thread: %s!!\n", err->message);
		g_error_free(err);
	}
	if ((udp_thread = g_thread_new("udp_thread", (GThreadFunc)udp_wait, (void *)&fd)) == NULL)
	{
		printf("Failed to create udp handling thread: %s!!\n", err->message);
		g_error_free(err);
	}

	while (1)
	{
		usleep(10000);
	}
}
