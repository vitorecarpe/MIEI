#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <limits.h>
#include <stdlib.h>
#include <sys/wait.h>

/* 2. Modifique o programa anterior de modo a que,
 * depois de realizar os redireccionamentos, seja
 * criado um novo processo que realize operações de
 * leitura e escrita. Observe o conteúdo dos ficheiros.
 * Repare que o processo filho “nasce” com as mesmas
 * associacões de descritores de ficheiros do processo
 * pai.
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
	int p = fork();
	if (p == 0) {
		while((r = read(0, buf, PIPE_BUF)) > 0)
			write(1, buf, r);
	} else wait(NULL);
	exit(0);
}