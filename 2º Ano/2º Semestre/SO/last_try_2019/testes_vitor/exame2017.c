// EXAME 26 JUNHO 2017

// GRUPO II

#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <signal.h>

/*
int *cons;
int proc_on;
int n_cons;

void quit_handler(int signum){
	int i;
	if(proc_on==n_cons-1) proc_on=0; else proc_on++;
	for(i=0;i<n_cons;i++){
		if(i==proc_on)
			kill(cons[i],SIGCONT);
		else
			kill(cons[i],SIGSTOP);
	}
}

int main(int argc, char** argv){
	int p, c;
	pid_t pid;
	cons = (int*)malloc(sizeof(int)*argv[2]);
	n_cons = argv[2];
	proc_on=0;

	signal(SIGQUIT, quit_handler);

	for(p=0; p<argv[1]; p++){
		if((pid=fork())==0){
			close(fd[0]);
			dup2(fd[1],1);
			close(fd[1]);
			execlp("produtor","produtor",NULL);
		}
	}
	for(c=0; c<argv[2]; c++){
		if((pid=fork())==0){
			close(fd[1]);
			dup2(fd[0],0);
			close(fd[0]);
			consumidores[c]=getpid();
			execlp("consumidor","consumidor",NULL);
		}
	}

	alarm_handler(SIGQUIT);

	wait(NULL);

	return 0;
}*/

int main(int argc, char** argv){
	int fd[2];
	pipe(fd);
	pid_t pid;
	int stdout;
	int i;

	stdout = dup(1);
	dup2(fd[1],1);
	close(fd[1]);

	if((pid=fork())==0)
		execlp(argv[1], argv[1], NULL);

	dup2(fd[0],0);
	close(fd[0]);

	for(i=2; i<argc-1; i++){
		if((pid=fork())==0){
			execlp(argv[i], argv[i], NULL);
		}else wait(NULL);
	}

	if((pid=fork())==0){
		dup2(stdout,1);
		close(stdout);
		execlp(argv[argc-1], argv[argc-1], NULL);
	}
	wait(NULL);


	return 0;
}





































