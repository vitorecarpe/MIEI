#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <limits.h>
#include <stdlib.h>

/* REDIRECIONAR O DESCRITOR ASSOCIADO AO STDIN PARA O
 * FICHEIRO /etc/passwd, STDOUT -> saida.txt, STDERR ->
 * erros.txt
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
	while((r = read(0, buf, PIPE_BUF)) > 0)
		write(1, buf, r);
	exit(0);
}