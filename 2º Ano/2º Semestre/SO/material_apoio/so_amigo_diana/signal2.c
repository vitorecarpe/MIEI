#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <stdlib.h>

void hand(int signo)
{
	printf("Recebi o sinal: %d\n",signo);
}
/*
int main()
{
	pid_t pid;
	pid = fork();
	if(pid==-1) exit(-1);
	if(pid==0)
	{
		signal(SIGINT,hand);
		while(1);
	}
	else
	{
		kill(pid,SIGINT);
	}
	return 0;
}
*/
int main()
{
	pid_t pid;
	pid = fork();
	switch(pid)
	{
		case 0: signal(SIGUSR1,hand);
			pause();
			exit(EXIT_SUCCESS);
		case -1: perror("fork");
			 exit(EXIT_FAILURE);
		default: kill(pid,SIGUSR1);
	}
	return 0;
}
