#include <signal.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <limits.h>

#include "auxiliar_funcoes.h"
#include "readln.h"

char** atualizaEstruta(char** funcao, int arg, char* linha){

	int n = -1;
	int i;

	for(i = 0; (i < arg) && (n == -1); i++){
		if(funcao[i][0] == '$'){
			n = 0;
		}
	}

	i--;
	if(n != -1){
		int j = i;

		int col = atoi(funcao[i] + 1);
		int atual;

		for(i = 0, atual = 0; (linha[i] != '\0') && (linha[i] != '\n') && (atual < col); i++){
			if(linha[i] == ':'){
				atual++;
			}
		}

		if(atual == col){

			char* nova = malloc(1);
			int tam = 1;

			for(i; (linha[i] != '\0') && (linha[i] != '\n') && (linha[i] != ':'); i++){
				nova = realloc(nova, tam + 1);
				nova[tam - 1] = linha[i];
				tam++;
			}

			nova[tam - 1] = '\0';

			funcao[j] = malloc(tam);
			strcpy(funcao[j], nova);
		}

	}

	return funcao;
}

int spawn(char** funcao, int arg, char* linha){
	
	int f = fork();

	if(f == -1){
		//ERRO TRATAR DEPOIS
		return -1;
	}



	if(f == 0){

		funcao = atualizaEstruta(funcao, arg, linha);

		execvp(funcao[0], funcao);
		perror(funcao[0]);
		_exit(0);
	}


	int status, r;
	wait(&status);
	
	if(WIFEXITED(status)) {
		r = WEXITSTATUS(status);
	}

	return r;
}

int main(int argc, char** argv){

	if(argc < 2){
		//ERRO

		return -1;
	}

	int n;
	char buf[PIPE_BUF];
	while((n = readln(0, buf, PIPE_BUF - 4)) > 0){

		int r = spawn(argv + 1, argc - 1, buf);
		char aux[5];
		sprintf(aux, "%d", r);
		n = acrescenta(buf, n, aux);

		write(1, buf, n);
	}

	return 0;
}