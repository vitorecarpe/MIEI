#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>

int main(int argc, char const *argv[]){

	mkfifo("fifo", 0600);
	int fd, n;
	char buf [100];

	while (1){
		fd = open ("fifo", O_RDONLY);

		while ((n = read(fd, buf, 10)) > 0){
			buf[n] = '\0';
			printf("%s\n", buf);
		} 
		close(fd);
	}
	
	return 0;
}