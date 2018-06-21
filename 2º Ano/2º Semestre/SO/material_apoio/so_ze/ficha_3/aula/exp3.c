#include <stdio.h>
#include <unistd.h>

int main() {

	char *opts[] = {"ls", "-l", "-a", NULL};
	
	printf("Antes com EXECV\n");

	execvp("ls", /* nome do fich exec. */ /* não se deve escrever depois do exec, pois o ls é aqui escrito em cima na memória e desaparece o printf
	*/	opts);

	printf("Aconteceu um erro\n");
}