#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>

int main()
{
int i;
pid_t pid;
for(i=1;i<=10;i++)
{
	pid = fork();
	switch(pid)
	{
		case -1: perror("Erro de fork\n");
			 exit(-1);
			 break;
		case 0:  printf("Sou o filho, processo nº %d\n",getpid());
			 exit(0);
		default: printf("Sou o pai, processo nº %d\n",getppid());
			 break;
	}
}
return 0;
}
