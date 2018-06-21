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
	buffer[i] = '\0';
	return i;
}

void main(int argc, char** argv) {
	int fd_r[argc - 1], fd_w[argc - 1], i, mp[2];
	int pid[argc];
	pid[0] = argc - 1;

	for (i = 1; i < argc; i++) {
		pipe(mp);
		fd_r[i - 1] = mp[0];
		fd_w[i - 1] = mp[1];
		pid_t p = fork();

		if (p == 0) {
			close(fd_r[i - 1]);
			dup2(fd_w[i - 1], 1); close(fd_w[i - 1]);
			execlp(argv[i], argv[i], NULL);
			exit(-1);
		} else {
			close(fd_w[i - 1]);
			pid[i] = p;
		}
	}

	int ant = 0;

	char buffer[PIPE_BUF];

	while (pid[0] > 0) {
		for (i = 1; i < argc; i++) {
			if (pid[i] != -1) {
				int r;
				if (ant == 1) {
					write(1, "OVER!\n", 6);
					ant = 0;
				}
				if ((r = readln(fd_r[i - 1], buffer, PIPE_BUF)) > 0) {
					write(1, buffer, r);
				}
				else {
					close(fd_r[i - 1]);
					pid[0]--;
					if (pid[0] == 0) write(1, "OVER!\n", 6);
					pid[i] = -1;
					ant = 1;
				}
			}
		}
	}
}