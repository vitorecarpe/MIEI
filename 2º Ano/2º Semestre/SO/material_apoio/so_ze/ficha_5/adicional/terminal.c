#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>

#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

//#include "readln1.h"

int pos_inicial = 0;
int n_elementos = 0;
char aux[1024];

char **moveArray(char **res, int i, int n){

	res = realloc(res, sizeof(char *) * (n+1));

	int j;
	for(j = (n-1); j > i; j--){

		res[j] = malloc(strlen(res[j-1]) + 1);

		strcpy(res[j], res[j-1]);
	}

	res[n] = NULL;

	return res;
}

char **poeArray(char **res, int i, int n, char **aux){

	int k;
	for(k = 0; k < n; k++){

		res[k+i] = malloc(strlen(aux[k]) + 1);
		strcpy(res[k+i], aux[k]);
	}

	res[i+n] = NULL;
	return res;
}

static int readchar(int fildes, char* buf) {

        if (pos_inicial == n_elementos) {
                n_elementos = read(fildes, aux, 1024);
                pos_inicial = 0;
        }

        if(n_elementos == 0) return 0;

        *buf = aux[pos_inicial];

        pos_inicial += 1;

        return 1;
}



ssize_t readln (int fildes, char *buf, size_t nbyte) {
	int i=0;

	
	int n = readchar(fildes, buf);


	while ( i<(nbyte-1) && n > 0 && buf[i] != '\n') {
         i++;

         n = readchar(fildes, buf + i);
     }

   if(i>=nbyte || n == 0) {
	     	buf[i] = 0;
	     	return i;
	     }
    else
    		buf[i+1]= '\0';

     return (i+1);
}

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

