#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>

int main(int argc, char *argv []){
	int r,w;
	char **args;
	if (!strcmp(argv[1],"-i")){
		r = open(argv[2],O_RDONLY);
		dup2(r,0);
		close(r);
		if (!strcmp(argv[3],"-o")){
			w = open(argv[4],O_WRONLY | O_CREAT | O_TRUNC, 0666);
			dup2(w,1);
			close(w);
			args = argv + 5;
		}
		else args = argv + 3;
	}
	else if (!strcmp(argv[1],"-o")){
		w = open(argv[2],O_WRONLY | O_CREAT | O_TRUNC, 0666);
		dup2(w,1);
		close(w);
		args = argv + 3;
	}
	else args = argv + 1;
	execvp(args[0],args);
}

//testar: ./f4_04 -i saida.txt -o saida4.txt wc -c
