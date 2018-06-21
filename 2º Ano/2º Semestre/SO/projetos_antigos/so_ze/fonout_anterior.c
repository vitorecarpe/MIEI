#include <stdio.h>
#include <unistd.h>
#include <string.h>



int main(int argc, char** argv){

	int pd[2];
	char buf[100];
	char bufaux[100];
	pipe(pd);
	int n;

	if(fork() == 0){
		close(pd[0]);

		dup2(pd[1], 1);

		close(pd[1]);

		execlp(argv[1], argv[1], NULL);
		perror(argv[1]);
		_exit(0);
	}

	int i;
	int pdaux[argc-2][2];

	for(i = 0; i < (argc-2); i++){
		pipe(pdaux[i]);
	}


	//int prog[2];
	//pipe(prog);

	if(fork() == 0){
		close(pd[1]);
		for(i = 0; i < (argc-2); i++){

			close(pdaux[i][0]);
		}

		while(n = read(pd[0], buf, 10)){
			for(i = 0; i < (argc-2); i++){

				write(pdaux[i][1], buf, n);
			}
		}

		close(pd[0]);
		
		for(i = 0; i < (argc-2); i++){

			close(pdaux[i][1]);
		}

		_exit(0);
	}

	close(pd[0]);
	close(pd[1]);

	for(i = 0; i < argc-2; i++){
		close(pdaux[i][1]);
	}

	for(i = 2; i < argc; i++){

		if(fork() == 0){
			
			dup2(pdaux[i-2][0], 0);
			
			for(int j = 0; j < (argc-2); j++){

				close(pdaux[j][0]);
			}

			execlp(argv[i], argv[i], NULL);
			perror(argv[i]);
			_exit(0);
		}

	}

	for(i = 0; i < (argc-2); i++){
		close(pdaux[i][0]);
	}

	for(i = 0; i < argc; i++){
		wait(NULL);
	}


}