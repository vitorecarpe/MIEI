#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/wait.h>

int main(){
	printf("Antes\n");

	if(fork() == 0){
		int fd = open("teste.txt", O_RDONLY, 0600);

	//	write(fd, "ola\n", 4);

		dup2(fd, 0);

		close(fd);

		int fd2 = open("res.txt", O_CREAT|O_WRONLY|O_TRUNC, 0600);

	//	write(fd, "ola\n", 4);

		dup2(fd2, 1);

		close(fd2);


		execlp("wcx", "wc", NULL);
		perror("wcx");// -> vai para o descritor 2 que é o stderr
		//printf("ERRO\n"); -> vai para o descritor 1(out) que neste caso é o fd
		_exit(1);
	}

	else {

		wait(NULL);
		printf("Depois pai\n");
	}
}