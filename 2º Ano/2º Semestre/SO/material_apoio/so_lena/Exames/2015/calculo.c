#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>


ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while(i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	buffer[i] = '\0';
	return i;
}


void main(int argc, char** argv) {

	int r; char buffer[PIPE_BUF];
	int mp[2];
	pipe(mp);
	int res[2];
	pipe(res);
	pid_t p = fork();


	if (p == 0) {
		close(mp[1]); close(res[0]);
		dup2(mp[0], 0); close(mp[0]);
		dup2(res[1], 1); close(res[1]);
		execlp("./calculo", "./calculo", NULL);
		exit(-1);
	} else {
		close(mp[0]);
		close(res[1]);
	}

	char res[PIPE_BUF];
	if((r = readln(0, buffer, PIPE_BUF)) > 0) {
		write(mp[1], buffer, r);
		if((r = readln(0, res, PIPE_BUF)) > 0) {
			int num = atoi(res);
		}
		while((r = readln(res[0], buffer, PIPE_BUF)) > 0) {
			if (atoi(buffer) != res) {
				kill(p, SIGKILL);
				exit(-1);
			}
		}
	}
}