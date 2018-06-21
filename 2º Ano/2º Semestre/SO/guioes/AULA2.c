// SO - AULA 2 - GUIÃO 1
// 22 Fev 2018 
// 100% Funcional!

/*
COMO COMPILAR EM C
$ gcc -Wall AULA2.c -o aula
$ ./aula
*/

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h> /* O_RDONLY, O_WRONLY, O_CREAT, O_* */

//EXERCICIOS 5 e 6

ssize_t readln (int fildes, void *buf, size_t nbyte){
	ssize_t nb = 0;
	char* p = buf;
	while(nb < nbyte) {  
		ssize_t n = read(fildes, p, 1);
		if (n <= 0) break;     //não há file...
		nb++;
		if(*p == '\n') break;  //end of file...
		p++;
	}
	return nb;
}

char buf[1024];

int main(){
	int fd = open("laranjas.txt", O_RDONLY); //abre o laranjas.txt
	int nl=1;
	while (1){
		size_t n = sprintf(buf,"%6d  ", nl);
		ssize_t nb = readln(fd, buf+n, sizeof(buf)); //para ler do doc, escrever fd no 
													 //1º arg de readln. Para ler da
													 //consola, escrever 1.
		if(nb<=0) break;
		write(1, buf, n+nb);
		nl++;
	}
	return 0;
}




/* --- RESOLUÇÃO DO ANO PASSADO ---

está errada...

//EXERCICIO 5
int readlnAlt (int fildes, void *buff, size_t nbyte){
	char a;
	int linha=0, c=0;
	int f=0;
	while(f=read(fildes, &a, 1)>0 && c>nbyte && !linha){
		if (a=='\n') linha=1;
		else buff[c+1]=a;
	}
	if(c>=nbyte) return (nbyte+1);
	else if(linha) return(c+1);
	else return f;
}
*/