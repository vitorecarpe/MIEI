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

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;
	while (i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Erro de leitura!\n");
		exit(-1);
	}
	buffer[i] = '\n';
	return i;
}



void terminate() {
	printf("%d esteve ativo durante %d segundos!\n", pidch, ok * 3);
	kill(pidch, SIGKILL);
	kill(getpid(), SIGKILL);
}

void main(int argc, char** argv) {
	int i;
	int n = argc - 1;
	int pid, pidc, status;

	for (i = 1; i < argc; i++) {
		pid = fork();

		if (pid == 0) {
			int pd[2];
			pipe(pd);

			signal(SIGALRM, conta);
			signal(SIGCHLD, terminate);
			pidc = fork();

			if (pidc == 0) {
				close(pd[0]);
				dup2(pd[1], 1); close(pd[1]);
				execlp(argv[i], argv[i], NULL);
				exit(-1);
			} else {
				close(pd[1]);
				pidch = pidc;
				ok = 0;
				int r;
				char buffer[PIPE_BUF];
				while((r = readln(pd[0], buffer, PIPE_BUF)) > 0) {
					ok++;
					buffer[r - 1] = '\0';
					if (strcmp(buffer, "OK!") != 0)	{
						ok--;
						break;
					}
				}
				kill(pidc, SIGSTOP); // TRIGGER SIGCHLD
				close(pd[0]);
			}
		}
	}

	for (i = 1; i < argc; i++) {
		wait(&status);
	}

	exit(0);
}