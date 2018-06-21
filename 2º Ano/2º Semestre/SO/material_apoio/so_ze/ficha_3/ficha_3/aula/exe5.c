#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char* argv[]) {
	int i;
//	char *opts[] = {"ls", "-l", "-a", NULL};
	
//	printf("Antes\n");

	for(i = 1; i < argc; i++){
		if(fork() == 0){
			execlp(argv[i], /* (nome do fich exec. */
			argv[i], /* o que fica em argv[0] */
			NULL);

			perror(argv[i]);
			_exit(1);

		}
	}

	for(i = 1; i < argc; i++) wait(NULL);

	printf("Depois\n");
}