#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <stdio.h>
#include <stdlib.h>
/* este n é concorrente
int main (int argc, char *argv[]){
	int i;int p,status;
	char *a [argc];
	for(i = 1; i < argc; i++){
		p = fork();
		if (p == 0){
			a[0] = argv[i];
			a[1] = NULL;
			execvp(argv[i],a);
			//execlp(argv[i],argv[i],NULL);
			exit(-1);
		}
		wait(&status);
	}
	exit(0);
}
*/
int main(int argc, char** argv){
	int status;
	int i;
	for (i = 1; i < argc; i++){
		if(!fork()){
			execlp(argv[i],argv[i],NULL);
			_exit(-1);
		}
	}
	for (i = 1; i < argc; i++) wait(&status);
	_exit(0);
}