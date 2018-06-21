#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

int parteString(char* args[], char *comando){
	int i = 0;
/*	for(i = 0; comando[i] != '\0'; i++){
		if(comando[i] == ' ') {
			c[k][j] = '\0';
			k++;
			j = 0;
		}

		else{
			c[k][j] = comando[i];
			j++;
		}
	}

	c[k] = '\0';
*/	args[i] = strtok(comando, " \t\n");

	while(args[i] != NULL){
		args[++i] = strtok(NULL, " \t\n");
	}

	return i;	
}


int main(){
	int i = 0, j;

	char *aux[5];

	//int pfd[3][2];
	
	int anterior[2];
	int seguinte[2];
	
	char *arg[4];

	arg[0] = malloc(23);
	arg[1] = malloc(12);
	arg[2] = malloc(5);
	arg[3] = malloc(6);

	strcpy(arg[0], "grep -v ^# /etc/passwd");
	strcpy(arg[1], "cut -f7 -d:");
	strcpy(arg[2], "uniq");
	strcpy(arg[3], "wc -l");

	/*pipe(pfd[0]);
	pipe(pfd[1]);
	pipe(pfd[2]);
	*/
	
	while(i < 4){

		if(i < 3) {
			//pipe(pfd[i]);
			pipe(seguinte);
		}

		if(fork() == 0){

			if(i > 0){

			dup2(anterior/*[i-1]*/[0], 0);
//			close(pfd[i-1][0]);
			}

			//printf("%s\n", arg[i]);

			if(i < 3) {
/*
				for(int j = 0; j < 3; j++){
					
					if(j != i){
						close(pfd[j][0]);
						close(pfd[j][1]);
					}
				}
*/
//				close(pfd[i][0]);
				dup2(seguinte/*[i]*/[1], 1);
//				close(pfd[i][1]);


			}

/*			for(j = 0; (j < 3) && (j < i); j++){

				close(pfd[j][0]);
				close(pfd[j][1]);
			}


*/			
			//printf("%s\n", arg[i]);

			close(anterior[0]);
			close(anterior[1]);
			close(seguinte[0]);
			close(seguinte[1]);

			j = parteString(aux, arg[i]);

			//printf("%s\n", aux[0]);

			execvp(aux[0], aux);
			perror(aux[0]);
			_exit(1);

		}

		i++;
		if(i > 1) close(anterior[0]);
		//close(anterior[1]);
		/*close(seguinte[0]);
		close(seguinte[1]);
		*/

		anterior[0] = seguinte[0];
		anterior[1] = seguinte[1];

		//close(seguinte[0]);
		close(seguinte[1]);
	}

	//close(seguinte[0]);
	//close(seguinte[1]);

	//close(anterior[0]);
	//close(anterior[1]);

/*	for(j = 0; j < 3; j++){

				close(pfd[j][0]);
				close(pfd[j][1]);
	}
*/
/*	for(i = 0; i < 4; i++){

		wait(NULL);
	}
*/
	return 0;

}