#include <stdio.h>
#include <unistd.h>

int main(){
	
	int pfd[2];
	char buf[100];
	char dados[1024];
	int n, i = 0;

	printf("Antes\n");

	pipe(pfd);
	
	if(fork()==0){

		close(pfd[0]);
		
		while(1){
			i++;
			n = write(pfd[1], dados, 1024);
			printf("Escrevi %d bytes!\n", n*i);
		}

		/*sleep(5);

		write(pfd[1], "cde", 3);

		sleep(3);
	*/
		printf("Terminei\n");
	//	_exit(0);
	}
	else{

		close(pfd[1]); //-->apenas estou a dizer que o pai deixa de ter o descritor de write do pipe na tabela de apontadores

		while(n = read(pfd[0], buf, 1) > 0){
			//printf("Antes\n");

			i++;

			if(i > 100){
				_exit(0);
			}

			//read(pfd[0], buf, 2);

			//sleep(1);

			printf("Li %d bytes\n", n);
		}
	}

	//printf("Depois\n");
}