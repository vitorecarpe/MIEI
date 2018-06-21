#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>

int mypopen(const char *command, const char *mode) {
	int fd, pd[2], o = pipe(pd);
	pid_t pid;

	if (o == -1) {
		perror("Erro de pipe!");
		exit(-1);
	}

	pid = fork();

	if (pid == -1) {
		perror("fork failed!");
		exit(-1);
	}

	if (strcmp(mode,"r") == 0) {
		if (pid == 0) {
			close(pd[0]);
			dup2(pd[1], 1); close(pd[1]);
			execlp("sh", "sh", "-c", command, NULL);
			perror("execlp failed!");
			exit(-1);
		}
		close(pd[1]);
		return (pd[0]);
	} else {
		if (pid == 0) {
			close(pd[1]);
			dup2(pd[0], 0); close(pd[0]);
			execlp("sh", "sh", "-c", command, NULL);
			perror("execlp failed");
			exit(-1);
		}
		close(pd[0]);
		return pd[1];
	}
}

void mypclose(int fd) {
	int o = close(fd);
	if (o == -1) {
		perror("closing failed!");
		exit(-1);
	}
	int status;
	wait(&status);
}

int main() {
	int fd;
	char temp[] = "Texto qualquer!\nlalala\nlalapooza\n"; // output should be 3
	char tot[] = "mans\n";
	fd = mypopen("wc -l", "w");
	write(fd, temp, sizeof(temp));
	write(fd, tot, sizeof(tot));
	write(fd, tot, sizeof(tot));
	write(fd, tot, sizeof(tot));
	write(fd, tot, sizeof(tot));
	write(fd, tot, sizeof(tot));
	write(fd, tot, sizeof(tot));
	write(fd, tot, sizeof(tot)); //output should be 10
	mypclose(fd);
	exit(0);
}