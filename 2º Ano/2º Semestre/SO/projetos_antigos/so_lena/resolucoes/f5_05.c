
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>

#define PIPE_SIZE 200

void splitPipe (char * buf,char** cmds){
	int i = 0,j = 0;
	cmds[j++] = buf;
	while(buf[i] != '\n'){
		if (buf[i] == '|'){
			buf[i] = '\0';
			cmds[j++] = &buf[i+1];
		}
		i++;
	}
	buf[i] = '\0';
	cmds[j] = NULL;
}

void split (char * buf,char** argv){
	int i = 0,j = 0;
	argv[j++] = buf;
	while(buf[i] != '\0'){
		if (buf[i] == ' '){
			buf[i] = '\0';
			argv[j++] = &buf[i+1];
		}
		i++;
	}
	buf[i] = '\0';
	argv[j] = NULL;
}

int main (){	
	int i = 0, status;
	int fd [2];
	char** cmds = (char**) malloc (10 * sizeof (char*)); 
	char** argv = (char**) malloc (10 * sizeof (char*));
	char buf [PIPE_SIZE];
	char* cmd; 
	int r = read(0,buf,PIPE_SIZE);
	splitPipe(buf,cmds);
	while(cmds[i++] != NULL){
		printf("%s\n", cmds[i-1]);
		pipe(fd);
		if (fork()){//se sou o pai
			close(fd[0]);
			dup2(fd[1],1);
			close(fd[1]);
			cmd = cmds[i];
			split(cmd,argv);
			execvp(argv[0],argv);
			wait(&status);
			_exit(0);
		}
		else {
			dup2(fd[0],0);
			close(fd[0]);
		}
	}
}