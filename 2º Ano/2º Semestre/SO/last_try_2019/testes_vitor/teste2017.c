// TESTE 26 MAIO 2017

// GRUPO II

#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <signal.h>


int main(int argc, char **argv){
	int i;
	pid_t pid;
	for(i=1; i<argc; i++){
		if((pid=fork())==0){
			//COMO IMPRIMIR APENAS X LINHAS DO STDOUT DE UM EXECLP?
		}
	}

	return 0;
}