#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

#include "readln1.h"

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
	
	for(i = 0; i < pos_f; i++){
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

	if((j - pos_i) > MAXELEM) return -1;

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


int main(int argc, char *argv[]){
	char buffer[1024];
	int fildes, n, r, pos = 0;

	if(argc == 1) fildes = 0;

	else{

		 if((fildes = open(argv[1], O_RDONLY)) == -1) {
		 	printf("Erro.O ficheiro não existe!\n");
		 	return 1;
		}
	}

	n = readln(fildes, buffer+pos);
	
	while(n > 0){

		if((1024 - (pos + n)) < MAXELEM) {
			if(write(1, buffer, pos + n) == -1) {
				printf("Erro!\n");
				return 1;
			} 
		}
		else pos += n;

		n = readln(fildes, buffer + pos);

		if(n == 0 && pos!=0) write(1, buffer, pos);
	}

	if(n == -1){
		printf("Erro!\n");
		return 1;
	}

	return 0;
}