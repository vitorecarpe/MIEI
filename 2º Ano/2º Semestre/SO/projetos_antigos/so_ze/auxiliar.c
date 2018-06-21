#include <string.h>
#include <sys/wait.h>
#include <stdlib.h>

#include "auxiliar.h"

int e_funcao_auxiliar(char* prog){
	
	if(strcmp(prog, "const") == 0) return 0;

	if(strcmp(prog, "filter") == 0) return 1;

	if(strcmp(prog, "window") == 0) return 2;

	if(strcmp(prog, "spawn") == 0) return 3;

	return -1;	
}

char* muda_programa(char* prog){

	int n = e_funcao_auxiliar(prog);

	if(n >= 0){
		char* new;
		new = malloc(strlen(prog) + 3);

		strcpy(new, "./");
		strcat(new, prog);

		return new;
	}

	return prog;
}

