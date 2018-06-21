#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <limits.h>

/* Escreva um programa “servidor”, que fique a correr
 * em background, e acrescente a um ficheiro de “log” 
 * todas as mensagens que sejam enviadas por “clientes”.
 * Escreva um programa cliente que envia para o
 * servidor o seu argumento. Cliente e servidor devem
 * comunicar via pipes com nome.
 */

int main(int argc, char** argv) {
	int p = fork();

	if (p == 0) {
		int o = open("fifo", O_RDONLY, 0600);
		if (o == -1) {
			perror("error opening fifo!");
			exit(-1);
		}
		char buf[PIPE_BUF]; int r;
		int file = open("log", O_WRONLY | O_CREAT | O_TRUNC, 0600);
		while (1) {
			while((r = read(o, buf, PIPE_BUF)) > 0)
				write(file, buf, r);
		}
	}
}