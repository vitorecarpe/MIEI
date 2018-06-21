#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>
#include <limits.h>

/* Considere a existência de um comando encontra, que
 * tendo como argumentos uma palavra e um nome de um
 * ficheiro, escreve no stdout as linhas do ficheiro
 * contendo a palavra. Implemente um programa conta
 * que, sendo invocado com conta palavra fich1..
 * fichn, e fazendo uso do comando acima, conte o
 * número total de linhas contendo palavra na lista
 * de ficheiros fich1...fichn.
 */

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
	int i, c = 0, r;
	char buffer[PIPE_BUF];
	int fd[argc - 2];

	for (i = 2; i < argc; i++) {
		int mp[2];
		pipe(mp);
		fd[i - 2] = mp[0];

		int p = fork();

		if (p == 0) {
			close(fd[i - 2]);
			dup2(mp[1], 1); close(mp[1]);
			execlp("./encontra", "./encontra", palavra, argv[i], NULL);
			exit(-1);
		}
		else close(mp[1]);
	}

	for (i = 2; i < argc; i++) {
		while ((r = readln(fd[i - 2], buffer, PIPE_BUF)) > 0)
			c++;
	}

	for (i = 2; i < argc; i++)
		close(fd[i - 2]);

	printf("Ocorrem %d vezes a palavra \"%s\".\n", c, palavra);
}