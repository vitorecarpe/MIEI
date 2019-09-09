// EXAME 29 JUNHO 2018

// GRUPO II

#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <signal.h>

/*
int *processos;
int i;

void alarm_handler(int signum){
	int procload = open("procload.txt", O_RDONLY, 0666);
	char load[3];
	int n, c;
	n=read(procload, load, 3);
	load[n]='\0';

	if(atoi(load)>100){
		for(c=0; c<i; c++){
			kill(processos[c],SIGSTOP);
		}
	} else{
		for(c=0; c<i; c++){
			kill(processos[c],SIGCONT);
		}
	}

	alarm(1);
}

int main(int argc, char** argv){
	int status;
	int flag=0;
	int o = 0;
	pid_t pid;
	signal(SIGALRM, alarm_handler);
	processos = (int*)malloc(sizeof(int)*(argc-1));
	for(i=1; i<argc; i++){
		if((pid=fork())==0){
			processos[i]=getpid();
			execlp(argv[i], argv[i], NULL);
			_exit(-1);
		}
	}

	alarm(1);

	for(i=1; i<argc; i++){
		wait(&status);
		if(WEXITSTATUS(status)==-1) flag=-1;
	}

	return flag;
}*/

///III

int main(int argc, char** argv){
	int i;
	int fd[2];
	pipe(fd);
	int procs[argc];

	for(i=1; i<argc; i++){
		if((pid=fork())==0){
			close(fd[0]);
			write(fd[1],argv[i],strlen(argv[i]));
			write(fd[1],' ',1);
			dup2(fd[1],1);
			close(fd[1]);
			procs[i]=getpid();
			execlp("termometro", "termometro", argv[i], NULL);
			_exit(-1);
		}
	}

	if((pid=fork())==0){
		close(fd[1]);
		dup2(fd[0],0);
		close(fd[0]);
		procs[0]=getpid();
		execlp("alarme", "alarme", NULL);
		_exit(-1);
	} 
	else {
		wait(NULL);
		for(i=0; i<argc, i++)
			kill(procs[i],SIGKILL);
		_exit(-1);
	}
	return 0;
}