int mysystem(char *cmd){
	char **comando = malloc(sizeof(char *) * 12);
	int controlo = 1;
	int algo = 1;
	int i;
	
	int n = parteString(comando, cmd);

	if(strcmp(comando[n-1], "&") == 0) controlo = 0;

	//printf("%s\n", comando[0]);

	if(strcmp(comando[0], "exit") == 0){
				_exit(0);
	}


	if(strcmp(comando[0], "cd") == 0){
			int i = 1, tam = 0;
			//for(i = 2; cmd[i] != '\0' && cmd[i] == ' '; i++);

			while(comando[i] != NULL){

				tam += strlen(comando[i]);
				i++;
			}
			
			char *res = malloc(tam);
			strcpy(res, "");
			i = 1;

			while(comando[i] != NULL){

				strcat(res, comando[i]);
				i++;
			}

			//printf("%s\n", res);

			chdir(res);
			//controlo = 0;
			system("ls");
		}

	else{

		if(fork() == 0){
			int atual = 0;

			if(n > 2){

				for(i = 0; comando[i]!=NULL && (strcmp(comando[i], "<") != 0) && (strcmp(comando[i], ">") != 0) && (strcmp(comando[i], ">>") != 0) && (strcmp(comando[i], "2>") != 0) && (strcmp(comando[i], "2>>") != 0); i++);

				if(comando[i] != NULL){
					atual = 0;
					if(strcmp(comando[i], "<") == 0){
						char **aux = malloc(sizeof(char *) * i);
						for(int k = 0; k < i; k++){

							aux[k] = comando[k];
							printf("%s\n", aux[k]);
						}


						//printf("OLAALALLALA<<<<<<<<\n");
						comando[atual] = malloc(11);
						strcpy(comando[atual], ".././redir2");
						atual++;

						comando[atual] = malloc(2);
						strcpy(comando[atual], "-i");
						atual++;

						char *aux2 = comando[i+1];

						if(aux2){
							comando[atual] = malloc(strlen(aux2) + 1);

							strcpy(comando[atual], aux2);
							atual++;
						}

						//comando = moveArray(comando, 2, n+1);
						comando = poeArray(comando, atual, i, aux);

						for(int j = 0; j < atual + i; j++){

							printf("%s\n", comando[j]);
						}

						algo = 0;

						//printf("ENTREI\n");
					}

					else{

					atual = 0;
					if(strcmp(comando[i], ">") == 0){
						char **aux = malloc(sizeof(char *) * i);
						for(int k = 0; k < i; k++){

							aux[k] = comando[k];
							printf("%s\n", aux[k]);
						}

						comando[atual] = malloc(11);
						strcpy(comando[atual], ".././redir2");
						atual++;

						comando[atual] = malloc(2);
						strcpy(comando[atual], "-ot");
						atual++;

						char *aux2 = comando[i+1];

						if(aux2){
							comando[atual] = malloc(strlen(aux2) + 1);

							strcpy(comando[atual], aux2);
							atual++;
						}

						//comando = moveArray(comando, 2, n+1);
						comando = poeArray(comando, atual, i, aux);

						for(int j = 0; j < atual + i; j++){

							printf("%s\n", comando[j]);
						}

						algo = 0;
						}

						else{
							atual = 0;
							if(strcmp(comando[i], ">>") == 0){
								char **aux = malloc(sizeof(char *) * i);
								for(int k = 0; k < i; k++){

									aux[k] = comando[k];
									printf("%s\n", aux[k]);
								}

								comando[atual] = malloc(11);
								strcpy(comando[atual], ".././redir2");
								atual++;

								comando[atual] = malloc(2);
								strcpy(comando[atual], "-oa");
								atual++;

								char *aux2 = comando[i+1];

								if(aux2){
									comando[atual] = malloc(strlen(aux2) + 1);

									strcpy(comando[atual], aux2);
									atual++;
								}

								//comando = moveArray(comando, 2, n+1);
								comando = poeArray(comando, atual, i, aux);

								for(int j = 0; j < atual + i; j++){

									printf("%s\n", comando[j]);
								}

								algo = 0;
								}

								else{
									atual = 0;
									if(strcmp(comando[i], "2>") == 0){
										char **aux = malloc(sizeof(char *) * i);
										for(int k = 0; k < i; k++){

											aux[k] = comando[k];
											printf("%s\n", aux[k]);
										}

										comando[atual] = malloc(11);
										strcpy(comando[atual], ".././redir2");
										atual++;

										comando[atual] = malloc(2);
										strcpy(comando[atual], "-et");
										atual++;

										char *aux2 = comando[i+1];

										if(aux2){
											comando[atual] = malloc(strlen(aux2) + 1);

											strcpy(comando[atual], aux2);
											atual++;
										}

										//comando = moveArray(comando, 2, n+1);
										comando = poeArray(comando, atual, i, aux);

										for(int j = 0; j < atual + i; j++){

											printf("%s\n", comando[j]);
										}

										algo = 0;
										}

									else{

										atual = 0;
										if(strcmp(comando[i], "2>>") == 0){
											char **aux = malloc(sizeof(char *) * i);
											for(int k = 0; k < i; k++){

												aux[k] = comando[k];
												printf("%s\n", aux[k]);
											}

											comando[atual] = malloc(11);
											strcpy(comando[atual], ".././redir2");
											atual++;

											comando[atual] = malloc(2);
											strcpy(comando[atual], "-ea");
											atual++;

											char *aux2 = comando[i+1];

											if(aux2){
												comando[atual] = malloc(strlen(aux2) + 1);

												strcpy(comando[atual], aux2);
												atual++;
											}

											//comando = moveArray(comando, 2, n+1);
											comando = poeArray(comando, atual, i, aux);

											for(int j = 0; j < atual + i; j++){

												printf("%s\n", comando[j]);
											}

											algo = 0;
											}
									}
								}
						}

					}
				}

				else{

												printf("ENTREI PIPE\n");
												int pipe = 0;
												char *auxpp[12];
												int jatem = 0;
												for(i = 0; comando[i] != NULL; i++){
													if(strcmp(comando[i], "|") == 0){

														pipe++;
														jatem = 0;

													}

													else{

														if(jatem == 0){
														auxpp[pipe] = malloc(strlen(comando[i]) + 1);
														strcpy(auxpp[pipe], comando[i]);
														jatem = 1;
														}

														else{
															auxpp[pipe] = realloc(auxpp[pipe], strlen(auxpp[pipe] + strlen(comando[i]) + 2));
															strcat(auxpp[pipe], " ");
															strcat(auxpp[pipe], comando[i]);
														}
													}
												}

												auxpp[pipe+1] = NULL;

												if(pipe != 0){

													comando[0] = malloc(strlen("./pipe") + 1);
													strcpy(comando[0], "./pipe");
												}

												int z;
												for(z = 0; auxpp[z] != NULL; z++){

													comando[z+1] = malloc(strlen(auxpp[z]) + 1);

													strcpy(comando[z+1], auxpp[z]);
												}

												char cpipe[3];

												sprintf(cpipe, "%d", pipe + 1);

												comando[z+1] = malloc(strlen(cpipe) + 1);

												strcpy(comando[z+1], cpipe);

												comando[z+2] = NULL;

												algo = 0;


										}
			}

			if(algo){
				execvp(comando[0], comando);
				printf("Ola\n");
				perror(comando[0]);
				_exit(1);
				}
			
			else{

				execv(comando[0], comando);
				perror(comando[0]);
				_exit(1);
				}
			//}
				
		}
		
		if(controlo) wait(NULL);
	}

	return 2;
}

int main(){
	
	char buffer[128 + 1];

	int fd = 0;
	int i, n;
	
	n = readln(fd, buffer, 128);
	
	while( n > 0){

			i = mysystem(buffer);

			printf("%d\n", i);
			n = readln(fd, buffer, 128);
	}
	
	return 0;
}