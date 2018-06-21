#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */
#include <limits.h>

ssize_t readln(int fildes, char* buffer, size_t nbyte) {
	int i = 0, r;
	while (i < nbyte && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Falha na leitura!\n");
		exit(-1);
	}
	buffer[i] = '\n';
	return i;
}


void main(int argc, char** argv) {
	char buf[PIPE_BUF];
	int r = readln(0, buf, PIPE_BUF);
	if (r > 0) exit(1);
	else exit(0);
}
