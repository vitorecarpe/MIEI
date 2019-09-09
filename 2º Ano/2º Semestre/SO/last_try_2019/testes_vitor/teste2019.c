// TESTE 28 MAIO 2019

// GRUPO II

#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>
#include <string.h>

int main(int argc, char** argv){

	int notas = open(argv[1], O_RDONLY, 0666);
	int n=0;
	char buf[10];
	char num[6];

	while((n=readline(notas,buf,10))>0){
		if(n>10){
			wait(NULL);
			n--;
		}

		int fd[2];
		pipe(fd);
		char email[23];

		n++;
		if((pid=fork())==0){
			sprintf(email, "%.6s@alunos.uminho.pt", buf);
			write(fd[1], buf, 10);
			close(fd[1]);
			dup2(fd[0],0);
			close(fd[0]);
			execlp("mail", "mail", "-s", "Sistemas_Operativos", email, NULL);
		}
	}

	for(int j=0; j<n; j++){
		wait(NULL);
	}

	return 0;
}


int main(int argc, char** argv){

	int n;
	char buf[128];
	char out;
	pid_t pid;
	int count=0;

	while((n=readline(0, buf, 128))>0){
		if(i>7){
			wait(NULL);
			i--;
		}

		i++;
		if((pid=fork())==0){
			close(fd[0]);
			dup2(fd[1],1);
			close(fd[1]);
			execlp("patgrep","patgrep", argv[1], NULL);
		}
	}

	while((n=read(fd[0], &out, 1))>0){
		if(strcmp(out,'X')==0) count++;
	}

	for(int k=0; k<i; k++){
		wait(NULL);
	}

	return 0;
}



























int main(int argc, char** argv){

	char* numr;
	int n=0;
	char* nota;
	char* email = malloc(sizeof(char) * 23);
	char* body = malloc(sizeof(char) * 10);
	pid_t pid;
	char buf[10];
	
	int file = open(argv[1], O_RDONLY, 0666);
	

	while(read(file, buf, 10)>0){

		if(n>9){
			wait(NULL);
			n--;
		}

		int fd[2];
		pipe(fd);
		buf[6]='\0';
		buf[9]='\0';
		numr = buf;
		nota = buf+7;
		int status=0;

		sprintf(email, "%s@alunos.uminho.pt", numr);
		sprintf(body, "%s %s", numr, nota);

		n++;
		if((pid=fork())==0){
			close(fd[1]);
			dup2(fd[0],0);
			close(fd[0]);
			execlp("mail","mail", "-s", "Sistemas_Operativos", email, NULL);

		} else{
			close(fd[0]);
			write(fd[1], body, 10);
			close(fd[1]);
			wait(&status);

		}

	}
	return 0;
}

// GRUPO III
/*
int main(int argc, char** argv){

	pid_t pid;
	char buf[128];
	char xis;
	int counter;
	int status;
	int fd[2];
	pipe(fd);

	while(read(0,buf,128)>0){
		n++;
		if(n<7){
			if((pid=fork())==0){
				close(fd[0]);
				dup2(fd[1],1);
				close(fd[1]);
				printf("MY FATHER: %d\n", getppid());
				execlp("patgrep","patgrep",argv[1]);
			}
		} else{
			int spid = wait(&status);
			printf("MY SON %d IS DEAD\n", spid);
			n--;
		}
	}
	while((n=read(fd[1], &xis, 1))>0){
		if(strcmp(xis,'X')==0)
			counter++;
	}

	printf("EXISTEM %d NESSE BLOCO.\n", counter);

	return 0;
} */







