/**
 * Programa similar ao anterior, mas que neste caso procura quais linhas numa matriz aleatória têm um certo
 * valor, passado como argumento, usando processos. No fim deve imprimir por ordem crescente as linhas onde
 * esse valor aparece.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <stdio.h>

#define NOFLINES 64			// Number of lines in the matrix - same as number of child processes
#define NOFCOLUMNS 4096		// Number of columns in the matrix
#define MAXVALUE 5000		// Maximum value of the matrix cells
#define SEED 2				// Seed for the pseudo-random generator

int main(int argc, char** argv)
{
	int matrix[NOFLINES][NOFCOLUMNS];
	int i, j, ic, valueToFind, status = 0;
	/* this array will store all the child pIds created by the fork() system call for the parent */
	pid_t pid[NOFLINES] = {0};

	/* Verifying input arguments and getting the number we need to search for from it */
	if (argc == 2) {
		valueToFind = atoi(argv[1]);
		if (!((valueToFind > 0) && (valueToFind <= MAXVALUE))) {
			perror("input argument either invalid or out of bounds");
			return -1;
		}
	} else {
		perror("wrong ammount of input: ./<exec> <valueToFind>");
		return -1;
	}

	/* Creating the pseudo-random Matrix 
	   Same seed will create the same matrix values */
	printf("Creating Matrix with %d lines and %d columns.\n", NOFLINES, NOFCOLUMNS);
	printf("Pseudo-random values generated with seed %d varying between 1 and %d.\n", SEED, MAXVALUE);
	srand(SEED);
	for (i = 0; i < NOFLINES; i++) {
		for (j = 0; j < NOFCOLUMNS; j++) {
			matrix[i][j] = rand() % MAXVALUE + 1;
		}
	}
	printf("Matrix built.\n");

	/* creating a child for each line */
	printf("Searching intruders with the code number %d in the lines.\n", valueToFind);
	for (ic = 0; ic < NOFLINES; ic++) {
		pid[ic] = fork();

		if (pid[ic] < 0) {
			perror("shooter down, clear the range");
			_exit(-1);
		}

		if (pid[ic] == 0) {
			// Child
			for (j = 0; j < NOFCOLUMNS; j++) {
				if (matrix[ic][j] == valueToFind)
					_exit(1);		// terminates the process returning true
			}
			_exit(0);				// terminates the process returning false
		}
	}

	/* waiting for each child specifically */
	printf("Intruder allert in lanes:");
	for (i = 0; i < NOFLINES; i++) {
		waitpid(pid[i],&status,0);
		if (WIFEXITED(status) && (WEXITSTATUS(status) == 1)) {
			printf(" %d;",i);
		}
	}
	printf("\nAiming\n");

	return 0;
}
