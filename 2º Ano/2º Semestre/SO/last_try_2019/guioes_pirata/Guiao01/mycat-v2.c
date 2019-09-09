/**
 * Funcionamento similar ao cat pré-definido da bash mas que apenas lê do standard input, com bloco de N bytes, e escreve no standard output.
 * Como argumento recebe apenas o tamanho do bloco N.
 * 
 * @author (Pirata) 
 * @version (2018.02)
 */

#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>

int main (int argc, char** argv) {
	char* buf = NULL;
	int n;
	int N;
	
	/* verificacao dos argumentos que recebe */
	if (argc != 2) {
		printf("I'm missing the size of the block! Give it to me!\n");
		exit(1);
	}
	
	N = atoi(argv[1]);
	/* o buf só aloca o espaço do N porque a cada vês liberta o buf */
	buf = malloc(N);
	
	// usando um exit a meio de um ciclo
	while(1) {
		/* 0 = stdin */
		n = read (0,buf,N);
		
		// se nao ler nada.
		if (n <= 0) {
			free(buf);
			exit(0);
		}
		
		/* 1 = stdout */
		write(1,buf,n);
	}
}
