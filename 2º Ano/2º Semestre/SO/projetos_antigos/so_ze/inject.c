#include <signal.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <limits.h>

#include "auxiliar.h"
//int inject()

int main(int argc, char** argv){
	
	if(argc > 2){
		int f = fork();

		if(f == -1){
			//sprintf(aux, "sou o nodo %s", argv[1]);
			char *s = "Não deu o fork\n";
			int fd = open("error.txt", O_WRONLY|O_CREAT, 0644);
			if(fd != -1){
				write(fd ,s, strlen(s));
			}
			//não consegiu criar, dá erro
		}

		else{

			if(f == 0){
				int fd = open(argv[1], O_WRONLY);
				dup2(fd, 1);
				close(fd);

				argv[2] = muda_programa(argv[2]);

				execvp(argv[2], argv+2);
				perror(argv[2]);
				_exit(0);
			}
	
				wait(NULL);
			
		}
	}

	else{
		char *s = "Não tem argumentos suficientes\n";
		int fd = open("error.txt", O_WRONLY|O_CREAT, 0644);
		if(fd != -1){
			write(fd ,s, strlen(s));
		}
}

	return 0;
}