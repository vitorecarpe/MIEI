#include <stdio.h>
#include <unistd.h>

int main() {

	printf("Antes\n");

	execlp("ls", /* (diretoria)nome do fich exec. */ /* não se deve escrever depois do exec, pois o ls é aqui escrito em cima na memória e desaparece o printf
	*/	"ls", /* argv[0] */
		"-l", /* argv[1] */
		NULL);

	printf("Aconteceu um erro\n");
}