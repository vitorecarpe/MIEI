#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */
#include <limits.h>

void main(int argc, char** argv) {
	if (argc != 1 && argc != 2) {
		perror("Erro a passar os argumentos!");
		exit(1);
	}
	int r, fd;
	if (argc == 2) fd = open(argv[1], O_RDONLY, 0600);
	else fd = 0;
	char buf[PIPE_BUF];
	while ((r = read(fd, buf, 1)) > 0)
		write(1, buf, 1);
	if (r == -1) {
		perror("Ocorreu um erro na leitura!");
		exit(-1);
	}
	exit(0);
}