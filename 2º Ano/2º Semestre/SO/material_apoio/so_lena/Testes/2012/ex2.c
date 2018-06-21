#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>


int permit;

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;
	while (i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Erro de leitura!\n");
		exit(-1);
	}
	buffer[i] = '\0';
	return i;
}

void sec(int sig) {
	if (permit == 1) {
		write(1, "PASSOU MAIS UM SEGUNDO\n", 23);
	}
	alarm(1);
}

void permission(int sig) {
	if (sig == 10) permit = 1;
	else if (sig == 12) permit = 0;
}

void main(int argc, char** argv) {
	permit = 0;
	int r;
	char buffer[PIPE_BUF];
	signal(SIGUSR1, permission);
	signal(SIGUSR2, permission);
	signal(SIGALRM, sec);

	int p = fork();
	if (p == 0) {
		alarm(1);
		while (1) {
			pause();
		}
	}
	else {
		while ((r = readln(0, buffer, PIPE_BUF)))
			write(1, buffer, r);
		if (r == 0) kill(p, SIGTERM);
	}
	exit(0);
}