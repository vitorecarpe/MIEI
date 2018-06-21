#include <stdio.h>
#include <unistd.h>
#include <string.h>

int main(){
	
	int pfd[2];
	char buf[100];
	char dados[1024];
	int n, i = 0;

	//printf("Antes\n");

	pipe(pfd);
	
	if(fork()==0){

		close(pfd[1]);

		dup2(pfd[0], 0);
		close(pfd[0]);

		execlp("wc", "wc", NULL);
		perror("wc");
		_exit(1);
		
		
	
		//printf("Terminei\n");
	//	_exit(0);
	}

	else{


		close(pfd[0]); //-->apenas estou a dizer que o pai deixa de ter o descritor de write do pipe na tabela de apontadores

		write(pfd[1], "pal1 pal2\npal3\n", 15);

		close(pfd[1]);

		wait(NULL);
	}



	//printf("Depois\n");
}