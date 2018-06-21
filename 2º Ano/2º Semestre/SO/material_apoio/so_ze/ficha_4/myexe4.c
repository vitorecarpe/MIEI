#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/wait.h>
#include <string.h>

int main(int argc, char *argv[]){

	int i = 1, s;

	if (strcmp(argv[i],"-i") == 0){
		int fd1 = open (argv[i+1], O_RDONLY, 0600);
		dup2(fd1, 0);
		close(fd1);
		i = i+2;
	}

	if (strcmp(argv[i],"-o") == 0){
		int fd2 = open (argv [i+1], O_CREAT | O_WRONLY, 0600);
		dup2(fd2,1);
		close(fd2);
		i = i+2;
	}

	if (fork() == 0){

		execvp(argv[i], argv+i);
		perror(argv[i]);
		_exit(1);
	}

	else {
		wait(&s);

		if(WIFEXITED(s) && (WEXITSTATUS(s) == 1))
			printf("%d\n", WEXITSTATUS(s));
	}
	return 0;
}