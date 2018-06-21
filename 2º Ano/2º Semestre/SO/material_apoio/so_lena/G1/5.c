#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

ssize_t readln(int fildes, void* buf, size_t nbyte) {
	int i = 0, r;
	char* buffer = buf;
	while (i < nbyte && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Falha na leitura!\n");
		exit(-1);
	}
	buffer[i] = '\0';
	return i;
}

int main(int argc, char** argv) {
	char buf[PIPE_BUF];
	int r;
	if ((r = readln(0, buf, PIPE_BUF)) > 0) { // só lê mesmo uma linha
		write(1, buf, r);
	}
	exit(0);
}