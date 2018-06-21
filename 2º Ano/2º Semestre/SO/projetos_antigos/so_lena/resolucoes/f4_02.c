#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>

#define MAX 64

int main (){
	int n;
	char b[MAX];

	//redir output
	close(1);
	int f = open("saida1.txt", O_CREAT | O_WRONLY | O_TRUNC, 0666);
	//if (f == -1)

	if (!fork()){
		n = read(0,b,MAX);
		write(1,b,n);//o write esta a escrever no descritor 1 que redirecionamos para o ficheiro txt
		printf("lolada");//tb vai ser redirecionado
	} else wait(0);
}