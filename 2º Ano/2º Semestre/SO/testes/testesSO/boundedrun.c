#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>

#define BUF_SIZE 100
int pid;

void timeLimit (){
	kill(pid,SIGKILL);
	_exit(0);
}

int main(int argc, char* argv[])
{
	signal(SIGALRM,timeLimit);
	int time = atoi(argv[1]);
	int size = atoi(argv[2]), nr;
	char buf [BUF_SIZE];
	int fd[2];
	pipe(fd);
	pid = fork();
	if (!pid){// se sou o filho
		dup2(fd[1],1);close(fd[1]);
		close(fd[0]);
		execvp(argv[3],argv + 3);
		_exit(-1);
	}else{//se sou o pai
		close(fd[1]);
		ualarm(time,0);
		while(size > 0 &&((nr = read(fd[0],buf,BUF_SIZE)) > 0)){
			if (size <= nr) nr = size;
			write(1,buf,nr);
			size -= nr;
		}
		kill(pid,SIGKILL);
	}
	return 0;
}