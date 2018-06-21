#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
/* exec permite execução de comands de bash, tem vários tipos de argumentos
*int execl(const char *path, const char *arg0, ..., NULL);
*int execlp(const char *file, const char *arg0, ..., NULL);
*int execv(const char *path, char *const argv[]);
*int execvp(const char *file, char *const argv[]);

 l -> 		parametros individuais (como tal, lista de argumentos variavel)
 v -> 		parametros dentro de vetor
 p -> 		usa o path do ambiente onde está
 sem p ->	precisa de um caminho especifico, fora do ambiente de trabalho atual
*/

int main(){
	pid_t p = fork();
	int status = 0;

	if(p < 0){
		printf("Failure in fork\n");
		exit(-1);
	}

	if(p == 0){
		execlp("ls","ls","-l",NULL);
		exit(1);
	}

	else{
		wait(&status);
	}

	return 0;
	}
