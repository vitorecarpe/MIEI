#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main (){
	char c = 'a';
	int r,i;
	r = open("10mb.dat",O_CREAT | O_WRONLY,0600);// r toma o valor do file descriptor (fildes)
	if (r == -1){perror("rip");exit(-1);}

	for(i = 0;i < 10*1024*1024;i++)
		write(r,&c,1);//write(fildes,onde se encontra o caracter,numero de caracteres escritos)
	_exit(0);
}

/*octal representation:
binary octal permissions
 000    0       nothing
 100    4       read
 010	2		write
 001	1		execute
 110	6		read & write
 111	7		rwx
 ...
 */