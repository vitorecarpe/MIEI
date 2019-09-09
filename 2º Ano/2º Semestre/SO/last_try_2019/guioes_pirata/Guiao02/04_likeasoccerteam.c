/**
 * Programa que cria 10 processos filhos que executam concorrentemente.
 * O pai depois espera pelo fim da execução dos filhos, imprimindo os respetivos
 * códigos de saida.
 * 
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

#define NROFCHILDREN 10

int main()
{
	pid_t pid;
	int i, status;

	/* creating all the children */
	for (i = 1; i <= NROFCHILDREN; i++) {
		pid = fork();

		if (pid == -1) {
			perror("fork failed, no children born");
			_exit(-1);	// terminates process with error imediatly
		}

		if (pid == 0) {
			// Children
			printf("I\'m a children - pId: %d - code: %d.\n", getpid(), i);
			_exit(i);	// terminates the child process with the child code i
		}
	}

	/* retrieving all the childrens answers */
	for (i = 0; i < NROFCHILDREN; i++) {
		pid = wait(&status);		// waits for any of the children
		if ((pid > 0) && (WIFEXITED(status))) {
			printf("Caught the child with pId: %d and with the code: %d.\n", pid, WEXITSTATUS(status));
		} else {
			perror("Something went wrong with this one");
			_exit(-1);
		}
	}
	return 0;
}
