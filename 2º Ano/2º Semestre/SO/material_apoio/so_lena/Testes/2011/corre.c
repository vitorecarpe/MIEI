#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>

int nSecs;
int pidch;

void killer() {
	kill(pidch, SIGKILL);
	kill(getpid(), SIGTERM);
}

pid_t criaProcesso(char* cmd) {
	int p = fork();

	if (p == 0) {
		signal(SIGALRM, killer);
		signal(SIGCHLD, killer);
		int pid = fork();

		if (pid == 0) {
			execlp(cmd, cmd, NULL);
			exit(-1);
		}
		else {
			pidch = pid;
			alarm(nSecs);
			pause();
		}
	} else {
		return p;
	}
}

void main(int argc, char** argv) {
	int status;
	int nP = atoi(argv[1]);
	nSecs = atoi(argv[2]);
	char** programs = argv + 3;
	int pid[nP];
	int i;

	if (argc - 3 < nP) nP = argc - 3; // caso - mais processo permitidos

	for (i = 0; i < nP; i++) {
		pid[i] = criaProcesso(programs[i]);
	}

	pid_t dead;

	while ((dead = wait(NULL)) > 0) {
		if (programs[i] != NULL) {
			for (int o = 0; o < nP; o++) {
				if (dead == pid[o]) {
					pid[o] = criaProcesso(programs[i++]);
				}
			}
		}
	}
	
	exit(0);
}