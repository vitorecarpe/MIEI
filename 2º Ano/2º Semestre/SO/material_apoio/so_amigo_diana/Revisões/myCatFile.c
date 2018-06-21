#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>

/* uso de write() e read() com open()
*  open(char* nomeFicheiro, modo de leitura, permissões)
*  O_RDONLY   read only
*  O_WRONLY	  write only
*  O_RDWR	  read & write
*  O_CREAT	  cria ficheiro se não existe, se já existe dá erro
*  O_APPEND   append da informação ao fim do ficheiro
*  O_TRUNC    se o ficheiro já existe, põe-no vazio e abre-o

*  As permissões são números em octogonal quando passados para binario
*representam as listas de permissão que obtemos com "ls -l"
*/

void catfile(char* filename){
	char buffer[4096];
	int fd = open(filename,O_RDONLY);
	int tam;
	while( read(fd,buffer,sizeof(buffer)) > 0){
		tam = strlen(buffer);
		write(1,buffer,tam);
		for(int i = 0; i < tam; i++)
			buffer[i] = '\0';
	}
}

int main(int argc, char* argv[]){
	if(argc <= 1){
		printf("not enough arguments\n");
		exit(-1);
	}
	else
		catfile(argv[1]);


	return 0;
	}
