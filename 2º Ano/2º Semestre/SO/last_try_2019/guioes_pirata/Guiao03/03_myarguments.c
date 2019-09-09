/**
 * Programa que imprime a lista de argumentos recebida na linha de comando.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <stdio.h>

int main (int argc, char ** argv)
{
	int i = 0;

	printf("Value of argc = %d\n", argc);

	printf("Listing the arguments:\n");
	do {
		/* There's always at least 1 argument.
		 * The command itself */
		printf("Index %d - %s\n", i, argv[i]);
		i++;
	} while (argv[i] != NULL);

	// Printing the NULL the "last" argument when index = argc
	printf("Index %d - %s\n", i, argv[i]);
	return 0;
}
