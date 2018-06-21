#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char* argv[]){
	
	argv[0] = "*.c";
	execv("./exe3",argv);
	perror("ls");
	return 0;
}
