/**@file client.c
 *
 * \brief Módulo responsável pelo cliente de uma rede
 *
 * Este programa servirá para um cliente da rede
 * poder escrever para um determiado nó (que será
 * feito através de um inject pelo controlador).
 */

#include <unistd.h>
#include <limits.h>
#include <fcntl.h>
#include "stringProcessing.h"

/**
 * Função main de client
 * @param  argc Número de argumentos
 * @param  argv Argumentos (terá o nome do pipe a abrir)
 * @return ---
 */
int main(int argc, char **argv){
	int file, charsRead;
	char buffer[PIPE_BUF];
	file = open(argv[1], O_WRONLY);

	if (file > 0)
		while ((charsRead = readline(0, buffer, PIPE_BUF)) > 0)
			write(file, buffer, charsRead);

	return 0;
}