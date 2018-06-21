#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <signal.h>
#include <sys/wait.h>
#include <string.h>

int ok;
int pidch;

void tempo() {
	alarm(3);
	printf("OK!\n");
	ok++;
}
void terminate() {
	printf("%d esteve ativo durante %d segundos!\n", getpid(), ok * 3);
	kill(getpid(), SIGKILL);
	kill(pidch, SIGKILL);
}

void main(int argc, char** argv) {
	int i;
	int n = argc - 1;
	int pid, pidc, status;

	for (i = 1; i < argc; i++) {
		pid = fork();

		if (pid == 0) {
			signal(SIGALRM, tempo);
			signal(SIGCHLD, terminate);
			pidc = fork();

			if (pidc == 0) {
				if (i == 1)
					execlp(argv[i], argv[i], "9", NULL);
				else
					execlp(argv[i], argv[i], "5", NULL);
				exit(-1);
			} else {
				pidch = pidc;
				ok = 0;
				alarm(3);
				while(1) {
					pause();
				}
			}
		}
	}

	for (i = 1; i < argc; i++) {
		wait(&status);
	}

	exit(0);
}