#include <stdio.h>
#include <unistd.h>

int main(){
	
	int pfd[2];
	char buf[100];
	int n;

	printf("Antes\n");

	pipe(pfd);
	
	if(fork()==0){
		write(pfd[1], "ab", 2);

		sleep(5);

		write(pfd[1], "cde", 3);

		sleep(3);

		printf("Terminei\n");
	//	_exit(0);
	}
	else{

		close(pfd[1]); //-->apenas estou a dizer que o pai deixa de ter o descritor de write do pipe na tabela de apontadores

		while(n = read(pfd[0], buf, 1) > 0){
			//printf("Antes\n");

			//read(pfd[0], buf, 2);

			printf("Depois %c %d\n", buf[0], n);
		}
	}

	//printf("Depois\n");
}