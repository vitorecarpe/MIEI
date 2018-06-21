#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

/*
* Conceito: fork() cria uma copia do processo atual e começa a executar até terminar
* getpid retorna o process ID do processo
* getppid retorna o process ID do processo pai
* fork() retorna o process id do filho criado. Se fork() == 0 , estamos no ambiente do filho. Se fork() == -1, o system call falhou
* wait() permite que o pai execute apenas depois dos processos filho terminarem.
* quaisquer modificações feitas pelo filho são independentes do ambiente do pai, neste caso a mudança da variavelTeste
*/

int main(){

	pid_t p = fork();
	int status = 0;
	int variavelTeste = 4;

	if(p < 0){
		printf("Failure in fork\n");
		exit(-1);
	}

	if(p == 0){
		variavelTeste = -619;
		printf("|");
		printf("Sou o filho: ");
		printf("PID: %d. PPID: %d\n",getpid(),getppid());
		printf("Neste ambiente variavelTeste = %d",variavelTeste);
		printf("|\n");
		exit(1);
	}

	else{
		wait(&status);
		printf("|status: %d.", status);
		printf("Sou o pai: ");
		printf("PID: %d. PPID: %d\n",getpid(),getppid());
		printf("Neste ambiente variavelTeste = %d",variavelTeste);
		printf("|\n");
	}
	return 0;
	}
