#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/wait.h>

int main(){
	int fd1 = open("saida.txt", O_CREAT|O_WRONLY|O_TRUNC, 0600);
	int fd2 = open("bla.txt", O_RDONLY, 0600);

	dup2(fd1, 1);

	close(fd1);

	dup2(fd2, 0);

	close(fd2);

	if(fork() == 0){

		execlp("wc", "wc", NULL);
		perror("wc");
		_exit(1);
	}

	else{

		wait(NULL);
	}


}