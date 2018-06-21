#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <stdio.h>
#include <stdlib.h>
/*
int execl(const char *path, const char *arg0, ..., NULL);
int execlp(const char *file, const char *arg0, ..., NULL);
int execv(const char *path, char *const argv[]);
int execvp(const char *file, char *const argv[]);
*/

int main (int argc,char* argv[]){
	int n;
	for(n = 0; n < argc; n++)
		printf("argv[%d] = %s\n",n,argv[n]);
	getchar();
	exit(0);
}