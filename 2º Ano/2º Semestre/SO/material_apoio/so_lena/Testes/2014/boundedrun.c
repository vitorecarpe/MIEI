#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>

/* Implemente um programa boundedrun que recebe como
 * argumentos dois inteiros (time e size) e outro
 * comando e seus argumentos.
 * E.g. "boundedrun 15 1024 du /"
 * O boundedrun deve escrever no standard output o
 * que o comando escrever, até um limite de size
 * bytes, ou até passarem time segundos.
 * Quando um destes limites é alcançado o comando deve
 * ser forçado a terminar.
 */

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

int pidch; 

void termina(int signum) {
	kill(pidch, SIGKILL);
}

void main(int argc, char** argv) {
	int nSecs = atoi(argv[1]), nBytes = atoi(argv[2]), i, r;
	int count = 0;
	signal(SIGALRM, termina);
	int mp[2];
	pipe(mp);
	alarm(nSecs);
	char buffer[PIPE_BUF];
	int p = fork();

	if (p == 0) {
		close(mp[0]);
		dup2(mp[1], 1); close(mp[1]);
		execvp(argv[3], argv + 3);
		exit(-1);
	} else {
		pidch = p;
		close(mp[1]);
	}
	while (nBytes > 0 && (r = readln(mp[0], buffer, PIPE_BUF)) > 0) {
		int save = nBytes;
		nBytes -= r;
		if (nBytes < 0) {
			write(1, buffer, save);
			kill(pidch, SIGKILL);
		} else write(1, buffer, r);
	}
	exit(0);
}