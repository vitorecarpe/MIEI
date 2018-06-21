#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>

typedef struct arrayC {
	int* values;		/* Valores do array. */
	int tamanho;		/* Tamanho do array. */
	int posI;			/* Posição inicial do array. */
	int nVals;			/* Número de valores que o array contém. */
}* ArrayC;


/** \brief Função que cria um novo array circular.
 *
 * @param	tamanho		Tamanho do array a criar.
 * @return				Novo array circular.
 */

ArrayC criaArrayC(int tamanho) {
	ArrayC new = (ArrayC) malloc(sizeof(struct arrayC));
	new -> values = (int*) malloc(tamanho * sizeof(int));
	new -> tamanho = tamanho;
	new -> posI = 0;
	new -> nVals = 0;
}


/** \brief Adiciona um determiando valor no array circular passado como parâmetro.
 *
 * @param 	array 		Array onde adicionar o valor.
 * @param	x			Valor a adicionar.
 */

void addArrayC(ArrayC array, int x) {
	array -> values[(array -> posI + array -> nVals) % array -> tamanho] = x;
	if (array -> nVals == array -> tamanho)
		array -> posI = (array -> posI + 1) % array -> tamanho;
	else (array -> nVals)++;
}


/** \brief Função que retorna uma linha lida pelo descritor de ficheiro fildes.
 *
 * @param	fildes   	O descritor de ficheiro de onde ler.
 * @param	buf  		O buffer para onde vai ser escrito a linha.
 * @param	nbyte  		O número de bytes máximo a ler.
 * @return				O numero de caractéres lido.
 */

int readln(int fildes, char *buf, int nbyte) {
	char c;
	int r;
	int i = 0;
	while (i < nbyte - 1 && (r = read(fildes, &c, 1)) == 1 && c != '\n') buf[i++] = c;
	if (r == -1) {
		perror("Erro na leitura do caracter!");
		_exit(-1);
	}
	if(r != 0) buf[i++] = '\n';
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


/** \brief Determina o valor mínimo de um array passado como parâmetro.
 *
 * @param	*val 		Array do qual se quer saber o mínimo.
 * @param	n 			Tamanho do array.
 */

int min(int* val, int n) {
	if (n == 0) return 0;
	int menor = val[0], i;
	for (i = 1; i < n; i++)
		if (val[i] < menor) menor = val[i];
	return menor;
}


/** \brief Determina o valor máximo de um array passado como parâmetro.
 *
 * @param	*val 		Array do qual se quer saber o máximo.
 * @param	n 			Tamanho do array.
 */

int max(int* val, int n) {
	if (n == 0) return 0;
	int maior = val[0], i;
	for (i = 1; i < n; i++)
		if (val[i] > maior) maior = val[i];
	return maior;
}


/** \brief Determina a soma dos valores de um array passado como parâmetro.
 *
 * @param	*val 		Array do qual se quer saber a soma.
 * @param	n 			Tamanho do array.
 */

int sum(int* val, int n) {
	int sum = 0, i;
	for (i = 0; i < n; i++)
		sum += val[i];
	return sum;
}


/** \brief Determina a média dos valores de um array passado como parâmetro.
 *
 * @param	*val 		Array do qual se quer saber a média.
 * @param	n 			Tamanho do array.
 */

int avg(int* val, int n) {
	return (n == 0) ? 0 : (sum(val, n) / n);
}


/** \brief Reproduz todas as linhas passadas no stdin acrescentando-lhes uma nova
 *		   coluna com o resultado de uma operação calculada sobre os valores de col
 *		   das nval linhas anteriores.
 * @param	col 		Número da coluna.
 * @param	nval 		Número de linhas.
 * @param	f 			Função que representa a operação a calcular.
 */

void _add(int col, int nval, int (*f)(int*, int)) {
	ArrayC array = criaArrayC(nval);
	int nr, w;
	char line[PIPE_BUF], linecp[PIPE_BUF];
	char** res = (char**) malloc (10 * sizeof(char*));
	while((nr = readln(0, line, PIPE_BUF)) > 0) {
		line[nr - 1] = '\0';
		strcpy(linecp, line);
		split(linecp, res, 10);
		nr = sprintf(line, "%s:%d\n", line, f(array -> values, array -> nVals));
		addArrayC(array, atoi(res[col]));
		w = write(1, line, nr);
		if (w == -1)
			perror("Erro na escrita no stdout!");
	}
}


/** \brief Função que reconhece a operação passada à função window.
 *
 */

void _window(int argc, char** argv) {
	int col = atoi(argv[0]) - 1;
	int nval = atoi(argv[2]);
	if (strcmp(argv[1], "avg") == 0)
		_add(col, nval, avg);
	else if (strcmp(argv[1], "max") == 0)
		_add(col, nval, max);
	else if (strcmp(argv[1], "min") == 0)
		_add(col, nval, min);
	else if (strcmp(argv[1], "sum") == 0)
		_add(col, nval, sum);
	else {
		perror("Operador inválido!");
		exit(-1);
	}
}


/** /brief Corre a função window.
 *
 */

void main(int argc, char** argv) {
	if (argc != 4) {
		perror("Erro ao passar os argumentos!");
		_exit(-1);
	} else _window(argc - 1, argv + 1);
	exit(0);
}