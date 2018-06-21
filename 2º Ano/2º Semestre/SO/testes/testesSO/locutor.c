#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <limits.h>

int sec = 0;
char bufL [PIPE_BUF];
int nr = 0;

void imprime (){
	sprintf(bufL,"%s\n",bufL);
	write(1,bufL,nr+1);
}

int readln (int fildes, char* buf, int nbytes){
	char c;int i;
	for(i = 0; i < nbytes && (read(fildes,&c,1) > 0) && c != '\n'; i++) buf[i] = c;
		buf[i++] = '\0';
	return i;
}

int main(int argc, char* argv[])
{
	char bufT [PIPE_BUF];
	int o = open(argv[1],O_RDONLY);
	signal(SIGALRM,imprime);
	if (o == -1){
		perror("Erro na abertura do ficheiro!");
		_exit(-1);
	}
	dup2(o,0);close(o);
	while(readln(0,bufT,PIPE_BUF) > 0){
		nr = readln(0,bufL,PIPE_BUF);
		alarm(atoi(bufT) - sec);
		pause();
		sec = atoi(bufT);
	}
	return 0;
}