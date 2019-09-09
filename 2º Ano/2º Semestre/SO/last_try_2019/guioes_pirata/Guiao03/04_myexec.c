/**
 * Programa que executa o comando da questao anterior com uma qualquer lista de argumentos.
 * Executar mantendo e alterando o argv[0]. O nome do ficheiro.
 * 
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

#define NAMEPROGRAM "./myarguments"

int main(int argc, char ** argv)
{
	pid_t pid;

	if (argc < 2) {
		perror("so lonely, no arguments passed");
		return (-1);
	}

	printf("Entering myexec\n");

	printf("Executing without changing the first argument (argv[0])\n");
	pid = fork();

	if (pid < 0) {
		perror("first fork failed");
		_exit(-1);
	}

	if (pid == 0) {
		/* using execvp because assuming both programs are in the same folder (already the correct path) */
		execvp(NAMEPROGRAM, argv);
		/* This will execute the program defined in NAMEPROGRAM
		 * but all the arguments, even the argv[0] to that program will be the same received here 
		 * this means that argv[0] passed to NAMEPROGRAM will be ./myexec and not NAMEPROGRAM */

		/* Do note that in case the program NAMEPROGRAM needs at any point to know it's own name
		 * stored in argv[0], executing it like this will send it the notification that it's being
		 * executed using ./myexec */
		_exit(-1); 		// in case of error trying the exec
	}
	
	wait(NULL);

	printf("\nExecuting after changing the first argument to %s (argv[0])\n", NAMEPROGRAM);
	pid = fork();

	if (pid < 0) {
		perror("second fork failed");
		_exit(-1);
	}

	if (pid == 0) {
		argv[0] = NAMEPROGRAM;
		/* using execvp because assuming both programs are in the same folder (already the correct path) */
		execvp(argv[0], argv);
		/* This will execute the program defined in argv[0] that now is the same as NAMEPROGRAM
		 * This time the argv[0] will be the NAMEPROGRAM itself */

		_exit(-1); 		// in case of error trying the exec
	}
	
	wait(NULL);
	return 0;
}
