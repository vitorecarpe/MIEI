#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdarg.h>

/* Este programa simula o | da sheel.
 * É capaz de encadear vários comandos.
 */

int encadear(char *cmd[])
{
	int i;
	
	pid_t pid;
	int c = 0;
	while(cmd[c])//Conta o número de comandos
		c++;

	int stdin_orig = dup(0);

	for (i = 0; i != c; i++) {
		int pd[2];
		if (i != c - 1) {
			if (pipe(pd)!=0) {
				perror("pipe");
				exit(EXIT_FAILURE);
			}
		}
		switch(pid = fork()) {
			case 0:
				if (i != c -1) {
					close(pd[0]);
					dup2(pd[1], 1);
					close(pd[1]);
				}
				close(stdin_orig);
				execlp("sh", "sh", "-c", cmd[i], NULL);
				perror("execlp");
				exit(EXIT_FAILURE);
			case -1:
				perror("execlp");
				return -1;
			default:
				if (i != c - 1) {
					close(pd[1]);
					dup2(pd[0], 0);
				}
				if (i == c - 1) {
					dup2(stdin_orig, 0);
					close(stdin_orig);
				}
		}
	}
	if (c)
		waitpid(pid, NULL, 0);
	return 0;
}

int main()
{
	char *comando[] = {"ls /etc","grep f","wc -l",NULL};
	//printf("%s\n",comando[1]);
	encadear(comando);
	return 0;
}
