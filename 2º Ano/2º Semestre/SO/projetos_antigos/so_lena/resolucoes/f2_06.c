#include <unistd.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <time.h>

int main(){
	srand(time(NULL));
	int matriz [10][10000];
	int p;
	int i,j;
	int status,s;
	int res = 0;

	for(i= 0; i < 10; i++)
		for(j = 0; j < 10000; j++)
			matriz[i][j] = rand()%100000;
	for(i = 0; i < 10; i++){
		p = fork();
		if (!p) {
			int k;
			int e = 0;
			for (k = 0; k < 10000 && !e; k++)
				if (matriz[i][k] == 3) e = 1;
			_exit(e);
		}
	}
	for(i = 0; i < 10; i++){
		wait(&status);
		res = res || WEXITSTATUS(status);
	}
	printf("%d\n",res );
	_exit(0);
	
}
