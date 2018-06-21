#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <limits.h>

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while(i < nbytes && (r = read(0, &buffer[i], 1)) > 0 && buffer[i++] != '\n');

	if (r == -1) {
		perror("Erro na leitura!");
		exit(-1);
	}
	buffer[i] = '\n';
	return i;
}

int main(int argc, char** argv) {
	int fd[argc], status, r, i;
	char buffer[PIPE_BUF];

	for (i = 1; i < argc; i++) {
		int mp[2];
		pipe(mp);

		int p = fork();

		if (p == 0) {
			close(mp[1]);
			dup2(mp[0], 0); close(mp[0]);
			char* cmd = argv[i];
			execlp(cmd, cmd, NULL);
			exit(-1);
		}
		fd[i] = mp[0];
	
	}

	for (i = 1; i < argc; i++) {
		wait(&status);
	}

	for (i = 1; i < argc; i++) {
		while((r = readln(fd[i], buffer, PIPE_BUF)) > 0) {
			buffer[r] = '\0';
			write(1, buffer, r);
		}
	}
}