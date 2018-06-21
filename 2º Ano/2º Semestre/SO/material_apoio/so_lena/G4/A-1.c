/* Implemente um interpretador de comandos muito
 * simples ainda que inspirado na bash. O interpretador
 * deverá executar comandos especificados numa linha de
 * texto introduzida pelo utilizador. Os comandos são
 * compostos pelo nome do programa a executar e uma
 * eventual lista de argumentos. Os comandos podem
 * ainda executar em primeiro plano, ou em pano de
 * fundo, caso o utilizador termine a linha com &.
 * O interpretador deverá terminar a sua execução
 * quando o utilizador invocar o comando interno exit
 * ou quando assinalar o fim de ficheiro (Control-D
 * no inı́cio de uma linha em sistemas baseados em
 * Unix).
 */

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>
#include <limits.h>
#include <string.h>
#include <fcntl.h>

ssize_t readln(int fildes, void* buf, size_t nbyte) {
	int i = 0, r;
	char* buffer = buf;
	while (i < nbyte && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Falha na leitura!\n");
		exit(-1);
	}
	return i;
}

int _system(char** command, char** redir) {
	int i;
	if (redir[0] != NULL) {
		if (strcmp(redir[0], ">") == 0) {
			int fd = open(redir[1], O_WRONLY | O_CREAT | O_TRUNC, 0600);
			dup2(fd, 1); close(fd);
		}
		else if (strcmp(redir[0], "<") == 0) {
			int fd = open(redir[1], O_RDONLY, 0600);
			dup2(fd, 0); close(fd);
		}
		else if (strcmp(redir[0], ">>") == 0) {
			int fd = open(redir[1], O_WRONLY | O_APPEND, 0600);
			dup2(fd, 1); close(fd);
		}
		else if (strcmp(redir[0], "2>") == 0) {
			int fd = open(redir[1], O_WRONLY | O_CREAT | O_TRUNC, 0600);
			dup2(fd, 2); close(fd);
		}
		else if (strcmp(redir[0], "2>>") == 0) {
			int fd = open(redir[1], O_WRONLY | O_APPEND, 0600);
			dup2(fd, 2); close(fd);
		}
	}
	char* aux = command[0];
	execvp(aux, command);
	exit(-1);
}

void split(char* str, char** res, int n) {
	int i, j = 0;
	res[j++] = str;
	for (i = 0; str[i] != '\0'; i++) {
		if (str[i] == ' ') {
			str[i] = '\0';
			if (j == n) {
				res = realloc(res, (n + 5) * sizeof (char*));
				n += 5;
			}
			res[j++] = &(str[i + 1]);
		}
	}
	res[j++] = NULL;
}

void saveRedir(char** res, char** redir) {
	int i = 0;
	for (i = 0; res[i] != NULL && strcmp(res[i], "<") != 0
		&& strcmp(res[i], ">") != 0 && strcmp(res[i], ">>") != 0
		&& strcmp(res[i], "2>") != 0 && strcmp(res[i], "2>>") != 0; i++);
	if (res[i] == NULL) redir[0] = NULL;
	else {
		redir[0] = res[i];
		redir[1] = res[i+1];
		res[i] = NULL;
	}
}

int main(int argc, char** argv) {
	int r, e = 0;
	char buf[PIPE_BUF];
	while ((r = readln(0, buf, PIPE_BUF)) > 0) {
		buf[r-1] = '\0';
		if (strcmp(buf, "exit") == 0) break;
		char** result = (char**) malloc(sizeof(char*) * 5);
		split(buf, result, 10);
		char** redir = (char**) malloc(sizeof(char*) * 2);
		saveRedir(result, redir);
		for (e = 0; result[e] != NULL && strcmp(result[e], "&") != 0; e++);
		if (result[e] == NULL) e = 0;
		else e = 1;
		int p = fork();
		if (p == 0) {
			_system(result, redir);
		}
		else if (p > 0 && e == 0)
			wait(NULL);
	}
}

/* In Unix, exec() is only part of the story.
 * exec() is used to start a new binary within the
 * current process. That means that the binary that
 * is currently running in the current process will
 * no longer be running.
 * So, before you call exec(), you want to call
 * fork() to create a new process so your current
 * binary can continue running.
 * Normally, to have the current binary wait for the
 * new process to exit, you call one of the wait*()
 * family. That function will put the current process
 * to sleep until the process you are waiting is done.
 * So in order to create a "background" process, your
 * current process should just skip the call to wait.
 */