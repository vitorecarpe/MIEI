#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>

#define MAX 64

int main (){
	int nr;
	char buf [40];
	int f = open("/etc/passwd",O_RDONLY);
	dup2(f,0);
	close(f);
	f = open("saida.txt",O_WRONLY | O_CREAT |O_TRUNC,0666);
	dup2(f,1);
	close(f);
	f = open("erros.txt",O_WRONLY | O_CREAT | O_TRUNC,0666);
	dup2(f,2);
	close(f);
	while ((nr = read(0,buf,39)) > 0)
		write(1,buf,nr);
}

/*
int main (){
	int n;
	char b[MAX];

	//redir output
	close(1);
	int f = open("saida.txt", O_CREAT | O_WRONLY | O_TRUNC, 0666);
	//if (f == -1)

	n = read(0,b,MAX);
	write(1,b,n);//o write esta a escrever no descritor 1 que redirecionamos para o ficheiro txt
	printf("lolada");//tb vai ser redirecionado
}
*/
/*ou
int main (){
	int n;
	char b[MAX];

	//redir output
	int f = open("saida.txt", O_CREAT | O_WRONLY | O_TRUNC, 0666);
	//if (f == -1)
	
	dup2(f,1);close(f); -- em vez de fecharmos o descritor 1
						-- pomos o 1 igual ao que criamos e fechamos 
						-- o que criamos

	n = read(0,b,MAX);
	write(1,b,n);//o write esta a escrever no descritor 1 que redirecionamos para o ficheiro txt
}
*/

//Nota: Usar o lsof -c "nome do programa", diz nos que 
//descritores é que estão abertos (descritores ou 1w 2u ...)