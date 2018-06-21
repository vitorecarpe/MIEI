#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>

#define MAX 64

int main (){
	char buff [MAX];
	int n;

	int e = open("error.txt",O_WRONLY | O_CREAT | O_TRUNC,0666);
	dup2(e,2);close(e);

	int r = open("/etc/passwd",O_RDONLY);
	if(r == -1) {
		perror("Erro na leitura do ficheiro de input");
		_exit(-1);
	}
	dup2(r,0);close(r);

	int w = open("saida4.txt",O_WRONLY | O_CREAT | O_TRUNC,0666);
	if(w == -1) {
		perror("Erro na escrita do ficheiro de output");
		_exit(-1);
	}
	dup2(w,1);close(w);

	execlp("wc","wc",NULL);

	return 0;
}