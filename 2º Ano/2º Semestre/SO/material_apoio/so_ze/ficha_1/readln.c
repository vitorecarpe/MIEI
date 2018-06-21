#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

ssize_t readln (int fildes, char *buf, size_t nbyte) {
	int i=0, n;

	while ( i<(nbyte-1) && (n =read(fildes,  buf+i, 1)) > 0 && buf[i] != '\n') {
         i++;
     }

     if(i>=nbyte || n == 0){
	     	buf[i] = 0;
	     	return i;
	     }
    else
    		buf[i+1]= 0;

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