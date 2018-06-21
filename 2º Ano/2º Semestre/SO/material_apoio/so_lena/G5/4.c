#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <limits.h>

/* 4. Escreva um programa que emule o funcionamento
 * do interpretador de comandos na execução encadeada
 * de ls /etc | wc -l.
 */

int main(int argc, char** argv) {
	int mp[2], n;
	pipe(mp); // criação de um pipe anónimo
	char buf[PIPE_BUF];
	int p = fork();
	if (p == 0) {
		close(mp[0]);
		dup2(mp[1], 1); close(mp[1]);
		execlp("ls", "ls", "/etc", NULL);
		exit(-1);
	} else { // se sou o pai
		close(mp[1]);
		dup2(mp[0], 0); close(mp[0]);
		execlp("wc", "wc", "-l", NULL);
		exit(-1);
	}
}