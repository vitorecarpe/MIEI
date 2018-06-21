#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

ssize_t readln (int fildes, char *buf, size_t nbyte) {
	int i=0;

	while ( i<(nbyte-1) && read(fildes,  buf+i, 1)>0 && buf[i]!='\n') {
         i++;
     }

     if(i>=nbyte){
//	     	buf[nbyte-2] = '\n';
//	     	buf[nbyte-1] = 0;
//	     	return 0;
     		buf[i]=0;
     }

    else
    		buf[i+1]= 0;


     return i;
}

int pertence(char a, char *b){
	int i, r=0;
	for(i=0; b[i]!='\0' && !r; i++){
		if(a==b[i]) r=1;
	}
	return r;
}

int main (int argc, char **argv){
	int i=0, fd;
	char buffer[1000];

	
	if(argc==1){
		printf("ERRO!\n");
		return 1;
	}

	char d = (*argv[1]);

	if(argc==2){
		while((readln (0,buffer,1000)>0)){
//			buffer[9] = '\0';
//			buffer[8] = '\n';
			if(pertence(d, buffer))
					printf("%s", buffer);
		}
	}

	else{

		for(i=2; i<argc; i++){
			
			if((fd=open(argv[i], O_RDONLY)) == -1){
				printf("Fatal error!\n");
				return 1;
			}
			
			while((readln (fd,buffer,1000)>0)){

				if(pertence(d, buffer)){
						if(argc==3)
							printf("%s", buffer);
						else
							printf("%s: %s", argv[i], buffer);
					}
				}
			close(fd);
			}
		}

	return 0;
}
