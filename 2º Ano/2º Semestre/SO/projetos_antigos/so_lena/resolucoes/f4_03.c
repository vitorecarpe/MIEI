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
	int f = open("saida3.txt", O_CREAT | O_WRONLY | O_TRUNC, 0666);
	//if (f == -1)

	if (!fork()){
		execlp("wc", "wc", NULL);
	}
}