#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>

int main(int argc, char *argv []){
	int r,w;
	char **args;
	if (argc < 2){
		perror("erro ao passar os argumentos");
		_exit(-1);
	}
	if(strcmp(argv[1],"-i") == 0){
		r = open(argv[2],O_RDONLY);
		dup2(r,0);close(r);
		args = argv + 3;
		if(strcmp(argv[3],"-o") == 0){
			w = open(argv[4],O_WRONLY | O_CREAT | O_TRUNC, 0666);
			dup2(w,1);close(r);
			args = argv + 5;
		} 
	}
	else if(strcmp(argv[1],"-o") == 0){
			w = open(argv[2],O_WRONLY | O_CREAT | O_TRUNC, 0666);
			dup2(w,1);close(r);
			args = argv + 3;
	}
	execvp(args[0],args);
	_exit(-1);
}