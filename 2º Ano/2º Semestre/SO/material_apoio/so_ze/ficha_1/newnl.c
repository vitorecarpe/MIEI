#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

//#include "readln1.h"
#define MAXELEM 128

static char aux[1024];

static int cont_lin = 0;
static int pos_i = 0;
static int pos_f = 1024;


int pos_muda_linha(){
	int i, r = 0;
	
	for(i = pos_i; i < pos_f && aux[i] != '\0' && !r; i++){
		if(aux[i] == '\n') r = 1;
	}

	if(r) return (i-1);
	return -1;
}


int conta_l(){
	int i = 0, r = 0;
	
	for(i = pos_i; i < pos_f; i++){
		if(aux[i] == '\n') r++;
	}

	return r;
}

ssize_t readln (int fildes, char *buf) {
	int i=0, j=0, n = 1, posb = 0, r;

	if(pos_i == 0 && cont_lin == 0) {
		r = read(fildes, aux, 1024);
		cont_lin = conta_l();
		pos_f = r;
	}

	if(pos_i != 0 && cont_lin == 0) {
		
		if((pos_f - pos_i) > MAXELEM) return -1;

		else	{
			for(i = 0; i < (pos_f - pos_i); i++) (buf)[i] = aux[pos_i + i];

			posb = i;

			r = read(fildes, aux, 1024);
			pos_i = 0;
			pos_f = r;
			cont_lin = conta_l();
		}
	}

	
	if(cont_lin == 0) return 0;
	

	j = pos_muda_linha();

	if(j == -1) return -1;

	n = j - pos_i;

	if(n > MAXELEM) return -1;

	if(posb != 0){	
		
		for(i = 0; i <= n; i++) {
			(buf)[posb + i] = aux[pos_i + i];
		}
		
		pos_i += i;
		cont_lin--;

		return (posb + i);
	}

//	*(&buf) = &(aux[pos_i]);

	for(i = 0; i <= n; i++) {
		(buf)[posb + i] = aux[pos_i + i];
	}

	n = j - pos_i + 1;

	pos_i = j+1;

	cont_lin--;

	return (n);
}

int main (int argc, char *argv[]) {
	char buffer[MAXELEM + 1];
	char str[7];
	int n, i = 1;
	int fd;

	if(argc == 1) { fd = 0; i = 0;}

	else fd=open(argv[1], O_RDONLY);

for(i; i < argc; i++){
	   if(i != 0) fd=open(argv[1], O_RDONLY);

	   	while ((n = readln (fd, buffer)) > 0 ){
	   		sprintf(str, "%d     ", i);
	   		write(1, str, 6);
	   		write(1, buffer, n);
	   		i++;
	   	}
   }
   	
   	if(n == -1){
   		printf("Fatal Error!\n");
   		return 1;
   	}

	return 0;
}