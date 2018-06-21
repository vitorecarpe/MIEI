#include <stdio.h>
#include <unistd.h>
#include <string.h>

int main(){
	
	int pfd[2];
	char buf[100];
	char dados[1024];
	int n, i = 0;

	printf("Antes\n");

	pipe(pfd);
	
	if(fork()==0){

		close(pfd[0]);

		dup2(pfd[1], 1);

		close(pfd[1]); //--> não é preciso ter dois apontadores para o mesmo sitio

		execlp("ls", "ls", NULL);
		perror("ls");
		_exit(1);
		
		
	
		printf("Terminei\n");
	//	_exit(0);
	}
	else{

		close(pfd[1]); //-->apenas estou a dizer que o pai deixa de ter o descritor de write do pipe na tabela de apontadores

		while(n = read(pfd[0], buf, 1) > 0){
			

			printf("%c", toupper(buf[0]));
		}
	}

	//printf("Depois\n");
}