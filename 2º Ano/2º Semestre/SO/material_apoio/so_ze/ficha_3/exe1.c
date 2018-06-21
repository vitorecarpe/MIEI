#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main(){
	
	execlp("ls", "ls", "-l", NULL);
	perror("ls");
	return 0;
}
