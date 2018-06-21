#include <wordexp.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <wait.h>
#include <stdlib.h>


int mysystem3(const char *command)
{
	wordexp_t p;
	pid_t pid;
	int sonwait=0;
	int execstatus=0;
	int status;
	char **argumentos;
	wordexp(command,&p,0);
	argumentos=p.we_wordv;
	if(strcmp(argumentos[p.we_wordc],"&")==0)
	{
		argumentos[p.we_wordc]=NULL;
		p.we_wordc--;
		sonwait=1;
	}
	pid = fork();
	if(sonwait)
	{
		switch(pid)
		{
			case -1: exit(-1);
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
		switch(pid)
		{
			case -1: exit(-1);
				 break;
			case 0: execstatus = execvp(argumentos[0],argumentos);
				if(execstatus==-1) exit(-1);
				break;
			default: break;
		}
	}
	return execstatus;
}

int main()
{
	wordexp_t p;
	char **w;
	int i;
	wordexp("ls -l &",&p,0);
	w = p.we_wordv;
	for(i=0;i<p.we_wordc;i++)
	{
		printf("%d %s\n",i,w[i]);
	}
	wordfree(&p);
	//mysystem3("ls -l");
	return 0;
}
