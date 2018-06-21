#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>

int readln (int fildes, char* buf, int n_bytes){
	int i,j;
	char c;
	for(i = 0;i < n_bytes && read(fildes,&c,1) == 1 && c != '\n';i++)
		buf[i] = c;
	buf[i] = '\0';
	return i;
}

int main (){
	int o = open("ordenar",O_RDONLY),nr;
	if (o == -1){
		perror("Erro na abertura do pipe!");
		_exit(-1);
	}
	char fich [PIPE_BUF];
	char fichN [PIPE_BUF + 7];
	while((nr = readln(o,fich,PIPE_BUF)) > 0){
		int fd[2];
		pipe(fd);
		if(!fork()){
			int r = open(fich,O_RDONLY);
			dup2(r,0);close(r);
			dup2(fd[1],1);close(fd[1]);
			close(fd[0]);
			execlp("cat","cat",NULL);
			_exit(-1);
		}else{
			close(fd[1]);
			sprintf(fichN,"%s.sorted",fich);
			int w = open(fichN,O_WRONLY | O_CREAT | O_TRUNC,0666);
			while((nr = read(fd[0],fich,PIPE_BUF)) > 0)
				write(w,fich,nr);
			close(fd[0]);
			close(w);
		}
	}
	return 0;
}