#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */
#include <limits.h>
#include <string.h>

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

void writeOrNot(char* file, char* equal, int fildes) {
	char buf[PIPE_BUF];
	int r;

	while((r = readln(fildes, buf, PIPE_BUF)) > 0) {
		//buf[r] = '\0';
		if(strstr(buf, equal)) {
			if (file != NULL) {
				char linha[PIPE_BUF];
				int nr = sprintf(linha, "%s: %s", file, buf);
				write(1, linha, nr);
			}
			else write(1, buf, r);
		}
	}
}

void main(int argc, char** argv) {
	int i;
	if (argc == 1) {
		perror("Argumentos errados!");
		exit(-1);
	}
	else if (argc == 2)
		writeOrNot(NULL, argv[1], 0);
	else {
		for (i = 2; i < argc; i++) {
			char* file = argv[i];
			int fd = open(file, O_RDONLY, 0600);
			writeOrNot(file, argv[1], fd);
		}
	}
}