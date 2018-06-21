#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>

int nSecs;
int nr;
char buf [PIPE_BUF];

void aumenta(){
	nSecs++;
}

void diminui(){
	nSecs--;
}

void imprime(){
	write(1,buf,nr);
}

int readln (int fildes, char *buf, int n_bytes){
	int i; char c;
	for (i = 0; i < n_bytes && read(fildes,&c,1) == 1 && c != '\n';i++) buf[i] = c;
	buf[i++] = '\n';
	return (i == 1)? 0 : i;
}

int main (int argc, char * argv[]){
	if (argc != 2){
		perror("Erro ao passar os argumentos!");
		_exit(-1);
	}
	nSecs = atoi(argv[1]);
	signal(SIGINT,aumenta);
	signal(SIGQUIT,diminui);
	signal(SIGALRM,imprime);
	while((nr = readln(0,buf,PIPE_BUF)) > 0){
		alarm(nSecs);
		pause();
	}
	return 0;
}