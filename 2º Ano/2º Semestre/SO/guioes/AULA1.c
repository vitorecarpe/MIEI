// SO - AULA 1 - GUIÃO 1
// 15 Fev 2018 
// 100% Funcional!

/*
COMO COMPILAR EM C
$ gcc -Wall AULA1.c -o aula
$ ./aula
*/

#include <stdio.h>
#include <string.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h> /* O_RDONLY, O_WRONLY, O_CREAT, O_* */

/*
int open(const char *path, int oflag [, mode]);
ssize_t read(int fildes, void *buf, size_t nbyte);
ssize_t write(int fildes, const void *buf, size_t nbyte);
int close(int fildes);
*/

int main(){
	int fd = open("coiso.txt", O_RDONLY); //abre o coiso.txt
	int n; 						      //var que vai guardar o n bytes de coiso.txt
	char buffer[1024];
	if (fd==-1){						  //se nao conseguir abrir...
		perror("coiso.txt");
		close(fd);
		return 1; /*EXIT_FAILURE*/
	}
	n = read(fd, buffer, sizeof(buffer)); //tamanho do texto em coiso.txt e insere no buffer.
	write(1, buffer, n);				  //escreve pelo buffer os n bytes do coiso.txt.
	close(fd);							  //fecha o file.
	return 0; /*EXIT_SUCCESS*/
}

/*
O_RDONLY - só leitura
O_WRONLY - só escrita
O_RDONLY+O_WRONLY - O_RDWR
O_APPEND - escrita no final do ficheiro
O_TRUNC - trunca o ficheiro (apaga tudo)
O_CREAT - se não existir então o ficheiro deve ser criado
O_EXCL - assegurar que estamos a criar um ficheiro para não abrir um existente
*/



/* --- RESOLUÇÃO DO ANO PASSADO ---

//EXERCICIO 1
int main(){
	char c;
	while(read(0,&c,1)>0)
		write(1,&c,1);
	
	return 0;
}
int mycat2 (int argc, char *argv[]){
	int b = atoi(argv[i]);
	char minibuf[b];
	int n;

	while ((n=read(0, &minibuf, b))>0)
		write(1,&minibuf,n);
}*/