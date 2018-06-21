#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>

int main()
{
	pid_t pid;
	char total[10];
	int fd,n;
	int pd[2];
	int ttotal=0;
	int tam;
	char temp[200];
	char temp2[200];
	if(pipe(pd)==-1)
	{
		perror("Erro de pipe");
		exit(-1);
	}
	pid = fork();
	switch(pid)
	{
		case -1: perror("fork");
			 break;
		case 0:  close(pd[1]);
			 while((tam=read(pd[0],temp,sizeof(temp))) > 0)
				{
					ttotal += tam;
				//	tam=0;
				}
			close(pd[0]);
			sprintf(total,"%d\n",ttotal);
			write(1,total,strlen(total));
			exit(EXIT_SUCCESS);
		default: close(pd[0]);//Este descritor não é utilizado no pai
			 fd = open("/etc/passwd",O_RDONLY);
			 while((n=read(fd,temp2,sizeof(temp2))) > 0)
			 {
				write(pd[1],temp2,n); 
			 }
			 close(pd[1]);
			 close(fd);
	}
	return 0;
}
