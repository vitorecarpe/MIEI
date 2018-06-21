#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>

int nrP;
int* pid;
char* cmd;
char** args;

pid_t criaProcesso() {
	int p = fork();

	if (p == 0) {
		execvp(cmd, args);
		exit(-1);
	}
	else {
		return p;
	}
}

void aumenta() {
	nrP++;
	pid = realloc(pid, sizeof(int) * nrP);
	pid[nrP] = criaProcesso();

}

void diminui() {
	kill(pid[--nrP], SIGKILL);
	pid = realloc(pid, sizeof(int) * nrP);
}

void substitui() {
	int status, i;

	pid_t pid_ant = wait(&status);
	for (i = 0; i < nrP; i++) {
	 	if (pid[i] == pid_ant) pid[i] = criaProcesso();
	}
}

void main(int argc, char** argv) {
	pid_t f = fork();

	if (f == 0) {
		nrP = 5;
		cmd = argv[1];
		args = argv + 1;
		pid = malloc(sizeof(int) * nrP);

		int status, i;
		signal(SIGUSR1, aumenta);
		signal(SIGUSR2, diminui);
		signal(SIGCHLD, substitui);

		for (i = 0; i < nrP; i++) {
			pid[i] = criaProcesso();
		}

		while(1) {
		}
	}
	else {
		kill(getpid(), SIGKILL);
		_exit(0);
	}
}