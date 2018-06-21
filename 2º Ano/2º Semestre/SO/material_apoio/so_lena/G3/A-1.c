#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>

int executeW(char* name) {
	int s = 1, status, t = 0;
	while (s) {
		t++;
		int p = fork();
		if (p == 0) {
			execl(name, name, NULL);
			exit(-1);
		} else {
			wait(&status);
			s = WEXITSTATUS(status);
			if (s == 0) break;
		}
	}
	return t;
}

int main(int argc, char** argv) {
	int i;
	for (i = 1; i < argc; i++) {
		int t = executeW(argv[i]);
		printf("%s %d\n", argv[i], t);
		sleep(1);
	}
	exit(0);
}