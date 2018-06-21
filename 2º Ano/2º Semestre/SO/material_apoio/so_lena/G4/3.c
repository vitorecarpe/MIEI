#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <limits.h>
#include <stdlib.h>
#include <sys/wait.h>

/* 2.  Modifique novamente o programa inicial de modo
 * a que seja executado o comando wc, sem argumentos,
 * depois do redireccionamento dos descritores de
 * entrada e saída. Note que, mais uma vez, as
 * associacões – e redireccionamentos – de descritores
 * de ficheiros sao preservados pela primitiva exec().
 */

void main() {
	int stdi = open("/etc/passwd", O_RDONLY | O_CREAT, 0600);
	int stdo = open("saida.txt", O_WRONLY | O_CREAT | O_TRUNC, 0600);
	int stde = open("erros.txt", O_WRONLY | O_CREAT | O_TRUNC, 0600);
	dup2(stdi, 0); close(stdi);
	dup2(stdo, 1); close(stdo);
	dup2(stde, 2); close(stde);
	int r;
	char buf[PIPE_BUF];
	execlp("wc", "wc", NULL);
	exit(-1);
}