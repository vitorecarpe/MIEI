#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>


int main(){
	
	int N = 10;
	int i;

	for(i = 0; i < N; i++){

		if(fork() == 0){
			printf("Tarefa 			i = %d \n", i);
			_exit(0);
		}

		

		wait(NULL);

		printf("Terminou\n");
	}
}