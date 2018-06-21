#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>


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

void main(int argc, char** argv) {
	int l, r, i;
	int fd_r[argc], fd_w[argc];

	fd_r[0] = argc - 1;

	char buffer[PIPE_BUF];

	for (i = 1; i < argc; i++) {
		int mp[2];
		pipe(mp);
		fd_w[i] = mp[1];
		fd_r[i] = mp[0];

		int p = fork();

		if (p == 0) {
			close(fd_r[i]);
			dup2(fd_w[i], 1); close(fd_w[i]);
			execlp(argv[i], argv[i], NULL);
			exit(-1);
		}
		else close(fd_w[i]);
	}


	while (fd_r[0] != 0) {
		for (i = 1; i < argc; i++) {
			if (fd_r[i] != -1) {
				int l = 10;
				while(l > 0 && (r = readln(fd_r[i], buffer, PIPE_BUF)) > 0) {
					write(1, buffer, r);
					l--;
				}
				if (l > 0) {
					close(fd_r[i]);
					fd_r[i] = -1;
					fd_r[0]--;
				}
			}
		}
	}
	exit(0);
}