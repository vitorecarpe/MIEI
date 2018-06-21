#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int r, i = 0;
	while (i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Erro de leitura!\n");
		exit(-1);
	}
	buffer[i] = '\0';
	return i;
}

char buffer[PIPE_BUF];
int r;
int seg_a;
int* fd_w;
int i;
int arg;

void texto(int sig) {
	if ((r = readln(0, buffer, PIPE_BUF)) > 0) {
		printf("%s", buffer);
		for (i = 1; i < arg; i++) {
			write(fd_w[i - 1], buffer, r);
		}
	}
}

void main(int argc, char** argv) {
	seg_a = 0;
	argc = argc;
	signal(SIGALRM, texto);
	int pd[2], pid, status;
	fd_w = malloc((sizeof(int)) * (argc - 1));
	int fd_r[argc - 1];

	for (i = 1; i < argc; i++) {
		pipe(pd);
		fd_w[i - 1] = pd[1];
		pid = fork();
		if (pid == 0) {
			close(fd_w[i - 1]);
			dup2(pd[0], 0); close(pd[0]);
			execlp(argv[i], argv[i], NULL);
			exit(-1);
		}
		else close(pd[0]);
	}

	int i = 0;

	while ((r = readln(0, buffer, PIPE_BUF)) > 0) {
		int n = atoi(buffer) - seg_a;
		seg_a = atoi(buffer);
		if (n == 0) kill(getpid(), SIGALRM);
		else {
			alarm(n);
			pause();
		}
	}

	for (i = 1; i < argc; i++) {
		close(fd_w[i - 1]);
	}
	exit(0);
}