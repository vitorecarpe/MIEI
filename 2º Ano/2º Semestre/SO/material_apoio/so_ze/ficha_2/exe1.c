#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(){

	int mypid, parentpid;

	mypid = getpid();

	parentpid = getppid();

	printf("O meu processo é %d, o do meu pai é %d\n", mypid, parentpid);

	return 0;
}