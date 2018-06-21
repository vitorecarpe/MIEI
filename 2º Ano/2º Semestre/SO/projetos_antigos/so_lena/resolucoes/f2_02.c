#include <unistd.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>

int main(){
	int p = fork();
	int status;
	if (p == -1)
		{perror("asneira no fork");_exit(-1);}
	else if (!p){
		printf("pid = %d,ppid = %d\n",getpid(),getppid());
	}
	else{
		wait(&status);
		printf("pid = %d,ppid = %d,pid son = %d\n",getpid(),getppid(),p);
	}
}

/*
int main(){
	int p;
	p = fork();
	if (p == -1)
		{perror("asneira no fork");_exit(-1);}
	if (p != 0)	
		printf("pid do filho = %d\n", p);
	else
		sleep(4);//dormir 2 segundos
	printf("pid = %d,ppid = %d\n",getpid(),getppid());
	//getchar();
}		*/