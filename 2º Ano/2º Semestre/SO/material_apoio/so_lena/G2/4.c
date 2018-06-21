#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
	int status, i, s, p;
	for (i = 0; i < 11; i++) {
		p = fork();
		if (p == -1) {
			perror("Erro a criar processo filho!\n");
			exit(1);
		}
		if (p == 0) {
			printf("PID: %d | PPID: %d\n", getpid(), getppid());
			_exit(i); // terminar a execução com um valor de saída igual ao
		}             // seu número de ordem
	}
	for (i = 0; i < 11; i++) {
		wait(&status);
		s = WEXITSTATUS(status);
		printf("Exit status do filho = %d\n", s);
	}
}