#include <stdio.h>
#include <unistd.h>

int main() {

	printf("Antes\n");

	execl("./args", /* (diretoria)nome do fich exec. */ /* não se deve escrever depois do exec, pois o ls é aqui escrito em cima na memória e desaparece o printf
	*/	"batata", /* argv[0] */
		"cebola", /* argv[1] */
		"*.c",
		NULL);

	printf("Aconteceu um erro\n");
}