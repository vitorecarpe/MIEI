#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

ssize_t readln(int fd,char *buffer,size_t count)
{
	int i=0;
	int n;
	while(i < count && (n=read(fd,&buffer[i],1)) > 0 && buffer[i++]!='\n')
		;
	return n == -1 ? n : i;
}

void mynl(const char *filename)
{
	char buffer[4096];
	int c = 1,i;
	int fd = open(filename,O_RDONLY);
	if(fd == -1)
	{
		perror("open error");
		return;
	}
	while(readln(fd,buffer,sizeof(buffer)) > 0)
	{
		//write(1,"%3d.",4);
		char linha[10];
		snprintf(linha,10,"%4d\t ",c);
		write(1,linha,10);
		int tam = strlen(buffer);
		//printf("%s",buffer);
		write(1,buffer,tam);
		c++;
		for(i=0;i<tam;i++)
			buffer[i]='\0';
	}
}

int main(int argc, char **argv)
{
	if(argc==2)
	{
		mynl(argv[1]);
	}
	else
		printf("Número de parâmetros insuficiente\n");
	return 0;
}
