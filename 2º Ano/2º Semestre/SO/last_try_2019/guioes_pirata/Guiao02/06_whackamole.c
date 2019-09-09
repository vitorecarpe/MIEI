/**
 * Programa que determina a existência de um determinado número inteiro nas linhas de uma matriz de
 * números inteiros. Implementado usando processos para correr as linhas a em busca de um determinado
 * valor recebido como argumento.
 * Matriz é gerada aleatoriamente.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <stdio.h>

#define NOFLINES 64			// Number of lines in the matrix
#define NOFCOLUMNS 4096		// Number of columns in the matrix
#define MAXVALUE 5000		// Maximum value of the matrix cells
#define NOFCHILD 10			// Number of child process we will create
#define SEED 2				// Seed for the pseudo-random generator

int main(int argc, char** argv)
{
	int matrix[NOFLINES][NOFCOLUMNS];
	int i, j, ic, valueToFind, counter = 0;
	pid_t pid = 1;

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

	printf("Searching the mole with the code %d.\n", valueToFind);
	printf("Starting to attack the matrix with %d child processes.\n", NOFCHILD);
	for (ic = 0; (pid > 0) && (ic < NOFCHILD); ic++) {
		pid = fork();

		if (pid < 0) {
			perror("fork got knifed");
			_exit(-1);
		}

		if (pid == 0) {
			for (i = ic; i < NOFLINES; i += NOFCHILD) {
				for (j = 0; j < NOFCOLUMNS; j++) {
					if (matrix[i][j] == valueToFind) {
						printf("Whacked mole at position Matrix[%d][%d]\n", i, j);
						counter++;
					}
				}
			}
		}
	}

	if (pid == 0) {
		printf("Child code %d found %d times the number %d.\n", ic, counter, valueToFind);
		_exit(0);		// child process ends with a success.
	}

	/* parent waits for NOFCHILD child processes to terminate */
	for (i = 0; i < NOFCHILD; i++) {
		wait(NULL);
	}
	printf("All the moles got whacked\n");
	return 0;
}
