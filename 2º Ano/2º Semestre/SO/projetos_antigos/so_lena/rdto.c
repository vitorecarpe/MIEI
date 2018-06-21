#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>

#define PIPE_NAME_MAX 9


/** \brief Função que retorna uma linha lida pelo descritor de ficheiro fildes.
 *
 * @param	fildes   	O descritor de ficheiro de onde ler.
 * @param	buf  		O buffer para onde vai ser escrito a linha.
 * @param	nbyte  		O número de bytes máximo a ler.
 * @return				O numero de caractéres lido.
 */

int readln(int fildes, char *buf, int nbyte) {
	char c;
	int r, i = 0;
	while (i < nbyte - 1 && (r = read(fildes, &c, 1)) == 1 && c != '\n') buf[i++] = c;
	if (r == -1) {
		perror("Erro na leitura do caracter!");
		return (-1);
	}
	if (r != 0) buf[i++] = '\n';
	return i;
}


/** \brief Função principal da conexão.
 *
 * @param	argc 	   	O número de argumentos passados ao programa (1 + nºids a mandar informação).
 * @param	argv 	   	Array de Strings com ids dos nodos a escrever informação.
 */

int main (int argc, char** argv) {
	int i, nr;
	int lastPid = atoi(argv[argc - 1]);
	char buf [PIPE_BUF];
	int fd [argc - 2];
	for (i = 1; i < argc - 1; i++) {
		char* pipeW = (char*) malloc(PIPE_NAME_MAX * sizeof(char));
		sprintf(pipeW, "pipeR%d", atoi(argv[i]));
		int w = open(pipeW, O_WRONLY);
		fd[i - 1] = w;
		free(pipeW);
	}
	if (lastPid != -1) kill(lastPid, SIGTERM);
	while ((nr = readln(0, buf, PIPE_BUF)) > 0) {
		for (i = 1; i < argc - 1; i++)
			write(fd[i - 1], buf, nr);
	}
}