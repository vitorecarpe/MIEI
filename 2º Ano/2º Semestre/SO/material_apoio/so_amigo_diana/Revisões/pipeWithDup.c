#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
#include <fcntl.h>

/*
* System calls pipe() , dup() e dup2()
* há 3 file descriptors constantes:
* 0 é usado para standard input
* 1 é usado para standard output
* 2 é usado para standard error
* Aqui o filho usa close(0) para remover o file descriptor atual do lugar 0, e  dup(pd[0]) cria uma copia do file descriptor de argumento para o primeiro sitio disponivel(neste caso 0, o nosso standard input). Portanto, ao executar wc, o standard input que vai procurar vai passar a ser p[0]. O pai faz o mesmo mas para o output, e passa o conteudo de ls que normalmente iria para o terminal(1) mas desta vez vai para p[1], como tal a leitura de p[0] por parte de wc vai ser o output de ls
* Usamos pipes para executar ls | wc
*
*/

int main(){

	int pd[2];
	pipe(pd);
	int status = 0;

 	pid_t p = fork();

	if(p == -1){
		printf("error\n");
		exit(-1);
	}

	if(p == 0){
		// filho
		close(0);
		int retVal = dup(pd[0]);
		close(pd[0]);
		close(pd[1]);
		execlp("wc","wc",NULL);
		dup2(retVal,0);

	}

	if(p > 0){
		// pai
		close(1);
		int retVal = dup(pd[1]);
		execlp("ls","ls",NULL);
		close(pd[0]);
		close(pd[1]);
		dup2(retVal,1);
	}

	return 0;
	}
