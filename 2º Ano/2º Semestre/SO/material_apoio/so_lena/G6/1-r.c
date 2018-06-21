#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <limits.h>
#include <unistd.h>

int main(int argc, char** argv) {
	int o = open("fifo", O_RDONLY,0600);
	if (o == -1) {
		perror("erros creating fifo!");
		exit(-1);
	}
	char buf[PIPE_BUF]; int r;
	while((r = read(o, buf, PIPE_BUF)) > 0)
		write(1, buf, r);
	exit(0);
}

/* Repare que ao contrário dos pipes anónimos, o pipe
 * corresponde a uma entrada no sistema de ficheiros,
 * sujeito ao mesmo controlo de acesso dos ficheiros
 * normais, e não obriga à criação do pipe por um
 * processo ascendente dos processos em comunicação.
 * Aliás, a comunicação pode mesmo realizar-se entre
 * processos de utilizadores distintos. Note ainda 
 * que tal como nos pipes anónimos, as operações de
 * leitura e escrita no pipe oferecem um canal
 * unidireccional sob uma polı́tica FIFO e diluição
 * da eventual fronteira das escritas. No entanto,
 * ao contrário dos pipes anónimos, a abertura para
 * escrita de um pipe com nome bloqueia até que um
 * processo o abra para leitura, e vice-versa.
 */