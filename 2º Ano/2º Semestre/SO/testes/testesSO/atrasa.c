#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>

int nSecs;

void aumenta(){
	nSecs++;
}

void diminui(){
	nSecs--;
}

void tempo(){
	nSecs--;
	if(nSecs > 0) alarm(1);
	else{
		int nr;
		int buf[100];
		while((nr = read(0,buf,100)) > 0)
			write(1,buf,nr);
		_exit(0);
	}
}

int main (int argc, char * argv[]){
	if (argc != 2){
		perror("Erro ao passar os argumentos!");
		_exit(-1);
	}
	nSecs = atoi(argv[1]);
	signal(SIGINT,aumenta);
	signal(SIGQUIT,diminui);
	signal(SIGALRM,tempo);
	alarm(1);
	while(1)
		pause();
}