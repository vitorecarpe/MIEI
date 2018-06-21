#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>

void main(int argc, char** argv) {
	int fd = open("xlog.log", O_WRONLY | O_TRUNC | O_CREAT, 0600);
	if (fd == -1) {
		perror("Erro ao criar ficheiro!");
		exit(-1);
	}
	int i;
	for (i = 1; i < argc; i++) {
		pid_t p = fork();

		if (p == 0) {
			dup2(fd, 1); close(fd);
			execlp(argv[i], argv[i], NULL);
			exit(-1);
		}
		else wait(NULL);
	}
	exit(0);
}