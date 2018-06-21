#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>

void parteString(char* args[], char *comando){
	int i = 0;
/*	for(i = 0; comando[i] != '\0'; i++){
		if(comando[i] == ' ') {
			c[k][j] = '\0';
			k++;
			j = 0;
		}

		else{
			c[k][j] = comando[i];
			j++;
		}
	}

	c[k] = '\0';
*/	args[i] = strtok(comando, " \t\n");

	while(args[i] != NULL){
		args[++i] = strtok(NULL, " \t\n");
	}	
}

int mysystem(char* comando){

	char* args[10];

	parteString(args, comando);
/*	int i = 0;

	args[i] = strtok(comando, " \t\n");

	while(args[i] != NULL){
		args[++i] = strtok(NULL, " \t\n");
	}
*/
	if(fork() == 0){
		execvp("ls", /* (diretoria)nome do fich exec. */ /* não se deve escrever depois do exec, pois o ls é aqui escrito em cima na memória e desaparece o printf
	*/	args /* argv[1] */);

		perror("/bin/ls");
		_exit(1);

	}

	else wait(NULL);

	return 0;
}

int main(){

	printf("Antes\n");

	char cmd[] = "ls -la";

//	mysystem("ls");
	//mysystem("ls -la"); ----> segmentation fault!(porque a string é uma constante e o strtok tenta partir uma constante sem mover dando segm fault)
	system(cmd);
//	mysystem("ls -la *.c");

	printf("Depois\n");
}