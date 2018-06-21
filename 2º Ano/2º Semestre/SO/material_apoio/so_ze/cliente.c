#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>

int main(int argc, char const *argv[]){

	int fd, i;

	fd = open ("fifo", O_WRONLY);

	for (i = 1; i < argc; i++){
		write(fd, argv[i], strlen(argv[i]));
		write(fd, "\n", strlen("\n"));
		
	}

	

	close(fd);

	return 0;
}