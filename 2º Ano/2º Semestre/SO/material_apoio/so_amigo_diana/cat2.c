/*
 * int read(int fd,char *buffer,size_t size);
 * int write(int fd,const char *buffer,size_t size);
 *
 * int open(const char* filename,int mode,int perms);
 * int close(int fd);
 *
 * #include <sys/types.h>
 * #include <fcntl.h>
 * #include <unistd.h>
 *
 * O_RDONLY
 * O_WRONLY
 * O_RDWR
 * O_APPEND
 * O_TRUNC
 * O_CREAT
 * O_EXCL
 *
 * Para juntar vários utilizar '+' ou '|'
 *
 * Ao criar um ficheiro devo especificar as permissões de criação
 *
 * PERMISSÕES: U G O (rwx)
 */

/* Este versão do cat é capaz de ler igualmente ficheiros
 */

#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>

void mycat()
{
	char buffer[4096];
	while(read(0,buffer,sizeof(buffer)) > 0)
	{
		int tam = strlen(buffer);
		int i;
		write(1,buffer,tam);
		for(i=0;i<tam;i++)
			buffer[i]='\0';
	}
}

void catfile(const char *filename)
{
	int fd = open(filename,O_RDONLY);
	char buffer[4096];
	if(fd == -1)
	{
		perror("open error");
		return;
	}
	while(read(fd,buffer,sizeof(buffer)) > 0)
	{
		int tam = strlen(buffer);
		int i;
		write(1,buffer,tam);
		for(i=0;i<tam;i++)
			buffer[i]='\0';
	}
	close(fd);
}

int main(int argc, char **argv)
{
	if(argc==2)
	{
		catfile(argv[1]);
	}
	else
		mycat();
	return 0;
}
