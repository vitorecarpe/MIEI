#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

#include "readln1.h"

/** Verifica a posção a mudar de linha(no array auxiliar) */
int pos_muda_linha(){
	int i, r = 0;
	
	for(i = pos_i; i < pos_f && aux[i] != '\0' && !r; i++){
		if(aux[i] == '\n') r = 1;
	}

	if(r) return (i-1);
	return -1;
}

/** Conta o número de linhas presentes no array auxliar */
int conta_l(){
	int i = 0, r = 0;
	
	for(i = 0; i < pos_f; i++){
		if(aux[i] == '\n') r++;
	}

	return r;
}

ssize_t readln (int fildes, char *buf) {
	int i=0, j=0, n = 1, posb = 0, r;

	if(pos_i == 0 && cont_lin == 0) {        /** Se o array auxiliar ão tiver nada vai fazer read do ficheiro */
		r = read(fildes, aux, 1024);
		cont_lin = conta_l(); //conta as linhas presentes no array
		pos_f = r; //atualiza a posição final do array auxiliar como sendo o número de carateres que leu
	}

	if(pos_i != 0 && cont_lin == 0) { //se a posição inicial for diferente de 0 e tiver 0 linhas tem de guardar aquilo que tem no array buffer(array a devolver) para depois poder ler para o array auxiliar
		
		if((pos_f - pos_i) > MAXELEM) return -1; //se o número de carateres for superior ao máximo permititdo dá erro

		else	{ //senão guarda os carateres no array buffer e volta a fazer read
			for(i = 0; i < (pos_f - pos_i); i++) (buf)[i] = aux[pos_i + i];

			posb = i;

			r = read(fildes, aux, 1024);
			pos_i = 0; //atualiza a posição inicial
			pos_f = r; //atualiza a posição final do array auxiliar como sendo o número de carateres que leu
			cont_lin = conta_l(); //conta o número de linhas presentes no array
		}
	}

	
	if(cont_lin == 0) return 0; //se o número de linhas for 0 é porque não há mais nada a ler
	

	j = pos_muda_linha(); //verifica a posição na qual vai mudar de linha

	if(j == -1) return -1; //se não existir posição a mudar de linha não pode ler e dá erro

	n = j - pos_i; //vê quantos carateres terá de ler

	if((j - pos_i) + posb > MAXELEM) return -1; //se o número de carateres que tiver de ler mais a posição em que está no array buffer for superior ao número de carateres permitidos dá erro

	if(posb != 0){	//se a posição do buffer for diferente de 0 vai colocar os carateres no buffer e devolve o número de carateres lidos
		
		for(i = 0; i <= n; i++) {
			(buf)[posb + i] = aux[pos_i + i];
		}
		
		pos_i += i; //atualiza a posição inicial(a partir da qual existe informação ainda não vista) no array auxiliar 
		cont_lin--; //atualiza o número de linhas presentes no array auxiliar

		return (posb + i);
	}

//	*(&buf) = &(aux[pos_i]);

	for(i = 0; i <= n; i++) {
		(buf)[posb + i] = aux[pos_i + i]; //aqui  a posição atual(a partir da qual é para inserir informação) do buffer será 0
	}

	n = j - pos_i + 1; //vê o número de caratares lidos(posição de mudar de linha(j) - posição inicial + 1)

	pos_i = j+1; //atualiza a posição inicial(a partir da qual existe informação ainda não vista) no array auxiliar 
	
	cont_lin--; //atualiza o número de linhas presentes no array auxiliar

	return (n);
}



int main (int argc, char **argv){
	int i=0, fd, j = 0, n, d, r = 0;
	char buffer[MAXELEM + 1];
	
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

	while((j < d) && (readln (fd,buffer)>0)){
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

		while((j < d) && (readln (fd,buffer)>0)){
			printf("%s", buffer);
			j++;
		}

		j = 0;
		close(fd);

		if(r && i != (argc -1)) printf("\n");
	}

	return 0;
}