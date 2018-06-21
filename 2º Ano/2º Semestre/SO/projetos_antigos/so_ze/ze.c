#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#include "readln.h"
int main(int argc, char const *argv[]){

	int pd[5][2], n, i, status, q;
	char* a[5];
	char buffer[20];

	a[0] = "eu ";
	a[1] = "so ";
	a[2] = "quero ";
	a[3] = "é ";
	a[4] = "passar\n";
		
	for (i = 0; i < 5; i++){

		if (fork() == 0){
			pipe(pd[i]);

			close(pd[i][0]);

			write (pd[i][1], a[i], strlen(a[i]));

			close(pd[i][1]);
			_exit(i);
		}	
	}
	
	
	for (i = 0; i < 5; i++){


		wait(&status);
		q = WEXITSTATUS(status);
		if (WIFEXITED(status))
			printf("i: %d | STATUS: %d => \n", i, q);

		close(pd[q][1]);
		while((n = read (pd[q][0], buffer, 1)) > 0){
			write(1, buffer, n);
		}

		close(pd[q][0]);
	}		
}