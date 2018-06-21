#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
/* usar o system call exec() para executar por um processo filho o executavel printArgumentos que está nesta directoria
*/

int main(int argc, char* argv[]){

	pid_t p = fork();
	int status = 0;
	argv[argc] = NULL; // em todos os exec(), tem de terminar com NULL

	if(p < 0){
		printf("Failure in fork\n");
		exit(-1);
	}

	if(p == 0){
		execvp("./printArgumentos",argv);
		exit(1);
	}

	else{
		wait(&status);
		printf("Processo pai, filho acabou de imprimir os argumentos deste main.\n");

	}

	return 0;
	}
