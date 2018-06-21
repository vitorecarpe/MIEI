#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main()
{
	pid_t pid;
	int pd[2];
	char temp[12];
	char hello[] = "ola mundo\n";
	if(pipe(pd) == -1)
	{
		perror("Erro no pipe");
		exit(-1);
	}
	pid = fork();
	switch (pid)
	{
		case -1: perror("Erro");
			 exit(-1);
		case 0: close(pd[1]);
			read(pd[0],temp,sizeof(temp));
			close(pd[0]);
			write(1,temp,strlen(temp));
			break;
		default: close(pd[0]);
			 write(pd[1],hello,sizeof(hello));
			 close(pd[1]);
		 	 break;
	}
	return 0;
}
