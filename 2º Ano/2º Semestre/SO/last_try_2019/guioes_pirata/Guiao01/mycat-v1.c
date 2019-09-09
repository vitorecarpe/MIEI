/**
 * Funcionamento similar ao cat pré-definido da bash mas que apenas lê do input, carácter a carácter e escreve no output.
 * Control+D = EOF do standard input.
 * 
 * @author (Pirata) 
 * @version (2018.02)
 */

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

int main (int argc, char** argv)
{
	char buf[100];
	int n = 1;
	
	if (argc == 1) { //verificar que o mycat nao recebe argumentos
		while (n > 0) { //continuamente le do STDIN e escreve no STDOUT até não ler nada	
			/* 0 = STDIN */
			n = read(0,buf,1);
			/* 1 = STDOUT */
			write(1,buf,n); //ignora o valor do retorno de write.
		}
		exit(0);
	} else {
		perror("woah there, there's no need for arguments\n");
		exit(-1);
	}
}
