#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
	int p = fork();
	if (p == -1) {
		perror("Erro a criar processo filho!\n");
		exit(1);
	}
	if (p != 0) { // se for pai
		printf("PID: %d | PPID: %d | CPID = %d\n", getpid(), getppid(), p);
		wait(NULL);
	} else { // se for filho
		printf("PID: %d | PPID: %d\n", getpid(), getppid());
	}
	exit(0);
}

/* - If fork() returns a negative value, the creation of a child process
 * was unsuccessful.
 * - fork() returns a zero to the newly created child process.
 * - fork() returns a positive value, the process ID of the child process,
 * to the parent. The returned process ID is of type pid_t defined in
 * sys/types.h. Normally, the process ID is an integer. Moreover,
 * a process can use function getpid() to retrieve the process ID assigned
 * to this process.
 */

/* === OUTPUT ===
PID: 5153 | PPID: 9235 | CPID = 5154
PID: 5154 | PPID: 5153
*/