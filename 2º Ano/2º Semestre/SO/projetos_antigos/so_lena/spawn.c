#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/wait.h>
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
	if (r != 0) buf[i++] = '\n';
	return i;
}


/** \brief Função que divide uma linha pelos seus espaços.
 *
 * @param	str 	   	A linha a dividir.
 * @param	res  		Array de Strings para adicionar as palavras.
 * @param	n   		O número de inicial de palavras que "res" suporta.
 */

void split(char* str, char** res, int n) {
	int i;
	int j = 0;
	res[j++] = str;
	for (i = 0; str[i]; i++) {
		if (str[i] == ':') {
			str[i] = '\0';
			if (j == n) res = realloc(res, (n + 5) * sizeof (char*));
			res[j++] = &(str[i + 1]);
		}
	}
}


/** \brief Este programa reproduz todas as linhas passadas, executando o comando
 * 		   indicado em argv uma vez para cada uma delas, e acrescentando uma nova
 * 		   coluna com o respetivo exit status.
 *
 * @param 		argc 	Número de argumentos do comando.
 * @param		argv 	Comando.
 */

void _spawn(int argc, char** argv) {
	char* arg;
	int status, i, nr, w;
	char line[PIPE_BUF], linecp[PIPE_BUF];
	char** argvcp = (char**) malloc((argc + 1) * sizeof (char*));
	char** res = (char**) malloc (10 * sizeof (char*));
	argvcp[argc] = NULL; // Para usarmos o execv, o último argumento do array tem de ser NULL
	while((nr = readln(0, line, PIPE_BUF)) > 0) {
		line[nr - 1] = '\0';
		strcpy(linecp, line);
		split(linecp, res, 10);
		for (i = 0; i < argc; i++) {
			if (argv[i][0] == '$') argvcp[i] = res[atoi(&(argv[i][1])) - 1];
			else argvcp[i] = argv[i];
		}
		if (!fork()) {
			execvp(argvcp[0], argvcp);
			_exit(-1);
		} else {
			wait(&status);
			status = WEXITSTATUS(status);
		}
		nr = sprintf(line, "%s:%d\n", line, status);
		w = write(1, line, nr);
		if (w == -1)
			perror("Erro na escrita no stdout!");
	}
}


/** \brief Corre a função spawn.
 *
 */

void main(int argc, char** argv) {
	_spawn(argc - 1, argv + 1);
	exit(0);
}