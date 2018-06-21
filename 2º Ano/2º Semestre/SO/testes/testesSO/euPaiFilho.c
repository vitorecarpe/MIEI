#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>

int main (){
	int i = 0;
	int status;
	while(i < 10){
		int pid = fork();
		if (pid){
			printf("pid: %d, ppid:%d, spid:%d\n",getpid(),getppid(),pid);
			waitpid(pid,&status,0);
			exit(0);
		}
		else i++;
	}
	return 0;
}