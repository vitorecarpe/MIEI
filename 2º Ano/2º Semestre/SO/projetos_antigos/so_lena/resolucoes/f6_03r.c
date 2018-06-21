#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>


int main () {
	int o,w,r;
	o = open("fifo",O_RDONLY);
	printf("%d\n",o );
	w = open("log.txt", O_CREAT | O_WRONLY, 0666);
	char buf [10];
	printf("wtf\n");
	exit(0);
	while( (r = read(o,buf,10)) > 0)
		printf("wtf\n");
		write(w,buf,r);
	exit(0);
}