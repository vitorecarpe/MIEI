#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>

int main(){
	
	mkfifo("servidor1", 0622);

	int fl = open("log.txt", O_CREAT|O_WRONLY|O_TRUNC, 0600);

	char *buf[1024];
	
	while(1){

		int fd = open("servidor1", O_RDONLY);

		int n = read(fd, buf, 1024);

		while(n > 0){

			write(fl, buf, n);
			n = read(fd, buf, 1024);
		}

		close(fd);
	}
	return 0;
}