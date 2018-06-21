#include <signal.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <string.h>
#include "readln.h"
#include "parteString.h"


int filter2(char* linha, int op1, int op2, int func){
	int coluna=1, a=0, b=0;
	char x[5], y[5];
	
	for(int i=0; linha[i]!='\0'; i++){
		if(linha[i]!=':'){
			if(coluna == op1){
				x[a] = linha[i];
				a++;
			}
			if(coluna == op2){
				y[b] = linha[i];
				b++;
			}
		}
		else coluna++;
	}
	x[a] = '\n'; 
	y[b] = '\n';
	a = atoi(x);
	b = atoi(y);

	switch(func){
		case 1: return (a==b); 
		case 2: return (a>=b);
		case 3: return (a<=b);	
		case 4: return (a>b); 
		case 5: return (a<b); 
		case 6: return (a!=b); 
		default: return 0;
	}
}

int operacao(char* aux){

	if(strcmp(aux, "==") == 0){
		return 1;
	}

	if(strcmp(aux, ">=") == 0){
		return 2;
	}

	if(strcmp(aux, "<=") == 0){
		return 3;
	}

	if(strcmp(aux, ">") == 0){
		return 4;
	}

	if(strcmp(aux, "<") == 0){
		return 5;
	}

	if(strcmp(aux, "!=") == 0){
		return 6;
	}

	return 7;
}

int main(int argc, char** argv){
	//printf("%d\n", argc);
	if(argc != 4){
		//ERRO
		return -1;
	}

	char buf[100];
	int n;
	int numero = 0;

	int op, c1, c2;

	//char *aux[5];
	//int num = parteString(aux, argv[1]);

	//if(num != 3){
		//ERRO
	//	return -1;
	//}

	c1 = atoi(argv[1]);
	//printf("%d\n", c1);
	c2 = atoi(argv[3]);
	//printf("%d\n", c2);
	op = operacao(argv[2]);
	//printf("%d\n", op);

	while((n = (readln(0, buf, 100))) > 0){
		
		if(fork() == 0){
			int x = filter2(buf, c1, c2, op);

			if(x > 0){
				//printf("%s\n", buf);
				write(1, buf, n);
				//printf("SOU  FILHO\n");
			}

			_exit(0);
		}
		numero++;
	}

	for(int i = 0; i < numero; i++){
		wait(NULL);
		//printf("Esperei por %d filho\n", i);
	}



	return 0;
}
