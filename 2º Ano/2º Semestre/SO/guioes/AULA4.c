//SO - AULA 4 - GUIÃO 2

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

/*
for(i=0;i<N;i++){
	if((x=fork())==0){
		funcao_filho(...);
		exit(0);
	}
}
*/


/*EXERCICIO 3
int main(){
	int i,x;
	for(i=0;i<10;i++){
		x=fork();
		if(x==0){ 
			printf("pid %d, ppid %d\n",getpid(),getppid());
			_exit(0);
		}
		wait(&status);
		s=WEXITSTATUS(status);
		printf("exit status do filho pid = %d\n",s);
	}
}

*/

/*EXERCICIO 4

int main(){
	for(i=0;i<10;i++){
		if((x==fork())==0){
			wait(0L);
		}
	}
	int status=WEXITSTATUS(status);
    printf("The child exited with return code %d\n", status);
    exit(0);
}
*/


/*EXERCICIO 5

for(i=0;i<10;i++){
	if((x==fork())==0){
		printf("PID PAI: %d",getppid());
		printf("PID: %d",getpid());
	}
	else{
		wait(0L);
		exit(0);
	}
}
*/

/*EXERCICIO 6

*/
