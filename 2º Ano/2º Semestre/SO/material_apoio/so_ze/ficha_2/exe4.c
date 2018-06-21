#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>


int main(){
	
	int N = 10;
	int i, status, cs;

	for(i = 0; i < N; i++){

		if(fork() == 0){
			printf("Tarefa 			i = %d \n", i);
			_exit(i);
		}


	}

	for(i = 0; i < N; i++){

		wait(&status);

		if(WIFEXITED(status)) {

			cs = WEXITSTATUS(status);

			printf("Terminou o %d\n", cs);
		}
	}
}