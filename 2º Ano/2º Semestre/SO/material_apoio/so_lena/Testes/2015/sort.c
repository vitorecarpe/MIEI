#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */
#include <limits.h>
#include <signal.h>
#include <sys/wait.h>
#include <string.h>

/* Tirando partido do comando "sort" (que nesta
 * pergunta deve ser executado sem mais argumentos)
 * que ordena o conteúdo recebido no STDIN e o emite
 * no STDOUT, pretende-se criar a seguinte
 * funcionalidade: Um utilizador que queira ordenar
 * um ficheiro chamado "nome.txt" presente na 
 * diretoria local, deve escrever o nome do mesmo
 * para um pipe com o nome "ordenar". Tal como
 * pode ser feito via "echo nome.txt > ordenar".
 * Faça um programo que leia repetidamente desse
 * pipe e produza ficheiros ordenados com a extensão
 * adicional "sorted". Neste exemplo seria gerado
 * o ficheiro "nome.txt.sorted".
 */

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while (i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');

	if (r == -1) {
		perror("Erro na leitura!");
		exit(-1);
	}
	buffer[i-1] = '\0';
	return i;
}

int main(int argc, char** argv) {
	int o = open("ordenar", O_RDONLY), nr;
	if(o == -1) {
		perror("Erro na abertura do pipe!");
		exit(-1);
	}

	char fich[PIPE_BUF];
	
	while((nr = readln(o, fich, PIPE_BUF)) > 0) {
		int r = open(fich, O_RDONLY, 0600);
		char fichN[strlen(fich) + 7];
		sprintf(fichN, "%s.sorted", fich);
		int w = open(fichN, O_WRONLY | O_CREAT | O_TRUNC, 0600);

		int p = fork();
		if (p == 0) {
			dup2(r, 0); close(r);
			dup2(w, 1); close(w);
			execlp("sort", "sort", NULL);
			_exit(-1);
		} else wait(NULL);
	}
	return 0;
}