#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>

int main() {
	int i;
	for (i = 1; i < 11; i++) {
		int p = fork();
		if (p == -1) {
			perror("erro fork()");
			exit(-1);
		}
		printf("MY PID: %d | MY PPID: %d\n", getpid(), getppid());
		if (p != 0) wait(NULL); // esperar pelo filho
	}
	exit(0);
}

/* Se p == 0 então trata-se de um processo filho.
 * Contar o número de processos:
 * ./a.out | wc -l
 * Conta o número de processos que são filhos:
 * ./a.out | grep \\0 | wc-l
*/
