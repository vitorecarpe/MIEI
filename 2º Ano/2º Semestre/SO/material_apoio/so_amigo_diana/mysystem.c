#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <wait.h>
#include <stdlib.h>
#include <string.h>

//int mysystem(const char *command)

//implementar uma microshell
//void str2argv(const char *str, char *argv)
//utilizar o execlp para invocar sh -c command
//

//Versão do mysystem com o comando execlp
//char *argv[] <=> char **argv
// função wordexp -> faz uma expansão de um comando

int str2argv(const char *str, char ***argv)
{
	char *token=NULL;
	char *argumento[1024];
	char *arg=NULL;
	arg=strdup(str);
	int i;
	for(i=0,token=strtok(arg," ");token!=NULL && i!=1023;i++,token=strtok(NULL," "))
	{
		argumento[i]=strdup(token);
	}
	argumento[i] = NULL;
	*argv = argumento;//Array de apontadores para Strings
	free(arg);
	return 0;
}


int mysystemv1(const char *command)//utilizando o execvp
{
	pid_t pid;
	int i;
	int sonwait=0;
	int execstatus;
	int status;
	char **argumentos=NULL;
	str2argv(command,&argumentos);
	for(i=0;argumentos[i]!=NULL;i++)
		;
	if(strcmp(argumentos[i-1],"&")==0)
	{
		argumentos[i-1]=NULL;
		sonwait=1;
	}
	pid = fork();
	if(sonwait){
		switch(pid){
			case -1:exit(-1);
				break;
			case 0: execstatus = execvp(argumentos[0],argumentos);
				if(execstatus==-1) exit(-1);
				break;
			default: wait(&status);
				 break;
		}
	}
	else
	{
		switch(pid){
			case -1: exit(-1);
				 break;
			case 0: execstatus = execvp(argumentos[0],argumentos);
				if(execstatus==-1) exit(-1);
				break;
			default: break;
		}
	}
	return 0;
}

int myshell(const char *command)
{
	return 0;
}

int main()
{
	int i;
	char **teste = NULL;
	str2argv("ls -l", &teste);
	for(i=0;teste[i]!=NULL;i++)
		printf("%s\n",teste[i]);
	mysystemv1("ls -l");
	return 0;
}
