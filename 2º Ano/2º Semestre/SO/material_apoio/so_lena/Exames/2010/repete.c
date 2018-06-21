#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>


ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while (i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');

	if (r == -1) {
		perror("Erro na leitura!");
		exit(-1);
	}
	buffer[i-1] = '\0';
	return i;
}

void main(int argc, char** argv) {
	int i, mp[2], fd_r[argc - 1], fd_w[argc - 1], r, status;
	char buffer[PIPE_BUF];

	for (i = 1; i < argc; i++) {
		pipe(mp);
		fd_w[i - 1] = mp[1];
		pid_t p = fork();

		if (p == 0) {
			close(fd_w[i - 1]);
			dup2(mp[0], 0); close(mp[0]);
			execlp(argv[i], argv[i], NULL);
			exit(-1);
		} else {
			close(mp[0]);
		}
	}

	while((r = readln(0, buffer, PIPE_BUF)) > 0) {
		for (i = 1; i < argc; i++) {
			write(fd_w[i - 1], buffer, r);
		}
	}

	for (i = 1; i < argc; i++) {
		close(fd_w[i - 1]);
	}

	for (i = 1; i < argc; i++) {
		wait(NULL);
	}
}