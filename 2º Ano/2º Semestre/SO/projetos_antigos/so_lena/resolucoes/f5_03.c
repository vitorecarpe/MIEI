#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>

#define B_SIZE 100

int main (int argc, char* argv []){
	int pd [2];
	int r = 15,status;
	char buf [B_SIZE];
	pipe(pd);
	int pid = fork(); 
	if (pid != 0){
		close(pd[0]); // n é necessário
		while((r = read(0,buf,B_SIZE)) != 0)
			write(pd[1],buf,r);
		close(pd[1]);
	}else{
		dup2(pd[0],0);
		close(pd[0]);
		close(pd[1]);
		execlp("wc","wc",NULL);
		/*while((r = read(pd[0],buf,B_SIZE)) != 0)
			write(1,buf,r);*/
		_exit(-1);
	}
	wait(&status);
	_exit(0);
}