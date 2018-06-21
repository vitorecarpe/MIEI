#include <stdio.h>
#include <unistd.h>
#include <string.h>

int main(){

	int pd[2];
	int n; 

	char buf[100];
	pipe(pd);

	if(fork() != 0){

		close(pd[1]);

		while(n = read(pd[0], buf, 5)){
			buf[n] = '\0';
			printf("%s", buf);
		}

		printf("\n");

		//_exit(0);
	}

	else{

		close(pd[0]);

		write(pd[1], "mr reese não merecia aquiloa", 30);

		sleep(5);

		write(pd[1], "ele merecia acabar bem", 22);

		//wait(NULL);
	}
}