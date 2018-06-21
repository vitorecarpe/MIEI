/**@file const.c
 *
 * \brief Executa o comando const
 *
 * Este módulo contem o comando de const, que adiciona 
 * uma determianda string no final de um input, da
 * seguinte forma : input:x , onde x é o que queremos
 * adicionar
 */

#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>
#include <signal.h>
#include <limits.h>
#include "stringProcessing.h"

/** \brief Indica se está a processar algum input */
static int working = 0;
/** \brief Indica se o programa será fechado */
static int quit_exec = 0;

/**
 * \brief Tratará dos sinais de saída
 * @param sig Sinal
 */
void const_handler(int sig){
	if (sig == SIGINT){
		if (working)
			quit_exec = 1;
		else {
			close(1);
			_exit(0);
		}
	}
}

/**
 * Retorna uma nova string com o resultado de colocar um ':x' no input recebido
 * @param  input Input
 * @param  x     Valor a colocar
 * @return       Nova string processada
 */
char* const_(char* input, char* x){
	int size_i = strlen(input);
	int size_x = strlen(x);
	char *new = malloc (sizeof(char) * (size_i + size_x +1));
	strcpy(new, input);
	new[size_i-1] = ':';
	new[size_i] = '\0';
	strcat(new, x);
	return new;
}

/**
 * Função main de const_
 * @param  argc Número de argumentos
 * @param  argv Argumentos
 * @return ---
 */
int main(int argc, char *argv[]){
	signal(SIGINT, const_handler);
	char buffer[PIPE_BUF];
	char *out;
	int charsRead;
	strcat(argv[1], "\n");
	while((charsRead = readline(0, buffer, PIPE_BUF)) > 0){
		if (charsRead > 1){
			working = 1;
			out = const_(buffer, argv[1]);
			write(1, out, strlen(out));
			memset(buffer, 0, charsRead);
			working = 0;
		}
		if (quit_exec){
			close(1);
			return 0;
		}
	}
	return 0;
}