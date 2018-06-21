#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>


int main () {
	int o,r;
	o = open("fifo",O_RDONLY);
	char buf [10];
	while( (r = read(o,buf,10)) > 0)
		write(1,buf,r);
	exit(0);
}