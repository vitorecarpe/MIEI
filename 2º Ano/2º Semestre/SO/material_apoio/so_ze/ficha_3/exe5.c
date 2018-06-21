#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char* argv[]){
	int i, s;

	for(i = 1; i < argc; i++){	
		if(fork() == 0){
			execlp(argv[i], argv[i], NULL);
			perror(argv[i]);
			
			_exit(1);
		}
	}	
	
	for(i = 1; i < argc; i++){
		
		wait(&s);
		if (WIFEXITED(s) && WEXITSTATUS(s) != 0)
			printf("%d\n", WEXITSTATUS(s));	
	}

	return 0;
}
