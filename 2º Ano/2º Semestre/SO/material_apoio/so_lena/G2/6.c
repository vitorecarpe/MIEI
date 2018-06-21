#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

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
			exit(e);
		}
	}
	for (i = 0; i < 10; i++) {
		wait(&status);
		res = res || WEXITSTATUS(status);
	}
	if (res == 1) printf("Encontrou!\n");
	else printf("Não encontrou!\n");
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