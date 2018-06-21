#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <limits.h>

int main (int argc, char** argv) {
	int mp[2], n;
	pipe(mp); // criação de um pipe anónimo
	char buf[PIPE_BUF];
	int p = fork();
	if (p == 0) { // se sou o filho
		close(mp[1]);
		dup2(mp[0], 0); close(mp[0]);
		execlp("wc", "wc", NULL);
		exit(-1);
	} else { // se sou o pai
		close(mp[0]);
		while ((n = read(0, buf, PIPE_BUF)) > 0) {
			write(mp[1], buf, n); // indice 1 é associado à escrita
		}
		close(mp[1]);
	}
}