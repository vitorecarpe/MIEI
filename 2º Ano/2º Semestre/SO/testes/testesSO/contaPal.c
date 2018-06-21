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

int readln (int fildes, char* buf, int nbytes){
	char c;int i;
	for(i = 0; i < nbytes && (read(fildes,&c,1) > 0) && c != '\n'; i++) buf[i] = c;
	buf[i] = '\0';
	return i;
}

int main(int argc, char* argv[])
{
	int i,nr,nLinhas = 0;
	int fd[2];
	pipe(fd);
	char buf [PIPE_BUF];
	for (i = 2; i < argc; i++){
		if(!fork()){
			dup2(fd[1],1);close(fd[1]);
			close(fd[0]);
			execlp("grep","grep","-n",argv[1],argv[i],NULL);
			_exit(-1);
		}
	}
	close(fd[1]);
	while((nr = readln(fd[0],buf,PIPE_BUF)) > 0){printf("%s\n",buf); nLinhas++;}
	printf("Existem %d linhas com a palavra %s.\n", nLinhas,argv[1]);
	return 0;
}