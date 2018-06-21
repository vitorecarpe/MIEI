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

int _system(char** command) {
	int i;
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

int main(int argc, char** argv) {
	int r;
	char buf[PIPE_BUF];
	while ((r = readln(0, buf, PIPE_BUF)) > 0) {
		buf[r-1] = '\0';
		if (strcmp(buf, "exit") == 0) break;
		int p = fork();
		char** result = (char**) malloc(sizeof(char*) * 5);
		split(buf, result, 10);
		int last;
		for (last = 0; result[last] != NULL; last++);
		if (p == 0) {
			_system(result);
		}
		else if (p > 0 && strcmp(result[last - 1], "&") != 0)
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