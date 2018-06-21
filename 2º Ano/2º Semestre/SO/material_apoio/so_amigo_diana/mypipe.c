#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main()
{
	int pd[2];
	pid_t pid1,pid2;
	pid1 = fork();
	if(pipe(fd)==-1)
	{
		perror("pipe");
		exit(EXIT_FAILURE);
	}
	switch(pid1)//para o mycat
	{
		case -1: perror("fork");
			 exit(EXIT_FAILURE);
		case 0: close(fd[0]);
			int fd = open("/etc/passwd",O_RDONLY);	
			dup2(fd,0)
			dup2(pd[0],1);
			execvp("mycat","mycat");
			
	}
	return 0;
}
