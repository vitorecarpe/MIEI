#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/* 6. Pretende-se determinar a existencia de um
 * determinado nûmero inteiro nas linhas de numa
 * matriz de números inteiros, em que o número de
 * colunas é muito maior do que o número de linhas.
 * Implemente, utilizando processos um programa que
 * determine a existência de um determinado número,
 * recebido como argumento, numa matriz gerada
 * aleatoriamente.
 */

void findNumber(int random[10][10000], int num) {
	int i, j, status, res = 0;
	for (i = 0; i < 10; i++) {
		int p = fork();
		if (p == -1) {
			perror("fork error()");
			exit(1);
		}
		if (p == 0) {
			int e = 0;
			for (j = 0; j < 10000 && !e; j++) {
				if (random[i][j] == num) e = 1;
			}
			if (e == 1) exit(i);
			else exit(0);
		}
	}
	sleep(1); // esperar que todos acabem
	for (i = 0; i < 10; i++) {
		wait(&status);
		int s = WEXITSTATUS(status);
		if (s != 0) printf("%d ", s);
	}
	printf("\n");
	exit(0);
}


int main(int argc, char** argv) {
	if (argc != 2) {
		perror("número de argumentos inválido!");
		exit(-1);
	}
	srand(time(NULL));
	int num = atoi(argv[1]), i, j, e = 0, res = 0, status;
	int random[10][10000];

	for (i = 0; i < 10; i++) {
		for (j = 0; j < 10000; j++) {
			random[i][j] = rand()%10000;
		}
	}
	findNumber(random, num);
	exit(0);
}