#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

int main(){
	printf("Antes\n");

	int fd = open("teste.txt", O_CREAT|O_WRONLY|O_TRUNC, 0600);

//	write(fd, "ola\n", 4);

	dup2(fd, 1);

	close(fd);

	printf("Depois\n");
}