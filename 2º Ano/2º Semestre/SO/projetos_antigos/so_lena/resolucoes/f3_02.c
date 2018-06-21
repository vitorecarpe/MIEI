#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*
int execl(const char *path, const char *arg0, ..., NULL);
int execlp(const char *file, const char *arg0, ..., NULL);
int execv(const char *path, char *const argv[]);
int execvp(const char *file, char *const argv[]);
*/

int main (){
	int status;
	if(!fork()){
		execlp("ls","ls","-l",NULL);
		exit(-1);
	}
	wait(&status);
	if (WIFEXITED(status)) printf("Exit Status %d\n",WEXITSTATUS(status) );
	exit(0);
}