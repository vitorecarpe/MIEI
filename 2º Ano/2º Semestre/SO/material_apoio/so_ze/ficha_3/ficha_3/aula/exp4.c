#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {

	char *opts[] = {"ls", "-l", "-a", NULL};
	
	printf("Antes\n");

	if(fork() == 0){
		execl("/bin/ls", /* (diretoria)nome do fich exec. */ /* não se deve escrever depois do exec, pois o ls é aqui escrito em cima na memória e desaparece o printf
	*/	"ls", /* argv[0] */
		"-l", /* argv[1] */
		NULL);

		perror("/bin/ls");
		_exit(1);

	}

	else wait(NULL);

	printf("Depois\n");
}