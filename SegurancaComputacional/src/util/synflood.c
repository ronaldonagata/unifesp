#include <unistd.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/ip.h>
#include <netinet/tcp.h>

// Packet length
#define TAMANHO_PACOTE 8192

struct ipheader 
{
 unsigned char      iph_ihl:5, /* Little-endian */
                    iph_ver:4;
 unsigned char      iph_tos;
 unsigned short int iph_len;
 unsigned short int iph_ident;
 unsigned char      iph_flags;
 unsigned short int iph_offset;
 unsigned char      iph_ttl;
 unsigned char      iph_protocol;
 unsigned short int iph_chksum;
 unsigned int       iph_sourceip;
 unsigned int       iph_destip;
};


struct tcpheader 
{
 unsigned short int tcph_srcport;
 unsigned short int tcph_destport;
 unsigned int       tcph_seqnum;
 unsigned int       tcph_acknum;
 unsigned char      tcph_reserved:4, tcph_offset:4;

  unsigned int
       tcp_res1:4,       /*little-endian*/
       tcph_hlen:4,      /*length of tcp header in 32-bit words*/
       tcph_fin:1,       /*Finish flag "fin"*/
       tcph_syn:1,       /*Synchronize sequence numbers to start a connection*/
       tcph_rst:1,       /*Reset flag */
       tcph_psh:1,       /*Push, sends data to the application*/
       tcph_ack:1,       /*acknowledge*/
       tcph_urg:1,       /*urgent pointer*/
       tcph_res2:2;

 unsigned short int tcph_win;
 unsigned short int tcph_chksum;
 unsigned short int tcph_urgptr;
};

unsigned short csum(unsigned short *buf, int len)
{
	unsigned long sum;

	for(sum=0; len>0; len--)
	{
		sum += *buf++;
	}
	
	sum = (sum >> 16) + (sum &0xffff);
	sum += (sum >> 16);

	return (unsigned short)(~sum);
}

int main(int argc, char *argv[])
{

int sd;
char buffer[TAMANHO_PACOTE];
srand( (unsigned)time(NULL) );

struct ipheader *ip = (struct ipheader *) buffer;
struct tcpheader *tcp = (struct tcpheader *) (buffer + sizeof(struct ipheader));
struct sockaddr_in sin, din;

int one = 1;
const int *val = &one;

memset(buffer, 0, TAMANHO_PACOTE);

if(argc != 5)
{
	printf("- Usage: %s <IP-Origem> <Port-Origem> <IP-Destino> <Porta-Destino>\n", argv[0]);
	exit(-1);
}

sd = socket(PF_INET, SOCK_RAW, IPPROTO_TCP);

if(sd < 0)
{
   perror("socket() error");
   exit(-1);
}
 
sin.sin_family = AF_INET;
din.sin_family = AF_INET;

sin.sin_port = htons(atoi(argv[2]));
din.sin_port = htons(atoi(argv[4]));

sin.sin_addr.s_addr = inet_addr(argv[1]);
din.sin_addr.s_addr = inet_addr(argv[3]);

ip->iph_ihl = 5;
ip->iph_ver = 4;
ip->iph_tos = 16;
ip->iph_len = sizeof(struct ipheader) + sizeof(struct tcpheader);
ip->iph_ident = htons(54321);
ip->iph_offset = 0;
ip->iph_ttl = 64;
ip->iph_protocol = 6; 
ip->iph_chksum = 0; 
ip->iph_sourceip = inet_addr(argv[1]);
ip->iph_destip = inet_addr(argv[3]);
tcp->tcph_srcport = htons(atoi(argv[2]));

tcp->tcph_destport = htons(atoi(argv[4]));
tcp->tcph_seqnum = htonl(1);
tcp->tcph_acknum = 0;
tcp->tcph_offset = 5;
tcp->tcph_syn = 1;
tcp->tcph_ack = 0;
tcp->tcph_win = htons(32767);
tcp->tcph_chksum = 0; 
tcp->tcph_urgptr = 0;

ip->iph_chksum = csum((unsigned short *) buffer, (sizeof(struct ipheader) + sizeof(struct tcpheader)));

if(setsockopt(sd, IPPROTO_IP, IP_HDRINCL, val, sizeof(one)) < 0)
{
    perror("Erro Socket");
    exit(-1);
}

unsigned int i;

for(i = 0; i < 20; i++)
{
	tcp->tcph_destport = htons(rand()%100000); // Generate random ips
	if(sendto(sd, buffer, ip->iph_len, 0, (struct sockaddr *)&sin, sizeof(sin)) < 0)
	{
	   perror("Erro em sendto()");
	   exit(-1);
	}
	sleep(5);
}

close(sd);

return 0;

}