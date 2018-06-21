#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/wait.h>

int main(){
	printf("Antes\n");

	if(fork() == 0){
		int fd = open("teste.txt", O_CREAT|O_WRONLY|O_TRUNC, 0600);

	//	write(fd, "ola\n", 4);

		dup2(fd, 1);

		close(fd);

		execlp("ls", "ls", NULL);
		perror("ls");
		_exit(1);
	}

	else {

		wait(NULL);
		printf("Depois pai\n");
	}
}