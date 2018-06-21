#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>

/* System call pipe()
*  Tem sempre 2 indices
*  Inicializado com pipe()
*  indice 0 para input
*  indice 1 para output
*/

int main(){
	pid_t p;
	int fd[2];
	int ret = pipe(fd);
	char buffer[4096];
	int status = 0;

	/* error handling */
	if(ret == -1){
		printf("error on pipe\n");
		exit(-1);
	}

	p = fork();

	if(p < 0){
		printf("error on fork\n");
		exit(-1);
	}

	if(p == 0){
		/* filho */
		write(fd[1],"ola teste",9);
		exit(1);
	}

	if(p > 0){
		/* pai */
		wait(&status); /* esperar que filho acabe */
		read(fd[0],buffer,10);
		printf("conteudo em buffer: %s\n",buffer);
	}


	return 0;
	}
