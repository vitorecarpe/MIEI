#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(){
	int p;
	int i;
	int status,s;
	for(i = 1; i<=10; i++){
		p = fork();
		if (p == 0){
			printf("pid = %d,ppid = %d\n",getpid(),getppid());
			_exit(i);
		}
	}
	for(i = 1; i<=10; i++){
		wait(&status);
		s = WEXITSTATUS(status);
		printf("%d\n",s);
	}
}
