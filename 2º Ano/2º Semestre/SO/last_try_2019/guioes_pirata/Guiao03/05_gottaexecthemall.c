/**
 * Programa que executa concorrentemente uma lista de executáveis passados como argumento
 * na linha de comando. Sem argumentos próprios. Esperar pela execução deles todos.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(int argc, char** argv)
{
	int i;
	pid_t pid;

	for (i = 1; i < argc; i++) {
		pid = fork();

		if (pid < 0) {
			perror("Titanic was an accident, this fail was a disaster");
			_exit(-1);
		}

		if (pid == 0) {
			// Child
			printf("Sending out child in mission codename: %s\n", argv[i]);
			/* as we know what the arguments are, using execl.
			 * using execlp to refer that either PATH commands or in the correct path */
			execlp(argv[i], argv[i], NULL);
			_exit(-1);		// in case of error
		}
	}

	for (i = 1; i < argc; i++) {
		wait(NULL);
    }

	return 0;
}
