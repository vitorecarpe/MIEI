#include <stdio.h>
#include <unistd.h>
#include <string.h>

int main(){
	int pfd[2];

	pipe(pfd);
	
	if(fork()==0){

		close(pfd[0]);

		dup2(pfd[1], 1);

		close(pfd[1]); //--> não é preciso ter dois apontadores para o mesmo sitio

		execlp("ls", "ls", "/etc", NULL);
		perror("ls");
		_exit(1);
		
	
		//printf("Terminei\n");
	//	_exit(0);
	}

	else{

		//wait(NULL);
		if(fork() == 0){
			close(pfd[1]);

			dup2(pfd[0], 0);
			close(pfd[0]);

			execlp("wc", "wc", "-l", NULL);
			perror("wc");
			_exit(1);
		}

		else{

			close(pfd[0]);
			close(pfd[1]);
			int i = 0;
			while(i < 2){
				wait(NULL);
				i++;
			}
		}
		//close(pfd[1]);
	}	
}