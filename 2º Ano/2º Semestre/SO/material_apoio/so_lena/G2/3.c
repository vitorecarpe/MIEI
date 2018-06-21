#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
	int i, status;
	for (i = 1; i < 11; i++) {
		int p = fork();
		if (p == -1) {
			perror("erro fork()");
			exit(-1);
		}
		if (p == 0) { // se sou filho
			printf("MY PID = %d, MYPPID = %d\n", getpid(), getppid());
			exit(i); // exit portanto não se multiplicam
		} else { // se sou pai
			wait(&status);
			int s = WEXITSTATUS(status);
			/*
			 * If the value of WIFEXITED(stat_val)
			 * is non-zero, this macro evaluates to
			 * the low-order 8 bits of the status
			 * argument that the child process passed
			 * to _exit() or exit(), or the value the
			 * child process returned from main().
			 */
			printf("SAÍDA: %d\n", s);
			/* WIFEXITED
			 * This macro returns a nonzero value if
			 * the child process terminated normally
			 * with exit or _exit.
			 */
		}
	}
}

/* If you call wait(NULL) (wait(2)), you only wait for any child to
 * terminate. With wait(&status) you wait for a child to terminate
 * but you want to know some information about it's termination.
 */

/* Fork() will fail and no child process will be created if:
 * [EAGAIN] The system-imposed limit on the total number of pro
 * cesses under execution would be exceeded.  This limit
 * is configuration-dependent.
 * [EAGAIN] The system-imposed limit MAXUPRC (<sys/param.h>) on
 * the total number of processes under execution by a single
 * user would be exceeded.

 * [ENOMEM] There is insufficient swap space for the new process.
 */