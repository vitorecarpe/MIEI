#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

ssize_t readln (int fildes, char *buf, size_t nbyte) {
	int i = 0, n;

	while ( i<(nbyte-1) && (n=read(fildes,  buf+i, 1)) > 0 && buf[i] != '\n') {
         i++;
     }

     if(i>=nbyte)
	     	buf[i] = 0;
    else
	    	buf[i+1] = 0;

     if(n!=0) return i;
     else return -1;
}

int main (int argc, char ** argv) {
	char buffer[1000];
	int j, i=1, n, fd;
	
	if(argc==1){
		
		while(readln (0,buffer,1000) > 0){
			printf("     %d   %s", i, buffer);
			i++;
			}

		}

	else{

		for(j=1; j<argc; j++){
		
			if ((fd=open(argv[j], O_RDONLY)) == -1) {
				printf("Fatal error!\n"); 
				return 1;
			}
			
			while( (n = readln (fd,buffer,1000)) > -1){
				if(n>0){
					printf("     %d   %s", i, buffer);
					i++;
					}
				else {
					printf("     %s", buffer);
					}
				}

			close(fd);			
			}
		}
	return 0;
}