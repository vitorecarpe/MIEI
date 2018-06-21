//SO - AULA 5 - GUIÃO 3

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

/*
Fork cria cópia, é exatamente o mesmo (incluindo apontadores),
exceto a etiqueta das coisas (PID=10 ou PID=20) e o resultado
da execução da funçao fork, no processo filho retorna 0.

Executar uma função, por exemplo: exec("/bin/ls") vai escrever
o programa "ls" sobre o código do processo, e vai apagar todo
o processo, assim, se tivermos...
exec("/bin/ls");
printf("olá mundo");
... não irá printar nada, porque ao executar ls no processo
apagou todo o processo.

execlp(const char *file, const char *arg0, ..., NULL);
execlp("/bin/ls", "ls", "-l", NULL) <- Temos de escrever NULL,
sem NULL nem sequer executa. O NULL diz ao compilador que é o
último elemento.


*/


/*EXERCICIO 1
int main(){
	printf("ANTES\n");
	execl("/bin/ls","ls","-l",NULL);
	printf("DEPOIS");
} 
*/

/*EXERCICIO 2
int main(){
	printf("ANTES\n");
	int x=fork();
	if(x==0){
		execl("/bin/ls","ls","-l",NULL);
		exit(0);
	}
	wait(0L);
	printf("DEPOIS\n");

}
*/

//EXERCICIO 3
/*int main(int argc, char* argv[]){
	int i;
	for(i=0;i!=argc;i++) printf("argv[%d] = \"%s\"\n", i, argv[i]);
}*/


//EXERCICIO 4
/*int main(){
	execlp("./ex3", "raul", "é", "gay", NULL);
	perror("./ex3");
	return 1;
}*/

//EXERCICIO 5 - Incompleto...
/*int main(int argc, char* argv[]){
	int i;
	for(i=1; i!=argc; i++){
		if(fork()==0){
			execlp... continua...
		}
	}
}*/

#define MAX 100

//EXERCICIO 6
int my_system(const char* comando){
	char* token =NULL;
	char* argv[MAX];
	int i, status;
	char* copia = strdup(comando);
	pid_t pid;
	for(i=0; (i!=MAX-1)&&(token=strsep(&copia," "))!=NULL; i++)
		argv[i]=strdup(token);        // strdup = malloc + strcpy 
	argv[i]=NULL;
	if((pid = fork())==0){
		execvp(argv[0], argv);
		_exit(1);
 	}
 	free(copia);
	waitpid(pid, &status, 0);
	return WIFEXITED(status)? WEXITSTATUS(status) : -1;
}
int main(){
	my_system("ls -l aula1.c");
	return 0;
}
















