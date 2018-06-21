#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>

/* 
 * 5. Implemente um programa que execute
 * concorrentemente uma lista de executáveis
 * especificados como argumentos da linha de
 * comando. Considere os executáveis sem quaisquer
 * argumentos próprios. O programa deverá esperar
 * pelo fim da execução de todos processos por si
 * criados.
 */

int main(int argc, char** argv) {
	int p, i, status;
	for (i = 1; i < argc; i++) {
		p = fork();
		if (p == -1) {
			perror("Bad arguments!");
			exit(-1);
		}
		if (p == 0) {
			execlp(argv[i], argv[i], NULL);
			exit(-1);
		}
	}
	for (i = 1; i < argc; i++) {
		wait(&status);
	}
}