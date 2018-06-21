#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

#define l 10
#define c 10000


int main(){

	int matriz[l][c];
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
					//printf("Encontrei, linha %d\n", i);
					r = 1;
				}

			
			}

			if(r) _exit(i);
			else _exit(l+1);
		}
	}

	int r = 1, status, enc[l], n = 0;

	for(i = 0; i < l; i++) {
		wait(&status);	

			if(WIFEXITED(status)) {
				r = WEXITSTATUS(status);
				if(r < l){
					enc[n] = r;
					n++;
				}
		}
	}


	if(n == 0) printf("Não encontrei\n");
	else {
		printf("Encontrei. Na(s) linha(s):\n");

		for(i = 0; i < n; i++){
			printf("%d\n", enc[i]);
		}
		}	

	return 0;
}