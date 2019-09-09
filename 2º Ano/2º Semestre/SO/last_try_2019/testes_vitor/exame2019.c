// EXAME 18 JUNHO 2019

// GRUPO II

#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>
#include <string.h>

pid_t pid2;

void alarm_handler(int signum){
	kill(pid2, SIGKILL);
}

int main(int argc, char** argv){
	int i = 0, n = 0, j = 0, in;
	int out = open("out.txt", O_CREAT | O_WRONLY, 0666);
	char buf[128];
	char lin[100];
	pid_t pid;

	while((i = readln(0,buf,128))>0){
		if(n<9){
			n++;
			if((pid=fork())==0){
				if((pid2=fork())==0){
					dup2(out,1);
					pid2 = getpid();
					execlp("agrep", "agrep", argv[1], buf, NULL);
				} else{
					signal(SIGALRM, alarm_handler);	
					alarm(60);
					wait(NULL);
				}
			}
		} else{
			wait(NULL);
			n--;
		}
	}

	for(int k=0; k<n; k++) wait(NULL);

	return 0;
}




int main(int argc, char** argv){
	char buf[128];
	int n=0;


	while((n=readln(0, buf, 100))>0){
		if(n<7){
			n++;
			if((pid=fork())==0){
				if((pid2=fork())==0){
					dup2(buf,0);
					close(fd[0]);
					dup2(fd[1],1);
					close(fd[1]);
					execlp("fetchurls", "fetchurls", NULL);
				} else{
					wait(NULL);
					close(fd[1]);
					dup2(fd[0],0);
					close(fd[0]);
					execlp("uniq", "uniq");
				}
			}
			
		} else{
			wait(NULL);
			n--;
		}
	}
}








/*
pid_t pid;

void alarm_handler(int signum){
	kill(pid, SIGKILL);
}


int main(int argc, char** argv){

	int n=0, i, fd;
	char buf[128];
	char linha[100];
	int fd[2];
	pipe(fd);
	
	while((i=readline(0, buf, 128))>0){
		if(n<9){
			n++;
			if((pid=fork())==0){
				close(fd[0]);
				dup2(fd[1],1);
				close(fd[1]);
				signal(SIGALRM, alarm_handler);
				pid = getpid();
				alarm(60);
				execlp("agrep", "agrep", argv[1], buf, NULL);
			} 
		} else{
			wait(NULL);
			n--;
		}
	}

	int out = open(argv[2], O_CREAT | O_WRONLY, 0666);
	
	while((n=read(fd[0], linha, 100))>0){
		write(out, linha, n);
	}

	return 0;
}*/

// GRUPO III

int main(int argc, char** argv){

	int fd[2];
	pid_t pid;
	int n=0;
	pipe(fd);

	while((n=readline(0,buf,100))>0){
		if(n<7){
			n++;
			if((pid=fork())==0){ 
				n++;
				if((pid=fork())==0){
					int pp[2];
					pipe(pp);

					write(pp[1],buf,n);
					close(pp[1]);

					dup2(pp[0],0);
					close(pp[0]);

					close(fd[0]);
					dup2(fd[1], 1);
					close(fd[1]);
					execlp("fetchurls", "fetchurls", NULL);
				} else {
					wait(NULL);
					close(fd[1]);
					dup2(fd[0],0);
					close(fd[0]);
					execlp("uniq", "uniq", NULL);
				}
			}
		} else{
			wait(NULL);
			n=n-2;
		}
	}

	return 0;
}












































































