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

int* pids;
int size;
int nPids = 5;
char** cmd;

void incrementa (){
	int pid = fork();
	if(!pid){
		execvp(cmd[0],cmd);
		_exit(-1);
	}else{
		nPids++;
		if (size == nPids){
			size *= 1.5;
			pids = realloc(pids,size * sizeof(int));
		}
		pids[nPids - 1] = pid;
	}
}

void decrementa (){
	kill(pids[nPids -1],SIGKILL);
	nPids--;
}

int main(int argc, char* argv[]){
	int pidl,pidn,status;
	pids = (int *) malloc (5 * sizeof (int));
	cmd = argv + 1;
	signal(SIGINT,incrementa);
	signal(SIGQUIT,decrementa);
	int i,nr,nLinhas = 0;
	char buf [PIPE_BUF];
	for (i = 0; i < 5; i++){
		if(!fork()){
			execvp(argv[1],argv + 1);
			_exit(-1);
		}
	}
	while(1){
		pidl = wait(&status);
		pidn = fork();
		if (!pidn){
			execvp(argv[1],argv + 1);
			_exit(-1);
		}
		for (i = 0; i < nPids; i++)
			if (pids[i] == pidl) pids[i] = pidn;
	}
	return 0;
}