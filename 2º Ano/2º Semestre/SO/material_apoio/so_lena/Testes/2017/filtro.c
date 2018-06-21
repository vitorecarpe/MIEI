#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
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
	int nr;
	int w;
	char line[PIPE_BUF];
	while((nr = readln(0, line, PIPE_BUF)) > 0){	
		line[nr++] = '\0';
		if (strstr(line, "palavra") != NULL) w = write(1, line, nr);
	}
}
