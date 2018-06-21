#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main()
{
	char temp[] = "ola mundo\n";
	int fd[2];
	char temp2[200];
	if(pipe(fd)==-1)
	{
		perror("erro no pipe");
		exit(-1);
	}
	write(fd[1],temp,strlen(temp)+1);//+1 para escrever o \0
	read(fd[0],&temp2,sizeof(temp2));
	write(1,temp2,strlen(temp2));
	//fputs(temp2,stdout);
	close(fd[0]);
	close(fd[1]);
	return 0;
}
