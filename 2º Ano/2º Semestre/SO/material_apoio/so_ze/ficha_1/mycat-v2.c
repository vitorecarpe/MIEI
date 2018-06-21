#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

int main (int argc, char **argv) {
	char c[32];
	int fd, i, n;

	if(argc==1) {
		while ((n = read(0,&c,32)) > 0) {
		write(1,&c,n);	
	   }
	}

	else{
		int fd, i;

		for(i=1; i < argc; i++){
			
			if((fd = open(argv[i], O_RDONLY)) == -1){
				printf("Fatal error!\n");
				return 1;
			}

			while ((n = read(fd,&c,32)) > 0) {
				write(1,&c,n);	
			}
			close(fd);
		}
	}
   return 0;
}