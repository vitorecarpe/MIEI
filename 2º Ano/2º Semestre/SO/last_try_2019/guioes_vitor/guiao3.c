#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>

/*
int execl(const char *path, const char *arg0, ..., NULL);
int execlp(const char *file, const char *arg0, ..., NULL);
int execv(const char *path, char *const argv[]);
int execvp(const char *file, char *const argv[]);
*/

//1
/*
int main(int argc, char** argv){
	execlp("ls", "ls", "-l", NULL);
	return 0;
}
*/

//2
/*
int main(int argc, char** argv){
	pid_t pid=0;
	int status=0;
	if((pid=fork())==0){
		printf("FILHO: %d - %d\n", getpid(), getppid());
		execlp("ls", "ls", "-l", NULL);
	} else {
		wait(&status);
		printf("\n PAI: %d\n", getpid(), getppid());
	}
	return 0;
}
*/

//3
/*
int main(int argc, char** argv){
	for(int i=1; i<argc; i++){
		printf("%s\n", argv[i]);
	}
	return 0;
}
*/

//4
/*
int main(int argc, char** argv){
	execv(argv[1], argv+1);
	return 0;
}
*/

//5
/*
int main(int argc, char** argv){
	pid_t pid;
	int i, status;
	for(i=1; i<argc; i++){
		if((pid=fork())==0){
			printf("#%d\n", i);
			printf("PROCESSO FILHO\n");
			printf("PROCESSO: %s\n", argv[i]);
			printf("FILHO: %d\n", getpid());
			printf("PAI: %d\n\n", getppid());
			execlp(argv[i], argv[i], NULL);
		}
	}
	for(i=1;i<argc; i++){
		int i = wait(&status);
		printf("PROCESSO PAI\n");
		printf("FILHO: %d\n", i);
		printf("PAI: %d\n", getpid());
	}
	return 0;
}
*/








