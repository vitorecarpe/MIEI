#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>

/* AUXILIAR A CONTA */

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while(i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');

	if (r == -1) {
		perror("Erro de leitura!");
		exit(-1);
	}
	buffer[i] = '\n';
	return i;
}

void main(int argc, char** argv) {
	char* palavra = argv[1];
	char* fich = argv[2];
	int r;
	char buffer[PIPE_BUF];
	int fd = open(fich, O_RDONLY, 0600);

	while ((r = readln(fd, buffer, PIPE_BUF)) > 0) {
		buffer[r] = '\0';
		if (strstr(buffer, palavra) != NULL) {
			write(1, buffer, r);
		}
	}
	exit(0);
}