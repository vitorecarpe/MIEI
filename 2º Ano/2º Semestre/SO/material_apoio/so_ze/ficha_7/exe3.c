#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

#define l 10
#define c 10000


int main(){

	int matriz[l][c];
	int filhos[l];
	int f, j, i;

	for(i = 0; i < l; i++)
		for(j = 0; j < c; j++) matriz[i][j] = 0;


	matriz[2][777] = 12;
	matriz[3][1] = 12;
	matriz[7][9123] = 12;

	for(i = 0; i < l; i++){

		f = fork();

		if(f == 0){
			int r = 0;
			


			for(j = 0; j < c && !r; j++){
				

				
				if(matriz[i][j] == 12) {
					printf("Encontrei, linha %d\n", i);
					r = 1;
				}

			
			}

			_exit(!r);
		}

		else{
			filhos[i] = f;
		}
	}

	int r = 1, status;

	for(i = 0; i < l; i++) {
		int pid = wait(&status);

		if(WIFEXITED(status)) {
			r = WEXITSTATUS(status);
			if(!r){

				for(int j = 0; j < l; j++){
					if(filhos[j] != pid){

						kill(filhos[j], 9);
					}
				}
			}
		}
	}

	//if(r) printf("Não encontrei\n");
//	else printf("Encontrei\n");	

	return 0;
}