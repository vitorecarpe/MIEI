#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>
#include <glob.h>


int existe_control(int *control, int n){

	int i, res = 0;

	for(i = 0; i < n && !res; i++){

		res = (control[i] != 0);
	}
	return res;
}


int main(int argc, char **argv){

	int i, r = 1, status, aux, boolean = 1, j, pid_aux;

	for(i = 1; i < argc; i++){
		int l = strlen(argv[i]);

		char *aux = malloc(l+3);

		strcpy(aux, "./");
		strcat(aux, argv[i]);

		argv[i] = malloc(l + 3);
		strcpy(argv[i], aux);

		free(aux);
	}

	int args[argc-1];
	int pid[argc-1];
	int control[argc-1];
	for(i = 0; i < (argc-1); i++)  args[i] = 0;
	for(i = 0; i < (argc-1); i++)  control[i] = 1;

	while(existe_control(control, argc-1)){
		
		for(i = 1; i < argc; i++){
						
					if(control[i-1]){

						pid_aux = fork();
						
						if(pid_aux == 0){
							//int j = 0;
							//int res = execlp(argv[i], argv[i], NULL);
							execlp(argv[i], argv[i], NULL);
							perror(argv[i]);
							_exit(0);				
						}

						pid[i-1] = pid_aux;
					}
			}

		for(i = 1; i < argc; i++){
				
				if(control[i-1]){	

					pid_aux = wait(&status);
					if(WIFEXITED(status)) {
						aux = WEXITSTATUS(status);

						boolean = 0;

						for(j = 0; j < (argc-1) && !boolean; j++){

								if(pid_aux == pid[j]) {
									boolean = 1;
									j--;
								}
						}

						if(boolean){
							args[j]++;

							control[j] = (aux != 0);
						}
						
					}
				}
		}

	}


	for(i = 1; i < argc; i++){

		printf("%s: 	%d\n", argv[i], args[i-1]);
	}

	return 0;
}