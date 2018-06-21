#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(){

	int mypid, parentpid;

	int n = fork();

	if(n == 0){


		mypid = getpid();

		sleep(5);

		parentpid = getppid();

		printf("O meu processo é %d, o do meu pai é %d\n", mypid, parentpid);

		_exit(0);
	}
	
	else{

	wait(NULL);

	mypid = getpid();

	parentpid = getppid();

	printf("O meu processo é %d, o do meu pai é %d e o do meu filho é %d\n", mypid, parentpid, n);
	}

	return 0;
}