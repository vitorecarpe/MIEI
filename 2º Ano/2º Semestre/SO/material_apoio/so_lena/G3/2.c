#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>

int main() {
	int p = fork();
	if (p == -1) {
		perror("Bad arguments!");
		exit(-1);
	}
	if (p == 0) {
		execlp("ls", "ls", "-l", NULL);
	}
	else wait(NULL);
	exit(0);
}