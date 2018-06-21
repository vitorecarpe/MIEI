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
	while (i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Falha na leitura!\n");
		exit(-1);
	}
	buffer[i] = '\0';
	return i;
}

char** parse(char commands[]) {
	int n = 1, i = 0;
	char** res = malloc(sizeof(char*) * n);

	char* p = strtok(commands, "|");

	while (p != NULL) {
		res[i++] = p;
		n++;
		res = realloc(res, sizeof(char*) * n);
		p = strtok(NULL, "|");
	}
	res[--n] = NULL;
	return res;
}

void trim(char* s) {
	int start = 0, end = strlen(s);

	while(s[start] == ' ') start++;
	while(s[end] == ' ') end--;

	s[end] = '\0';

	s = s + start;
}


void main(int argc, char** argv) {
	int r, i, p;
	char buffer[PIPE_BUF];
	r = readln(0, buffer, PIPE_BUF);
	buffer[r-1] = '\0';
	if (r == -1) {
		perror("Erro na leitura!\n");
		exit(-1);
	}
	int l;
	char** res = parse(buffer);
	for (l = 0; res[l] != NULL; l++)
		trim(res[l]);

	for (i = 0; i < (l-1); i++) {
		int mp[2];
		pipe(mp);
		p = fork();

		if (p == 0) {
			close(mp[0]);
			dup2(mp[1], 1); close(mp[1]);
			execlp("sh", "sh", "-c", res[i], NULL);
			printf("aqui!\n");
			exit(-1);
		}
		close(mp[1]);
		dup2(mp[0], 0); close(mp[0]);
	}

	execlp("sh", "sh", "-c", res[i], NULL);
	exit(-1);
}