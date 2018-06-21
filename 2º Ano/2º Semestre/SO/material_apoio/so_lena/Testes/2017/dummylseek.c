
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>


ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while (i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');

	if (r == -1) {
		perror("Erro na leitura!");
		exit(-1);
	}

	buffer[i] = '\0';

	return i;
}

void main(int argc, char** argv) {
	/*int fd = open("dummy.txt", O_RDONLY, 0600);
	int r;
	char buffer[PIPE_BUF];
	lseek(fd, 4, SEEK_SET);
	lseek(fd, 4, SEEK_CUR);
	while((r = readln(fd, buffer, PIPE_BUF)) > 0) {
		write(1, buffer, r);
	}*/
	int fd = open("dummy.txt", O_RDONLY, 0600);
	fd = access("dummy.txt", X_OK);
	printf("%d\n", fd);
	exit(0);
}