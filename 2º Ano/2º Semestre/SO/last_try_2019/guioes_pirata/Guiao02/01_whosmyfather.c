/**
 * Programa que imprime o seu identificador e o do seu pai.
 * Comprovar usando o comando "ps" no terminal que o pai do processo é o interpretador de comandos (bash)
 * usado para o executar.
 *
 * @author (Pirata)
 * @version (2018.05)
 */

#include <unistd.h>
#include <stdio.h>

int main()
{

	printf("I\'m a simple program leading a simple life.\n");
	printf("My process id is %d and my father\'s pId: %d.\n", getpid(), getppid());
	/* if you execute the command "ps" in the terminal you can verify that
	 * the getppid() here is the same as the bash pid
	 */
	return 0;
}
