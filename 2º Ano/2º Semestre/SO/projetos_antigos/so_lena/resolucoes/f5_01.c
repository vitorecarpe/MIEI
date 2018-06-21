#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>

#define B_SIZE 20

int main (int argc, char* argv []){
	int pd [2];
	int r = 15,status;
	char buf [B_SIZE];
	pipe(pd);
	int pid = fork();
	if (pid != 0){
		sleep(5);
		r = read(0,buf,12);
		//printf("%s\n", buf);
		write(pd[1],buf,r);
	}else{
		r = read(pd[0],buf,20);
		write(1,buf,r);
		_exit(0);
	}
	wait(&status);
	_exit(0);
}