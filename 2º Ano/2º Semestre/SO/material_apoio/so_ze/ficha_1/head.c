#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>


ssize_t readln (int fildes, char *buf, size_t nbyte) {
	int i = 0, n, j = 0;

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

int main (int argc, char **argv){
	int i=0, fd, j = 0, n, d, r = 0;
	char buffer[1000];
	
	if(argc==1){
		printf("ERRO!\n");
		return 1;
	}

	if(argv[1][0] == '-') d = atoi(argv[1]) * (-1);
	
	else d = 10;


	if(argc == 2) {
		if(argv[1][0] == '-') {
			fd = 0;
			i = 1;
			}

		else {

			if((fd = open(argv[1], O_RDONLY)) == -1) {
				printf("Fatal error!\n");
				return 1;
			}
			i = 1;
		}
	}

	else{

		if(argv[1][0] == '-') {
			i = 2;

			if((fd = open(argv[2], O_RDONLY)) == -1) {
				printf("Fatal error!\n");
				return 1;
			}
		}
		else {
			i = 1;
			
			if((fd = open(argv[1], O_RDONLY)) == -1) {
				printf("Fatal error!\n");
				return 1;
			}
		}
	}

	if(((argc == 3) && (argv[1][0] != '-')) || (argc > 3)) r = 1;

	if(r){
		printf("==> %s <==\n", argv[i]);
	}

	while((j < d) && (readln (fd,buffer,1000)>-1)){
				printf("%s", buffer);
				j++;
			}

	if(r && i != (argc -1)) printf("\n");

	i++;
	j = 0;
	close(fd);

	for(i; i<argc; i++){
		
		if ((fd = open(argv[i], O_RDONLY)) == -1) {
			printf("Fatal error!\n");
			return 1;
		}

		if(r){
			printf("==> %s <==\n", argv[i]);
		}

		while((j < d) && (readln (fd,buffer,1000)>-1)){
			printf("%s", buffer);
			j++;
		}

		j = 0;
		close(fd);

		if(r && i != (argc -1)) printf("\n");
	}


/*	if(argc==2){
		
		if(argv[1][0]=='-'){
		
			int d = atoi(argv[1]) * (-1);
		
			while((i < d) && (readln (0,buffer,1000)>-1)){
				printf("%s", buffer);
				i++;
			}
		}
		
		else{
			
			fd = open(argv[1], O_RDONLY);

			while((i < 10) && (readln (fd,buffer,1000)>-1)){
				printf("%s", buffer);
				i++;
			}
		}
	}

	else{
		
		if(argv[1][0]=='-'){
		
			int d = atoi(argv[1]) * (-1);
		
			for(j=2; j<argc; j++){
				fd = open(argv[j], O_RDONLY);

				if(argc>3){
					printf("==> %s <==\n", argv[j]);
				}
		
				while((i < d) && (readln (fd,buffer,1000)>-1)){
					printf("%s", buffer);
					i++;
				}

				i=0;
				close(fd);

				if(argc>3 && j!=(argc-1)) printf("\n");
			}
		}
		
		else{
			
			for(j=1; j<argc; j++){
				fd = open(argv[j], O_RDONLY);
				
				printf("==> %s <==\n", argv[j]);

				while((i < 10) && (readln (fd,buffer,1000)>-1)){
					printf("%s", buffer);
					i++;
				}

				i=0;
				close(fd);

				if(j!=(argc-1)) printf("\n");
			}
		}

	}
*/
	return 0;
}