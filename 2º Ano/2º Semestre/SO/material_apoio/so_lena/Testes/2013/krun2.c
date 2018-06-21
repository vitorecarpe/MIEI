#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>
#include <errno.h>

int act = 0;
int max_act = 5;
pid_t *execs;

void usr1_handler(int sig) {

	max_act++;
	execs = (pid_t*)realloc(execs, sizeof(pid_t) * max_act);
}

void usr2_handler(int sig) {
	max_act--;
}

void d_handler(int sig) {

	pid_t dead = wait(NULL);
	act--;
	int i;
	for (i = 0; i < max_act; i++)
		if (execs[i] == dead)
			execs[i] = 0;
}

int main(int argc, char **argv) {
 
	execs = malloc(sizeof(pid_t) * 5);
	char *args[argc];
	int i;

	for (i = 0; i < argc - 1; i++)
		args[i] = argv[i + 1];

	args[i] = NULL;

	signal(SIGUSR1, usr1_handler);
	signal(SIGUSR2, usr2_handler);
	signal(SIGCHLD, d_handler);

	while (1) {
		while (act < max_act) {
			pid_t exec = fork();
			if (exec == -1) {
				perror("Erro no fork().\n"); 
				exit(-1);
			}
			else if (exec == 0)
				execvp(argv[1], args);
			else {
				int found = 0;
				for (i = 0; i < max_act && !found; i++) {
					if (execs[i] == 0) {
						execs[i] = exec;
						found = 1;
					}
				}
				act++;
			}
		}
		if (act == max_act)
			pause();
		else if (act > max_act) {
			int ex = act;
			while (ex > max_act) {
				kill(execs[ex--], SIGKILL);
				pause();
			}
			execs = (pid_t*)realloc(execs, sizeof(pid_t) * max_act);
		}
	}
	
}
  