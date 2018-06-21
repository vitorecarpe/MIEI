#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>


void main() {
	int status;

	int p = fork();

	if (p == 0) {
		while(1) {
			pause();
		}
	}

	kill(p, SIGKILL);

	pid_t dead = wait(&status);

	printf("%d\n", dead);

	exit(0);
}