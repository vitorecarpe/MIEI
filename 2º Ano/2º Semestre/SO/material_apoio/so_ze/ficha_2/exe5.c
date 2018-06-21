#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(){

	int N = 10;
	int i, mypid, parentpid;

	for(i = 0; i < (N-1); i++){


		if(fork() != 0){

			break;
		}
	}


	wait(NULL);

	mypid = getpid();

	parentpid = getppid();

	sleep(2);

	printf("O meu processo é %d, o do meu pai é %d\n", mypid, parentpid);

	_exit(0);
}