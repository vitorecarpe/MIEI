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

#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>

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

int main()
{
	mycat();
	return 0;
}
