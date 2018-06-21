#include <signal.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <string.h>
#include <limits.h>
#include "readln.h"
#include "parteString.h"
#include "auxiliar.h"

void le_terminal(char* nome){

	char buf[PIPE_BUF];

	if(fork() == 0){
		int n;
		int fd = open(nome, O_WRONLY);
		while((n = (readln(0, buf, PIPE_BUF))) > 0){
			write(fd, buf, n);
		}

		close(fd);
		_exit(0);
	}
	
	wait(NULL);	

}


void escreve_terminal(char* programa, char* nome){
	char buf[PIPE_BUF];

	if(fork() == 0){
		int fd = open(nome, O_RDONLY);
		dup2(fd, 0);
		close(fd);
		char* funcao[10];
		int num = parteString(funcao, programa);
		funcao[0] = muda_programa(funcao[0]);
		execvp(funcao[0], funcao);
		perror(funcao[0]);
		_exit(0);
	}
	
	int fd = open("erros.txt", O_CREAT|O_WRONLY, 0644);
	char* str = malloc(49 + strlen(nome));
	sprintf(str, "O nodo com o nome %s teve a sua saída descartada\n", nome);
	write(fd, str, strlen(str));

	wait(NULL);


}