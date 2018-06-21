#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(){
	printf("pid = %d,ppid = %d\n",getpid(),getppid());
	getchar();
}