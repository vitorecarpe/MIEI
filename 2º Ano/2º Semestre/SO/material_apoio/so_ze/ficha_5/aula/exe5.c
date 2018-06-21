#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

int main(){
	
	char *cmd[] = {
		"grep -v ^# /etc/passwd", "cut -f7 -d:", "uniq", "wc -l"
	};



	char *args[10];

	int n = 4; // = ...

	int i, seguinte[2], anterior;

	for(i = 0; i < n; i++){

		if(i < (n-1)) pipe(seguinte);

		if(fork() == 0){

			//Filho

			if(i > 0){
				dup2(anterior, 0);
				close(anterior);
			}

			if(i < (n-1)){
				close(seguinte[0]);
				dup2(seguinte[1], 1);
				close(seguinte[1]);
			}

			int j = 0;

			char *aux = malloc(strlen(cmd[i]) + 1);
			strcpy(aux, cmd[i]);

			args[j] = strtok(aux, " \t\n");//strtok(cmds[i], ...) ;
			
			while(args[j] != NULL){
				args[++j] = strtok(NULL, " \t\n");
			}

			execvp(args[0], args);
			perror(args[0]);
			_exit(1);
		}

		//Pai
		if(i < (n-1)) close(seguinte[1]);
		
		if(i > 0) close(anterior);

		anterior = seguinte[0];
	}

	for(i = 0; i < n; i++){
		wait(NULL);
	}
}