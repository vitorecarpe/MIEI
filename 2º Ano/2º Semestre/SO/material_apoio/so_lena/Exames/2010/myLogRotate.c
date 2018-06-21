#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <string.h>

char* fichN;
int pid;
int fd;

pid_t createWrite(char* cmd) {
	fd = open(fichN, O_CREAT | O_WRONLY | O_TRUNC, 0600);
	
	pid_t p = fork();

	if (p == 0) {
		dup2(fd, 1); close(fd);
		execlp(cmd, cmd, NULL);
		exit(-1);
	}
	else return p;

}

void logrotate() {
	kill(pid, SIGSTOP);
	sleep(3);
	pid_t p = fork();

	if (p == 0) {
		execlp("logrotate", "logrotate", fichN, NULL);
		exit(-1);
	}
	else wait(NULL);

	kill(pid, SIGCONT);
}

void main(int argc, char** argv) {
	char* cmd = argv[1];
	fichN = malloc(sizeof(char) * (strlen(cmd)));
	signal(SIGALRM, logrotate);
	sprintf(fichN, "%s", cmd);
	printf("/var/log/%s\n", fichN);
	

	alarm(600); // a cada 10 minutos

	pid = createWrite(cmd);
}