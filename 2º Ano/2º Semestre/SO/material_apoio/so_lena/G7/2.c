#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <signal.h>
#include <sys/wait.h>
#include <string.h>

int* pid;
int i;

void signalHandler(int sig) {
	pid[0]--;
}

int main(int argc, char** argv) {
	pid = malloc(sizeof(int) * argc);
	pid[0] = 0; // na posição 0 estará o numero de processos ativos.
	int p, status;

	struct sigaction act;

	act.sa_handler = signalHandler;
	act.sa_flags = SA_NOCLDSTOP;

	if (sigaction(SIGCHLD, &act, 0) == -1) {
		perror("sigaction");
		exit(1);
	}

	for (i = 1; i < argc; i++) {
		pid[0]++;
		p = fork();
		if (p == 0) {
			pid[i] = getpid();
			kill(pid[i], SIGSTOP);
			execlp(argv[i], argv[i], NULL);
			exit(-1);
		}
		else {
			pid[i] = p;
			waitpid(-1, &status, WUNTRACED);
		}
	}

	while (pid[0] != 0) {
		for (i = 1; i < argc; i++) {
			kill(pid[i], SIGCONT);
			sleep(1);
			kill(pid[i], SIGSTOP);
		}
	}
}