#include <limits.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>
#include "parteString.h"
#include "readln.h"
#include "auxiliar.h"

void fonout(char* programa, char* nome, char** argv, int argc){
	 int pd[2];
	 pipe(pd);
	 int fork1 = 0;
	
	if(fork() == 0){
		while(1){	
			if(fork() == 0){
				int fd = open(nome, O_RDONLY);
				close(pd[0]);
				dup2(fd, 0);
				close(fd);
				dup2(pd[1], 1);
				close(pd[1]);

				char* funcao[10];
				int num = parteString(funcao, programa);
				funcao[0] = muda_programa(funcao[0]);
				//printf("Programa: %s\n", funcao[0]);
				execvp(funcao[0], funcao);
				perror(funcao[0]);
				_exit(0);
			}

			wait(NULL);
		}
	}

	fork1++;


	if(fork() == 0){

		close(pd[1]);
		dup2(pd[0], 0);
		close(pd[0]);
		char buf[PIPE_BUF];
		int pipes[argc];
		int n;
		
		while(1 /*&& n != -1*/){
			/*for(int i = 0; i < argc; i++){
				pipes[i] = open(argv[i], O_WRONLY);
			}

			
			while((n = readln(0, buf, PIPE_BUF)) > 0){

				for(int i = 0; i < argc; i++){
					write(pipes[i], buf, n);
				}	
			}

			for(int i = 0; i < argc; i++){
				close(pipes[i]);
			}
			*/
			for(int i = 0; i < argc; i++){
					pipes[i] = open(argv[i], O_WRONLY);
			}

			while((n = readln(0, buf, PIPE_BUF)) > 0){
				

				for(int i = 0; i < argc; i++){
					int f = fork();

					if(f == -1){
						//ERRO
					}

					else{

						if(f == 0){
							for(int j = 0; j < argc; j++){
								if(j != i){
									close(pipes[j]);
								}
							}

							write(pipes[i], buf, n);

							printf("PASSEI NO PIPE: %d\n", i);

							close(pipes[i]);

							_exit(0);

						}
					}
				}
			}

			for(int i = 0; i < argc; i++){
				close(pipes[i]);

			}

			for(int i = 0; i < argc; i++){
				wait(NULL);		
			}


		}
		
		
		_exit(0);

	}

	fork1++;

	close(pd[0]);
	close(pd[1]);

	for(int i = 0; i < fork1; i++){

		wait(NULL);
	}

}																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																											