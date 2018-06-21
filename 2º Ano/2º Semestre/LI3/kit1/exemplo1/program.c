#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include "person.h"


/*

1 - Estrutura básica programa em c
2 - Separação de código em .c e .h (definição vs declaração)
3 - Structs
4 - Compilação código em módulos (Makefile e biliotecas)
5 - alcance variáveis
6 - Apontador vs endereço 
7 - free

*/

int main(int argc, char* argv[]){
	
	Person_t andre;
	andre.name=malloc(sizeof(char)*20);
	strcpy(andre.name, "Andre");
	andre.age=20;

	Person_t new_andre;
	printf("idade anterior andre %d\n",andre.age);
	andre=change_age1(andre, 30);
	printf("idade modificada andre %d\n",andre.age);


	//Person_t *ptr=&andre;

	printf("idade anterior %d\n",andre.age);
	change_age2(&andre, 30);
	//change_age2(ptr, 30);
	printf("idade modificada %d\n",andre.age);



	printf("idade anterior andre%d\n",andre.age);
	new_andre=change_age1(andre, 30);
	printf("idade modificada andre %d\n",andre.age);
	//printf("idade modificada new_andre %d\n",new_andre.age);



	free(andre.name);

	return 0;
}