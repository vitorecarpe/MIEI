#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>

ssize_t readln(int fildes, char* buffer, size_t nbyte) {
	int i = 0, r;
	while (i < nbyte && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Falha na leitura!\n");
		exit(-1);
	}
	buffer[i] = '\n';
	return i;
}

pid_t createProcess(char* url) {
	pid_t ret;

	ret = fork();

	if (ret == 0) {
		int mp[2];
		pipe(mp);

		pid_t p = fork();

		if (p == 0) {
			close(mp[0]);
			dup2(mp[1], 1); close(mp[1]);
			execl("get1feed", "get1feed", url, NULL);
			exit(-1);
		}
		else {
			close(mp[1]);
			dup2(mp[0], 0); close(mp[0]);
			execl("filterNA", "filterNA", url, NULL);
			exit(-1);
		}
	} else return ret;
}

void main(int argc, char** argv) {
	int* pid;
	char** urls = argv + 1;
	int i;
	int np = 10;

	if (argc -1 < 10) np = argc - 1;

	pid = malloc(sizeof(int) * np);

	for (i = 0; i < np; i++) {
		pid[i] = createProcess(urls[i]);
	}

	pid_t dead;
	
	while ((dead = wait(NULL)) > 0) {
		if (urls[i] != NULL) {
			for (int o = 0; o < np; o++) {
				if (dead == pid[o]) pid[o] = createProcess(urls[i++]);
			}
		}
	}
	exit(0);
}