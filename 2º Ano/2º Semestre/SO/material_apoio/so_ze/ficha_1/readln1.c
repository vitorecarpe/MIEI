#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

//#include "readln1.h"

int pos_inicial = 0;
int n_elementos = 0;
char aux[1024];

static int readchar(int fildes, char* buf) {

        if (pos_inicial == n_elementos) {
                n_elementos = read(fildes, aux, 1024);
                pos_inicial = 0;
        }

        if(n_elementos == 0) return 0;

        *buf = aux[pos_inicial];

        pos_inicial += 1;

        return 1;
}



ssize_t readln (int fildes, char *buf, size_t nbyte) {
	int i=0;

	
	int n = readchar(fildes, buf);


	while ( i<(nbyte-1) && n > 0 && buf[i] != '\n') {
         i++;

         n = readchar(fildes, buf + i);
     }

   if(i>=nbyte || n == 0) {
	     	buf[i] = 0;
	     	return i;
	     }
    else
    		buf[i+1]= '\0';

     return (i+1);
}

int main (int argc, char *argv[]) {
//	pos_inicial = 0;
//	n_elementos = 0;

	char buffer[128 + 1];
	int n;
	int fd;

	if(argc == 1) fd = 0;

	else	fd = open(argv[1], O_RDONLY);

   	n = readln (fd, buffer, 128);

//   	printf("%d\n", n);
   	
   	if(n == -1){
   		printf("Fatal Error!\n");
   		return 1;
   	}
   	
   	if(n == 0) printf("Não existem linhas\n");
	
	else {
		while(n > 0) {
			write(1, buffer, n);
			n = readln(fd, buffer, 128);
//			printf("%d\n", n);
		}
	}

	return 0;
}