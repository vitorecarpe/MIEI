/**
 * Programa que cria 10 processos filhos que executam sequencialmente (o filho seguinte só é criado
 * depois do anterior acabar a execução).
 * Os filhos imprimem os identificadores e terminam execução com um valor de saída igual ao número
 * de ordem que foram criados.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

#define NUMBEROFCHILDREN 10

int main()
{
	pid_t pid;
	int i, status;

	for (i = 1; i <= NUMBEROFCHILDREN; i++) {
		pid = fork();
		if (pid == -1) {
			perror("fork failed. terminating all processes");
			return -1;
		}

		if (pid == 0) {
			// Child
			printf("I\'m the son with pId: %d and number: %d.", getpid(), i);
			printf("My father has the pId: %d.\n", getppid());
			/* Terminates the process imediatly with code i */
			_exit(i);	//would be correct to use exit() instead but using _exit() as requested.
		} else {
			// Parent
			wait(&status);	//waiting for any child to terminate
			if (WIFEXITED(status)) {		// checks if child terminated normally
				printf("Child with pId: %d terminated with the code: %d.\n", pid, WEXITSTATUS(status));
			} else {
				perror("Child went wrong in life.");
            }
		}
	}
	return 0;
}
