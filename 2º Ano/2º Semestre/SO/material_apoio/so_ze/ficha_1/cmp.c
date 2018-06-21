#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

ssize_t readln (int fildes, char *buf, size_t nbyte) {
	int i=0, n;

	while ( i<(nbyte-1) && (n = read(fildes,  buf+i, 1)) > 0 && buf[i] != '\n') {
         i++;
     }

     if(i>=nbyte)
	     	buf[i] = 0;
    else
    		buf[i+1]= 0;

    if(n != 0) return i;
    else return -1;
}

int compara_linha(char *l1, char *l2, int *t){
	int i, r = 1;
	
	for(i = 0; l1[i] != '\0' && l2[i] != '\0' && r; i++){
		if(l1[i] != l2[i]) r = 0;
		else t[1]++;
	}

	if(l1[i] != l2[i]) r = 0;

	return r;
}

int main(int argc, char **argv){
	if(argc == 1){
		return 1;
	}

	if(argc > 3) {
		printf("Erro! Só pode comparar 2 ficheiros.\n");
		return 1;
	}

	int fd1, fd2, r = 1;
	int lc[2], n1, n2;
	char buffer1[1000], buffer2[1000];

	if((fd1 = open(argv[1], O_RDONLY)) == -1){
		printf("Fatal error!\n");
		return 1;
	}
	
	lc[0] = 1;
	lc[1] = 1;

	if(argc == 2) fd2 = 0;

	else  {
		if((fd2 = open(argv[2], O_RDONLY)) == -1) {
			printf("Fatal error!\n");
			return 1;
		}
	}

	while(r && ((n2 = readln(fd2, buffer2, 1000)) > -1 && (n1 = readln(fd1, buffer1, 1000)) > -1)){

		r = compara_linha(buffer1, buffer2, lc);
		if(r) lc[0]++; 
	}

	if(!r) {

		if(argc == 2)  printf("%s  -  differ: char %d,  line %d\n", argv[1], lc[1], lc[0]);

		else printf("%s  %s  -  differ: char %d,  line %d\n", argv[1], argv[2], lc[1], lc[0]);

		return 0;
	}

	if(n2 == -1) n1 = readln(fd1, buffer1, 1000);

	if(n1 != n2) {
		if(n1 == -1) printf("EOF %s\n", argv[1]);
		
		else {
			
			if(argc > 2) printf("EOF %s\n", argv[2]);

			else  printf("EOF ecrã\n");
		}
		return 0;
	}


	return 0;
}