#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>

int read_bytes;

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while (i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');

	if (r == -1) {
		perror("Erro de leitura!");
		exit(-1);
	}
	buffer[i] = '\0';
	return i;
}

void imprime() {
	printf("Leu %d bytes!\n", read_bytes);
	read_bytes = 0;
	alarm(1);
}

void main(int argc, char** argv) {
	int r, in[2], ex[2];
	pipe(in);
	pipe(ex);
	char buffer[PIPE_BUF];
	signal(SIGALRM, imprime);

	pid_t p = fork();

	if (p == 0) {
		close(in[0]);
		dup2(ex[1], 1); close(ex[1]);
		execlp(argv[1], argv[1], NULL);
		_exit(-1);
	} else {
		close(ex[1]);
		pid_t pid = fork();

		if (pid == 0) {
			close(ex[1]);
			dup2(in[0], 0); close(in[0]);
			execlp(argv[2], argv[2], NULL);
			_exit(-1);
		} //else close(in[0]);
	}

	alarm(1);
	while ((r = readln(ex[0], buffer, PIPE_BUF)) > 0) {
		read_bytes += r;
		write(in[1], buffer, r);
	}
	
	exit(0);
}