/**
 * Cria um ficheiro com 10Mb de tamanho que recebe o nome do ficheiro como argumento.
 * Como conteúdo, são 10Mb apenas com a letra 'a'.
 * 
 * @author (Pirata) 
 * @version (2018.02)
 */

#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

#define DEZMEGA 10*1024*1024

int main(int argc, char** argv)
{
	int fd, i;
	char a = 'a';
	
	/* verificacao dos argumentos. Se a mais ou a menos. */
	if (argc != 2) {
		if (argc < 2) {
			printf("Feed me the file name!\n");
        } else {
			printf("Woah partner, go easy on the arguments. One file only!\n");
        }
		exit(1);
	}
	
	/* open(<filename>, <options>, <restrictions>) */
	fd = open(argv[1], O_CREAT | O_TRUNC | O_WRONLY, 0640);
	
	/* esta parte poderia ser feita para usar menos vezes a chamada write. */
	for (i = 0; i < DEZMEGA; i++) {
		write(fd, &a, 1); //ignora o valor de retorno da função
    }

	close(fd);
	
	exit(0);
}
