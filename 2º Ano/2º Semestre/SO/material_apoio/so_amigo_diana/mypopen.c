#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>

FILE * mypopen(const char *command, const char *mode)
{
	FILE *fp;
	pid_t pid;
	int pd[2];
	if(pipe(pd)==-1)
	{
		perror("Erro de pipe");
		exit(-1);
	}
	pid = fork();
	if(strcmp(mode,"r")==0)
	{
	switch (pid)
	{
		case -1: perror("fork");
			 exit(EXIT_FAILURE);
			 break;
		case 0: close(pd[0]);
			dup2(pd[1], 1);
			close(pd[1]);
			execlp("sh","sh","-c",command,NULL);
			perror("execlp");
			exit(EXIT_FAILURE);
		default: close(pd[1]);
			 fp=fdopen(pd[0],mode);
			 //close(pd[0]);
			 return fp;
	}
	}
	else
	{
	switch (pid)
	{
		case -1: perror("fork");
			 exit(EXIT_FAILURE);
			 break;
		case 0: close(pd[1]);
			dup2(pd[0], 0);
			close(pd[0]);
			execlp("sh","sh","-c",command,NULL);
			perror("execlp");
			exit(EXIT_FAILURE);
		default: close(pd[0]);
			 fp=fdopen(pd[1],mode);
			 //close(pd[0]);
			 return fp;
	}

	}
}

void mypclose(FILE *stream)
{
	fclose(stream);
	int status;
	wait(&status);
}

int main()
{
	FILE *fp;
	char temp[] = "Texto qualquer!\n";
	fp = mypopen("wc -l","w");
	fwrite(temp,sizeof(temp),1,fp);
	fwrite(temp,sizeof(temp),1,fp);
	char buffer[4096];
	while(fgets(buffer,sizeof(buffer),fp)!=NULL)
		printf("%s",buffer);
	mypclose(fp);
	return 0;
}
