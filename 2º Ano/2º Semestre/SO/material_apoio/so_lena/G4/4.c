#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <limits.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <string.h>

/* 4. Escreva um programa redir que permita executar
 * um comando, opcionalmente redireccionando a entrada
 * e/ou a saída. O programa podera ser invocado, com:
 */

void redir(int fdi, int fdo, char** command) {
	if (fdi != 0) {dup2(fdi, 0); close(fdi);}
	if (fdo != 1) {dup2(fdo, 1); close(fdo);}
	execvp(command[0], command);
	exit(-1);
}

void main(int argc, char** argv) {
	if (strcmp(argv[1], "-i") == 0 && strcmp(argv[3], "-o") == 0) {
		int fdi = open(argv[2], O_RDONLY, 0600);
		int fdo = open(argv[4], O_WRONLY | O_CREAT | O_TRUNC, 0600);
		redir(fdi, fdo, argv + 5);
	}
	else if (strcmp(argv[1], "-i") == 0) {
		int fdi = open(argv[2], O_RDONLY, 0600);
		redir(fdi, 1, argv + 3);
	}
	else if (strcmp(argv[1], "-o") == 0) {
		int fdo = open(argv[2], O_WRONLY | O_CREAT | O_TRUNC, 0600);
		redir(0, fdo, argv + 3);
	} else redir(0, 1, argv + 1);
}