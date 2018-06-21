#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>

int main (int argc, char* argv []){
	pid_t pids [3];int i; pid_t pid;
	for ( i = 0; i <=2; i++){
		if (!(pid = fork())) {
			pids[i] = pid;
			printf("%ld %ld", (long)getpid(), (long) pids[i]);
		}
	}
}

	//criar os processos e pará-los e dar 1 segundo 
	//um de cada vez
