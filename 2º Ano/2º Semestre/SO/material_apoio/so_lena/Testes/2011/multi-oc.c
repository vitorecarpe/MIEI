#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>


ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while(i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');

	buffer[i] = '\0';
	return i;
}


void main(int argc, char** argv) {
	int n = argc - 2, i, status, r;
	int fd_r[argc], fd_w[argc];
	char* term = argv[1];
	char buffer[PIPE_BUF];
	int results[argc];

	for (i = 2; i < argc; i++) {
		int mp[2];
		pipe(mp);
		fd_r[i] = mp[0];
		fd_w[i] = mp[1];
		int f = open(argv[i], O_RDONLY, 0600);
		int p = fork();

		if (p == 0) {
			close(fd_r[i]);
			dup2(f, 0); close(f);
			dup2(fd_w[i], 1); close(fd_w[i]);
			execlp("./oc", "./oc", term, NULL);
			exit(-1);
		}
	}

	for (i = 2; i < argc; i++) {
		wait(&status);
		if (r = readln(fd_r[i], buffer, PIPE_BUF)) {
			results[i] = atoi(buffer);
		}
		printf("%s : %d\n", argv[i], results[i]);
	}
}