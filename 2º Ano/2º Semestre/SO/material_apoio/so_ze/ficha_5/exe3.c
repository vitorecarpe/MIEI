#include <stdio.h>
#include <unistd.h>
#include <string.h>

int main(){

	int pd[2];
	int n; 

	char buf[100];
	pipe(pd);

	if(fork() == 0){	

		close(pd[1])	
		dup2(pd[0], 0);

		close(pd[0]);

		execlp("wc", "wc", NULL);

		_exit(0);
	}


		close(pd[0]);
		while((n = read(0, buf, 100)) > 0){
			write(pd[1], buf, n);
		}

		close(pd[1]);


	wait(NULL);
}