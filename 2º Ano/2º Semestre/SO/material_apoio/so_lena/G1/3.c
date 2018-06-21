#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

void main(int argc, char** argv) {
	if (argc != 2) {
		perror("Erro ao passar os argumentos!");
		_exit(1);
	}
	int r, n = atoi(argv[1]);
	char* buf = malloc(n);
	while ((r = read(0, buf, n)) > 0)
		write (1, buf, r);

	free(buf);

	if (r == -1) {
		perror("Ocorreu um erro na leitura!");
		exit(1); // EXIT_FAILURE
	}
	
	exit(0); // EXIT_SUCCESS
}