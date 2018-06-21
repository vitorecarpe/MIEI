#include <signal.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <string.h>

int main(){

	if(fork() == 0){
		execlp("./filter", "./filter", NULL);
		perror("./filter");
		_exit(0);
	}

	wait(NULL);
	return 0;

	char* pr = malloc(1);
	pr[0] = '\0';
	pr = realloc(pr, 10);
	strcat(pr, "olaolaola");
	pr = realloc(pr, 15);
	strcat(pr, "olol");
	printf("%s\n", pr);
}