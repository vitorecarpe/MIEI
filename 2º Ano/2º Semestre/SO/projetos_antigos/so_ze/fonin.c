#include <limits.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>
#include "readln.h"

void fonin(char* nome, char** argv, int argc){

	int fd = open(nome, O_WRONLY);
	int i;

	for(i = 0; i < argc; i++){

		if(fork() == 0){
				int n;
				while(1){
					int le = open(argv[i], O_RDONLY);

					char buf[PIPE_BUF];
					

					while((n = readln(le, buf, PIPE_BUF)) > 0){

							write(fd, buf, n);
					}

					close(le);
					}

					close(fd);
					_exit(0);
		}
	}

	close(fd);

	for(i = 0; i < argc; i++){
		wait(NULL);
	}
}