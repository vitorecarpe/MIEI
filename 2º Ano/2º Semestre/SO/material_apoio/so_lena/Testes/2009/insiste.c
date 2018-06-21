#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>

void main(int argc, char** argv) {

	pid_t first = fork();

	if (first == 0) {
		execlp(argv[1], argv[1], NULL);
		exit(-1);
	}

	while(1) {
		pid_t dead = wait(NULL);
		first = fork();

		if (first == 0) {
			execlp(argv[1], argv[1], NULL);
			exit(-1);
		}
	}
}