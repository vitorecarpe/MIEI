#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */
#include <limits.h>
#include <signal.h>

/* Desenvolva um comando atrasa com um argumento
 * numérico que, quando devidamente invocado
 * (ex: cat /etc/passwd | atrasa 10) apresenta
 * em STDOUT o input presente em STDIN, introduzindo
 * no entanto um atraso temporal em segundos entre
 * cada linha, consoante o argumento especificado.
 * O envio dos sinais SIGUSR1 e SIGUSR2 a um processo
 * atrasa deve permitir aumentar e diminuir o atraso
 * em 1 segundo, respetivamente.
 */

int nSecs;
char buffer[PIPE_BUF];
int r;

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

void aumenta(int signum) {
	nSecs++;
}

void diminui(int signum) {
	nSecs--;
}

void lala(int signum) {
	write(1, buffer, r);
}

void main(int argc, char** argv) {
	nSecs = atoi(argv[1]);

	signal(SIGALRM, lala);
	signal(SIGUSR1, aumenta);
	signal(SIGUSR2, diminui);

	while((r = readln(0, buffer, PIPE_BUF)) > 0) {
		alarm(nSecs);
		pause(); // pause()  causes the calling process (or thread) to sleep until a signal
                 // is delivered that either terminates the process or causes  the  invoca‐
                 // tion of a signal-catching function.
	}

	exit(0);
}
