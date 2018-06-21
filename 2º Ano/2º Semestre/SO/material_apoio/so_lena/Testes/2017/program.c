#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
 
pid_t *filtro;
pid_t *existe;
int n;
 
ssize_t readln(int fildes, char* buffer, size_t nbyte) {
	int i = 0, r;
	while (i < nbyte && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Falha na leitura!\n");
		exit(-1);
	}
	buffer[i] = '\n';
	return i;
}
 
void handler(int sig) {	int status, i;
	wait(&status);
	
	if (WEXITSTATUS(status) == 1) {
		for (i = 0; i < n; i++) {
			kill(filtro[i], SIGKILL);
			kill(existe[i], SIGKILL);
		}
		printf("O padrão existe!\n");
		exit(0);
	}
}
 
int main(int argc, char **argv) {
	n = atoi(argv[1]);
	filtro = malloc(sizeof(pid_t) * n);
	existe = malloc(sizeof(pid_t) * n);
	int i;
	int fd_w[n];
	size_t r;
	char buffer[PIPE_BUF];
 
	pid_t pid;
	int mp[2];
	int mp2[2];
 
	signal(SIGCHLD, handler);
   
	for (i = 0; i < n; i++) {
		pipe(mp);
		pipe(mp2);
		fd_w[i] = mp[1];
 
		pid = fork();
		if (pid == 0) {
			close(fd_w[i]); close(mp2[0]);
			dup2(mp[0], 0); close(mp[0]);
			dup2(mp2[1], 1); close(mp2[1]);
			execlp("./filtro", "./filtro", NULL);
			_exit(-1);
		}
		else {
			filtro[i] = pid;
			pid = fork();
			if (pid == 0) {
				close(mp2[1]);
				dup2(mp2[0], 0); close(mp2[0]);
				execlp("./existe", "./existe", NULL);
				_exit(-1);
			}
			else {
				close(mp2[0]);
				close(mp[0]); close(mp2[1]);
				existe[i] = pid;
			}
		}
	}
 
	i = 0;
	while ((r = readln(0, buffer, PIPE_BUF)) > 0) {
		write(fd_w[i], buffer, r);
		if (i == n-1) i = 0;
		else i++;
	}
	
	for (i = 0; i < n; i++) {
		close(fd_w[i]);
		kill(filtro[i], SIGKILL);
		kill(existe[i], SIGKILL);
	}
 	
	exit(0);
}