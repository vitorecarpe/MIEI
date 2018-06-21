#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>


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
		_exit(-1);
	}
	if(r != 0) buf[i++] = '\n';
	return i;
}


/** \brief Este programa reproduz as linhas que lhe são passadas acrescentando uma nova
 * 		   coluna sempre com o mesmo valor.
 *
 * @param	arg 		Valor a acrescentar às linhas.
 */

void _const(char* arg) {
	int nr, w, n = strlen(arg) + 2;
	char line[PIPE_BUF + n];
	while((nr = readln(0,line,PIPE_BUF)) > 0){	
		line[nr - 1] = '\0';
		nr = sprintf(line, "%s:%s\n", line, arg);
		w = write(1, line, nr);
		if (w == -1) perror("Erro na escrita no stdout!");
	}
}


/** \brief Corre a função const.
 *
 */

void main(int argc, char** argv) {
	if (argc != 2){
		perror ("Erro ao passar os argumentos à função const!");
		_exit(-1);
	}
	else _const(argv[1]);
	exit(0);
}