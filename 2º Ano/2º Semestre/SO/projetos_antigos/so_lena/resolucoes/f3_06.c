#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main (int argc, char *argv []){
	int i,j = 0;
	char ** res = (char**) malloc (10*sizeof (char*));
	if (argc != 2){
		perror("Erro ao passar argumentos!!");
		exit(-1);
	}
	res[j++] = argv[1];
	for (i = 0; argv[1][i] != '\0'; i++)
		if (argv[1][i] == ' ') {
			argv[1][i] = '\0';
			res[j++] = &argv[1][i+1];
		}
	res[j] = NULL;
	execvp(res[0],res);
	exit(-1);
}



/*
int main (int argc, char *argv []){
	int a = 0,i,j = 0;
	char *arg[10];char pal [20];
	if (argc != 2){
		perror("Erro ao passar argumentos!!");
		exit(-1);
	}
	for (i = 0; argv[1][i] != '\0'; i++){
		if (argv[1][i] != ' ') {pal[j] = argv[1][i];j++;}
		else if (argv[1][i-1] != ' ' ){
			  pal[j] = '\0';
			  arg[a] = (char *) malloc (sizeof(j+1));
			  strcpy(arg[a],pal);
			  a++;
			  j = 0;}
	}
	pal[j] = '\0';
	arg[a++] = pal;
	arg[a] = NULL;
	execvp(arg[0],arg);
	exit(-1);
}*/