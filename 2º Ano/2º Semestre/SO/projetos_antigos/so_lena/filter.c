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


/** \brief Testa a igualdade de dois inteiros.
 * @param 		n1		Primeiro valor.
 * @param 		n2		Segundo valor.
 */

int igual(int n1, int n2) {
	return (n1 == n2) ? 1 : 0;
}


/** \brief Testa se um primeiro valor é maior ou igual que um segundo valor.
 * @param 		n1		Primeiro valor.
 * @param 		n2		Segundo valor.
 */

int maiorIgual(int n1, int n2) {
	return (n1 >= n2) ? 1 : 0;
}


/** \brief Testa se um primeiro valor é menor ou igual que um segundo valor.
 * @param 		n1		Primeiro valor.
 * @param 		n2		Segundo valor.
 */

int menorIgual(int n1, int n2) {
	return (n1 <= n2) ? 1 : 0;
}


/** \brief Testa se um primeiro valor é maior que um segundo valor.
 * @param 		n1		Primeiro valor.
 * @param 		n2		Segundo valor.
 */

int maior(int n1, int n2) {
	return (n1 > n2) ? 1 : 0;
}


/** \brief Testa se um primeiro valor é menor que um segundo valor.
 * @param 		n1		Primeiro valor.
 * @param 		n2		Segundo valor.
 */

int menor(int n1, int n2) {
	return (n1 < n2) ? 1 : 0;
}


/** \brief Testa se um primeiro valor é diferente de um segundo valor.
 * @param 		n1		Primeiro valor.
 * @param 		n2		Segundo valor.
 */

int diferente(int n1, int n2) {
	return (n1 != n2) ? 1 : 0;
}


/** \brief Reproduz as linhas que satisfazem uma condição entre valores de duas colunas.
 * @param 		col1		Primeira coluna.
 * @param 		col2		Segunda coluna.
 * @param		f 			Função que representa um operador.
 */

void _filterS(int col1, int col2, int (*f)(int, int)) {
	int nr; // Comprimento de cada linha lida.
	int w;
	char buf[PIPE_BUF], line[PIPE_BUF], linecp[PIPE_BUF];
	char** res = (char**) malloc (10 * sizeof (char*));
	while((nr = readln(0,line,PIPE_BUF)) > 0){	
		strcpy(linecp, line);
		linecp[nr - 1] = '\0';
		split(linecp, res, 10);
		if(f(atoi(res[col1]), atoi (res[col2]))) w = write(1, line, nr);
		if (w == -1) perror("Erro na escrita no stdout!");
	}
}


/** \brief Função que reconhece um operador passado ao filter.
 *
 * @param 	argc 	Número de argumentos do comando.
 * @param 	argv	Argumentos do comando filter.
 */

void _filter(int argc, char** argv) {
	int col1 = atoi(argv[0]) - 1; // -1 porque num vetor a coluna 0 equivale à 1
	int col2 = atoi(argv[2]) - 1;
	if (strcmp(argv[1], "=") == 0)
		_filterS(col1, col2, igual);
	else if (strcmp(argv[1], ">=") == 0)
		_filterS(col1, col2, maiorIgual);
	else if (strcmp(argv[1], "<=") == 0)
		_filterS(col1, col2, menorIgual);
	else if (strcmp(argv[1], ">") == 0)
		_filterS(col1, col2, maior);
	else if (strcmp(argv[1], "<") == 0)
		_filterS(col1, col2, menor);
	else if (strcmp(argv[1], "!=") == 0)
		_filterS(col1, col2, diferente);
	else {
		perror ("Operador inválido!");
		exit(-1); // Eu acho que aqui devia ser 1, mas pronto.
	}
}

void main(int argc, char** argv) {
	if (argc != 4) {
		perror ("Erro ao passar os argumentos à função filter!");
		exit(-1); // Eu acho que aqui devia ser 1, mas pronto.
	} else _filter(argc - 1, argv + 1);
	exit(0);
}