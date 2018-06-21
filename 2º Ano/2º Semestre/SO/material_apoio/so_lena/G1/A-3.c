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
	buffer[i] = '\0';
	return i;
}

void get10Lines(int fildes) {
	char buf[PIPE_BUF];
	int l = 10;
	int r;
	while (l > 0 && ((r = readln(fildes, buf, PIPE_BUF)) > 0)) {
		write(1, buf, r);
		l--;
	}
}

void main(int argc, char** argv) {
	int i;
	if (argc == 1)
		get10Lines(0);
	else {
		for (i = 1; i < argc; i++) {
			char* file = argv[i];
			char line[PIPE_BUF];
			int fd = open(file, O_RDONLY, 0600);
			int nr = sprintf(line, ">== %s ==<\n", file);
			write(1, line, nr);
			get10Lines(fd);
		}
	}
}