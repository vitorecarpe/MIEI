#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>


// int mkfifo(const char *pathname, mode_t mode);

//1

int main(int argc, char** argv){
	int fifo = mkfifo("fifo", 0666);
	return 0;
}
int main(int argc, char** argv){
	int n;
	char buf[1024];
	int pipe = open("fifo", O_WRONLY);

	while((n=read(0,buf,1024))>0){
		write(pipe, buf, n);
	}
	close(pipe);
	return 0;
}
int main(int argc, char** argv){
	int n;
	char buf[1024];
	int pipe = open("fifo", O_RDONLY);

	while((n=read(pipe,buf,1024))>0){
		write(1, buf, n);
	}
	close(pipe);
	return 0;
}

//2
int main(int argc, char** argv){
	int log = open("log", O_CREAT | O_RDWR, 0666);
	int fifo = mkfifo("fifo", 0666);
	int pipe = open("fifo",O_RDONLY);
	int n;
	char buf[1024];

	while((n=read(pipe,buf,1))>0){
		write(log,buf,1);
	}
	return 0;
}

int main(int argc, char** argv){
	int i;

	int pipe = open("fifo",O_WRONLY);

	for(i=1;i<argc;i++){
		write(pipe, argv[i], strlen(argv[i]));
	}

	return 0;
}