#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */
#include <limits.h>
#include <signal.h>

int nSecs;
char buffer[PIPE_BUF];
int r;

ssize_t readln(int fildes, char* buffer, size_t nbyte) {
	int i = 0, k;
    while (i < nbyte && (k = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
    if (k == -1) {
		perror("FALHA!\n");
		exit(-1);
	}
	buffer[i] = '\0';
	return i;
}

void aumenta() { nSecs++; }
void diminui() { nSecs--; }
void escreve() { write(1, buffer, r); }

void main(int argc, char** argv) {
	nSecs = atoi(argv[1]);

	signal(SIGALRM, escreve);
	signal(SIGUSR1, aumenta);
	signal(SIGUSR2, diminui);
    // signal(SIGINT, diminui); // para testar

	while((r = readln(0, buffer, PIPE_BUF)) > 0) {
		alarm(nSecs);
		pause();
	}
	exit(0);
}
