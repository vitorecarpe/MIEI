/**
 * Programa que executa o comando ls -l usando um processo filho.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main()
{
	pid_t pid;	// same as int

	pid = fork();

	if (pid < 0) {
		perror("fork failed");
		_exit(-1);
	}

	if (pid == 0) {
		// Child
		/* using exec with the p (execlp) because it's a command in the system path */
		execlp("ls","ls","-l",NULL);

		_exit(-1);		// Only executes this in case there's problems trying to do the execl
	} else {
		// Parent
		wait(NULL);
	}
	return 0;
}
