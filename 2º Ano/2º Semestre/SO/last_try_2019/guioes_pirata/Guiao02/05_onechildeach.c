/**
 * Programa que cria uma descendência em profundidade de dez processos. Cada filho cria outro filho.
 * Cada processo imprime o seu identificador e o do seu parente. O pai fica a espera de o filho terminar a sua execução
 * antes de ele próprio imprimir os identificadores.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

#define NOFDESCENDANTES 10

int main()
{
	pid_t pid = 0;
	int i;

	/* If it's the parent, it won't go back into the cycle */
	for (i = 1; (i <= NOFDESCENDANTES) && (!pid); i++) {
		pid = fork();

		if (pid < 0) {
			perror("this fork failed. noone loves it, it should just die");
			_exit(-1);
		}

		if (pid > 0) {
			// Parent
			wait(NULL);
			printf("I\'m the parent now, pId: %d and my father is pId: %d. Just made the child pId: %d.\n",getpid(), getppid(), pid);
		} else {
			// Child
			printf("I\'m the child this round, pId: %d and my father is pId: %d.\n", getpid(), getppid());
		}
	}
	return 0;
}
