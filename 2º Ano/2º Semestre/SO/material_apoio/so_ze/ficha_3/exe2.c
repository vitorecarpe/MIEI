#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main(){
	
	if(fork() == 0) {
		execlp("ls", "ls", "-l", NULL);
		perror("ls");
		_exit(1);
		}
	
	else wait(NULL);
	
	printf("PAI\n");
}
