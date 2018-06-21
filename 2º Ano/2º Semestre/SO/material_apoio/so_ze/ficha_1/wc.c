#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

ssize_t readln (int fildes, char *buf, size_t nbyte) {
	int i=0, n;

	while ( i<(nbyte-1) && (n = read(fildes,  buf+i, 1))>0 && buf[i]!='\n') {
         i++;
     }

     if(i>=nbyte)
     		buf[i]=0;

    else
    		buf[i+1]= 0;


    if(n!=0) return i;
    else return -1;
}

void conta_palavras_carateres(char *buf, int *pb){
	int i;
	if(buf[0]!='\0' && buf[0]!='\n'){
		pb[0]++;
		pb[1]++;
		
		for(i=1; buf[i]!='\0';i++){
		
			if(buf[i]!=' ' && buf[i-1]==' ') {
				pb[0]++;
				pb[1]++;
			}
		
			else{
				if(buf[i]!=' ') pb[1]++;
			}

		}
	}
	if(buf[0] == '\n') pb[1]++;
}


int main (int argc, char **argv){
	int l[argc], pb[argc][2], i;
	char buffer[1000];

	for(i=0; i<argc; i++){
		l[i] = 0;
		pb[i][0] = pb[i][1] = 0;
	}
	
	if(argc==1){

		while((readln (0,buffer,1000)>-1)){
			l[0]++;
			conta_palavras_carateres(buffer, pb[0]);
		}

		printf("%d    %d    %d\n", l[0], pb[0][0], pb[0][1]);
	}

	else{

		for(i=1; i<argc; i++){
			int fd=open(argv[i], O_RDONLY);

			while((readln (fd,buffer,1000)>-1)){
				l[i]++;
				conta_palavras_carateres(buffer, pb[i]);
			}

			printf("%d    %d    %d   %s\n", l[i], pb[i][0], pb[i][1], argv[i]);

			pb[0][0] += pb[i][0];
			pb[0][1] += pb[i][1];
			l[0] += l[i];
		}

		if(argc > 2) printf("%d    %d    %d   total\n", l[0], pb[0][0], pb[0][1]); 
	}

	return 0;
}