#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>

/*
* Contar quantos ficheiros (não hidden) estão numa diretoria
* ls dá-nos o numero de ficheiros, flag -1 separa por linhas
* output redirecionado para input de wc
* sacar primeiro numero do output (numero de linhas)
* ^ neste caso é simples porque basta usar flag -w
*/
int main(){

	printf("\t\t main start\n");
	int pd[2];
	pipe(pd); /* iniciar o pipe */

	if(pipe(pd) == -1){ /* caso pipe falhe */
		perror("pipe");
		exit(-1);
	}

	pid_t p = fork();
	if(p == -1){ /* caso fork falhe */
		perror("fork");
		exit(-1);
	}
	if(p == 0){ /* filho tratará de ls e redirecionar o seu output */
		close(pd[0]); /* nunca usamos o input do pipe , fechamos */
		dup2(pd[1],1); /* colocar o ouput do pipe como standard output */
		close(pd[1]); /* usamos o 1 agora, não pd[1], fechar */
		execlp("ls","ls","-1",NULL); /* exec() coloca o output no standard output, que vai ser agora pd[1] porque redirecionamos */
		exit(1);
	}
	if(p > 0){
		wait(NULL); /* esperar que filho acabe */
		char output1[] = "Numero de ficheiros nesta diretoria: ";
		write(1,output1,strlen(output1)); /* printf é menos confiavel e write permite melhor controlo sobre as strings */
		close(pd[1]); /* nunca usamos ouput, pai vai ler apenas */
		dup2(pd[0],0); /* redirecionar input */
		close(pd[0]); /* não usamos */
		execlp("wc","wc","-w", NULL); /* wc vai ler do standard input, que é agora p[0], o filho escreveu neste pipe com ls */
		exit(1);
	}

	return 0;
	}
