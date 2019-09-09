#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>
#include <string.h>

//int pipe(pd[2]);

//1
/*
int main(int argc, char** argv){

	int pid;
	char msg[1024];
	char* msgf;
	int fd[2];
	pipe(fd);

	if((pid=fork())==0){
		//PROCESSO FILHO
		printf("ENTROU FILHO\n");
		sleep(5);

		msgf="I'm your son\n";

		close(fd[0]);
		write(fd[1], msgf, strlen(msgf));
		close(fd[1]);

	} else{
		//PROCESSO PAI
		printf("ENTROU PAI\n");
		wait(NULL);
		printf("ACABOU ESPERA!\n");

		close(fd[1]);
		int nbyte = read(fd[0],msg, 1024);
		write(1, msg, nbyte);
		close(fd[0]);
		
	}
	return 0;
}
*/

//2
/*
int main(int argc, char** argv){

	int pid;
	int nbyte;
	char msg[1024];
	char msgf[1024];
	int fd[2];
	pipe(fd);

	if((pid=fork())==0){
		//PROCESSO FILHO
		printf("ENTROU FILHO\n");
		sleep(5);

		close(fd[0]);
		while((nbyte=read(0,msgf,1))>0){
			write(fd[1], msgf, 1);
		close(fd[1]);

		printf("FIM FILHO\n");

	} else{
		//PROCESSO PAI
		printf("ENTROU PAI\n");
		wait(NULL);
		printf("ACABOU ESPERA!\n");

		close(fd[1]);
		while((nbyte=read(fd[0], msg, 1))>0)
			write(1, msg, 1);
		close(fd[0]);

		printf("FIM PAI\n");
		
	}
	return 0;
}
*/

//3
/*
int main(int argc, char** argv){

	int pid;
	int nbyte;
	char msg[1024];
	char msgf[1024];
	int fd[2];
	pipe(fd);

	if((pid=fork())==0){
		//PROCESSO FILHO
		printf("ENTROU FILHO\n");

		close(fd[1]);
		dup2(fd[0],0);
		execlp("wc","wc", NULL);
		close(fd[0]);

		printf("FIM FILHO\n");

	} else{
		//PROCESSO PAI
		printf("ENTROU PAI\n");

		close(fd[0]);
		while((nbyte=read(0,msgf,1))>0)
			write(fd[1], msgf, 1);
		close(fd[1]);

		printf("FIM PAI\n");
		
	}
	return 0;
}
*/

//4

int main(int argc, char** argv){
	int pid;
	int fd[2];
	pipe(fd);

	if((pid=fork())==0){
		//PROCESSO FILHO
		close(fd[0]);
		dup2(fd[1], 1);
		close(fd[1]);
		execlp("ls", "ls", "/etc", NULL);
		
	} else{
		//PROCESSO PAI
		wait(NULL);
		close(fd[1]);
		dup2(fd[0],0);
		close(fd[0]);
		execlp("wc","wc", "-l", NULL);
	}
	return 0;
}

