#include <signal.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <string.h>
#include <limits.h>
#include "nodo.h"
#include "readln.h"
#include "parteString.h"

typedef struct principal{
	Nodo* nodos;
	int numero_nodos;
} Prin, *Principal;

Principal inicia_Principal(){

	Principal p = malloc(sizeof(Prin));
	p->nodos = malloc(sizeof(Nodo));
	p->nodos[0] = NULL;
	p->numero_nodos = 0;

	return p;
}

void insere_nodo(Principal p, Nodo n){

	(p->numero_nodos)++;
	p->nodos = realloc(p->nodos, sizeof(Nodo) * (p->numero_nodos + 1));
	p->nodos[p->numero_nodos] = NULL;
	p->nodos[p->numero_nodos - 1] = n;
}

void coneta_nodos(Principal p, char* escreve, char* le){
	int i;
	Nodo n_escreve;
	Nodo n_le;
	int num = 0;
	for(i = 0; (i < p->numero_nodos) && (num < 2); i++){

		if(strcmp(getNome(p->nodos[i]), escreve) == 0){
			n_escreve = p->nodos[i];
			num++;
		}

		else{

			if(strcmp(getNome(p->nodos[i]), le) == 0){
			n_le = p->nodos[i];
			num++;
			}
		}
	}


	//printf("%s\n", getNome(n_escreve));

	coneta_escrita_nodo(n_escreve, le);
	coneta_leitura_nodo(n_le, escreve);
}

void distribui_funcionalidades(char* buf, int n, Principal p){
	char *comando[10];
	int numero_comandos;
	numero_comandos = parteString(comando, buf);

	if((strcmp(comando[0], "node") == 0)){
		char* programa = malloc(sizeof(char) * (strlen(comando[2]) + 1));
		strcpy(programa, comando[2]);

		for(int i = 3; i < numero_comandos; i++){
			programa = realloc(programa, strlen(programa) + strlen(comando[i] + 2));
			strcat(programa, " ");
			strcat(programa, comando[i]);
		}

		Nodo novo = cria_nodo(comando[1], programa);
		//printf("%s\n", programa);
		//printf("%d\n", sizeof(programa));
		insere_nodo(p, novo);
		/*for(int i = 0; i < p->numero_nodos; i++){
			if(p->nodos[i] != NULL) printf("OLA\n");
		}
		*/
	}

	else{

		if((strcmp(comando[0], "connect")) == 0){

			for(int i = 2; i < numero_comandos; i++){

				coneta_nodos(p, comando[1], comando[i]);
			}
		}

		else{
			if(!strcmp(comando[0], "inject")){
				int f=fork();

				if(f==0){

					execvp("./inject", comando);
					perror("./inject");
					_exit(0);
				}
				else{
					if(f==-1){
						int fd = open("erros.txt", O_WRONLY | O_CREAT, 0644);
						write(fd, "Nao criou um filho\n", 20);
					}
					else{
						
						//wait(NULL);
						wait(NULL);
					}
				}
			}
		}
	}

}

void inicializa_funcoes(Principal p){

	//printf("ENTRIE\n");
	for(int i = 0; i < p->numero_nodos; i++){
	//	printf("ENTREI\n");
		if(fork() == 0){
			executa_nodos(p->nodos[i]);
			_exit(0);
		}
	}


	for(int i = 0;i < p->numero_nodos; i++){
		wait(NULL);
	}
}

void executa_funcoes(char* buf, int n, Principal p){

	
}

int mystrcmp(char* a, char* b){
	int i, res = 0;
	for(i = 0; (res == 0) && a[i]!='\0' && b[i]!='\0'; i++){
		if(a[i] < b[i]){
			res = -1;
		}

		else{
			if(a[i] > b[i]){
				res = 1;
			}
		}
	}

	return res;
}

int main(){

	Principal p = inicia_Principal();
	char buf[PIPE_BUF];
	int n;
	int primeiro = 0;

	//printf("%d\n", PIPE_BUF);
	int i = 0;
	
	while((n = (readln(0, buf, PIPE_BUF))) > 0){
		
		if((mystrcmp(buf,"inject") == 0) && !primeiro){
			primeiro++;
			printf("ENRIE\n");
			if(fork() == 0){
					inicializa_funcoes(p);
			}
		}

		//printf("%s\n", buf);
		distribui_funcionalidades(buf, n, p);
		i++;

		
	}

	//printf("OLA\n");
	/*if(fork() == 0){
		inicializa_funcoes(p);
		_exit(0);
	}
*/
	/*while((n = (readln(0, buf, PIPE_BUF))) > 0){

		//executa_funcoes(buf, n, p);
	}
*/

	wait(NULL);

	return 0;
}
