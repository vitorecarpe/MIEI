#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char* argv[]){
		pid_t pid;
		for(int i=1;i < argc;i++)
		{

				pid = fork();
				switch(pid)
				{

						case -1: perror("Erro de fork\n");
								 exit(-1);
								 break;
						case 0:  execlp(argv[i],argv[i],NULL);
								 exit(1);
						default: wait(NULL);
								 break;
				}
		}
}
