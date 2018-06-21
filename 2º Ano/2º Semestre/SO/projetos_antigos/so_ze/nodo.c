#include <signal.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <string.h>
#include "nodo.h"
#include "fonin.h"
#include "fonout.h"
#include "funcoes_terminal.h"

typedef struct nodo{

	char** chegam;
	int quantos_chegam;
	char** saem;
	int quantos_saem;
	char* programa;
	char* nome;
} Node;

char* getNome(Nodo n){

	return n->nome;
}

void coneta_escrita_nodo(Nodo n, char* nome){

	(n->quantos_saem)++;
	n->saem = realloc(n->saem, sizeof(char *) * (n->quantos_saem + 1));
	n->saem[n->quantos_saem] = NULL;
	n->saem[n->quantos_saem - 1] = malloc(strlen(nome) + 1);
	strcpy(n->saem[n->quantos_saem - 1], nome);

	//return 0;
}

void coneta_leitura_nodo(Nodo n, char* nome){
	
	(n->quantos_chegam)++;
	n->chegam = realloc(n->chegam, sizeof(char *) * (n->quantos_chegam + 1));
	n->chegam[n->quantos_chegam] = NULL;
	n->chegam[n->quantos_chegam - 1] = malloc(strlen(nome) + 1);
	strcpy(n->chegam[n->quantos_chegam - 1], nome);


	//return 0;
}

Nodo cria_nodo(char *nome, char* funcao){

	Nodo n = malloc(sizeof(Node));
	n->nome = malloc(strlen(nome) + 1);
	strcpy(n->nome, nome);
	n->programa = malloc(sizeof(strlen(funcao)+1));
	strcpy(n->programa, funcao);
	n->saem = malloc(sizeof(char*) * 1);
	n->quantos_saem = 0;
	n->quantos_chegam = 0;
	n->chegam = malloc(sizeof(char*) * 1);
	n->chegam[0] = n->saem[0] = NULL;
	mkfifo(nome, 0622);

	return n;
}


void executa_nodos(Nodo n){

	/*printf("Nodo: %s\n", n->nome);
	printf("Chegam %d\n", n->quantos_chegam);
	printf("Saem: %d\n", n->quantos_saem);
*/
	if(fork() == 0){

		if(n->quantos_chegam > 0){
		//	fonin(n->nome, n->chegam, n->quantos_chegam);
		}

		else{

			//le_terminal(n->nome);
		}

		_exit(0);

	}
	

	if(fork() == 0){


		if(n->quantos_saem > 0){
			//printf("FONOUT %s\n", n->nome);
			fonout(n->programa, n->nome, n->saem, n->quantos_saem);
		}

		else{
			escreve_terminal(n->programa, n->nome);
		}

		_exit(0);

	}
	
	for(int i = 0; i < 2; i++){

		wait(NULL);
	}
}

/*int main(){
	printf("MERDA!\n");
}
*/
