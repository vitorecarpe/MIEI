#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>

int main() {
	printf("MY PID: %d ; MY PARENT'S PID: %d\n", getpid(), getppid());
	sleep(10); // para ser visível
	exit(0);
}

// ./1 &
// ps
// Para correr o programa no background e poder ver
// na bash o programa na lista de processos.
// A bash cria um fork.

// pid_t getpid(void);
/* get process identification */
// pid_t getppid(void);
/* returns the process ID of the parent of the calling process */
// pid_t fork(void);
/* creates a new process by duplicating the calling process.
 * the new process is referred to as the child process. the calling process
 * is referred to as the parent process.
 */
// void _exit(int status);
/* terminate the calling process */
// pid_t wait(int *status);
/* wait for process to change state */
// pid_t waitpid(pid_t pid, int *status, int options);
/* The wait() system call suspends execution of the calling process
 * until one of  its  children terminates.  The call wait(&status)
 * is equivalent to:
 * waitpid(-1, &status, 0);
 * The  waitpid() system call suspends execution of the calling process
 * until a child specified by pid argument has changed state.
 * By default, waitpid() waits only for terminated children, but this
 * behavior is modifiable via the options argument, as described below.
 */

// int WIFEXITED(int status); // MACRO
/* This macro returns a nonzero value if the child process terminated
 * normally with exit or _exit.
 */
// int WEXITSTATUS(int status); // MACRO
/* If WIFEXITED is true of status, this macro returns the low-order
 * 8 bits of the exit status value from the child process.
 */

// wait(NULL) -- waits for child process to join with this parent