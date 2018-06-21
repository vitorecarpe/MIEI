#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>
 #include <glob.h>

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

int todos_fich(char *pat, char **comando, int indice, int tam){
	glob_t globbuf;
	int i;

	globbuf.gl_offs = 0;
    int r = glob(pat, GLOB_DOOFFS, NULL, &globbuf);
    
    if(r == 0){
/*    	for(i = 0; i < tam; i++){
	    	printf("%s\n", comando[i]);
	    }
*/
	    comando = realloc(comando , (tam + globbuf.gl_pathc + 1) * sizeof(char *));



	    strcpy(comando[indice], comando[tam-1]);

/*	    for(i = 0; i < tam; i++){
	    	printf("%s\n", comando[i]);
	    }
*/
	    i = tam-1;

	   	tam = (tam + globbuf.gl_pathc);

	   	comando[tam] = NULL;

	   	if(comando[tam] == NULL) printf("JÀFOSTE\n");


	   	for(int j = 0; i < (tam - 1); i++, j++){

//	   		printf("%s\n", globbuf.gl_pathv[j]);
	   		comando[i] = malloc(strlen(globbuf.gl_pathv[j]) + 1);
	   		strcpy(comando[i], globbuf.gl_pathv[j]);
//	   		printf("%s\n", comando[i]);
	   	}

//	   	comando[tam] = NULL;
	}

	return tam;
}


int mysystem(char *cmd){
	char **comando = malloc(10 * sizeof(char*));
	
	int i, r,  n = parteString(comando, cmd);

	if(comando[n] == NULL) printf("FOGO\n");

//	printf("%d\n", n);

	for(i = 0; (i < n) && !r; i++){
		int l = strlen(comando[i]);
//		printf("%d\n", l);

		if(l > 2){
			printf("%s\n", comando[i]);
			if((comando[i][0] == '*') && (comando[i][1] == '.')){
//				printf("%s\n", comando[i]);
				i--;
				r = 1;
			}
		}
	}

	if(r){
//		printf("%s\n", comando[i]);
		n = todos_fich(comando[i], comando, i, n);
//		for(i = 0; i < n; i++) printf("%s\n", comando[i]);
	}
	printf("%d\n", n);

//	comando[n] = NULL;

	for(i = 0; i < (n-1); i++) printf("%s\n", comando[i]);

	if(comando[n] == NULL) printf("ERAS\n");

	if(fork() == 0){
		execvp(comando[0], comando);
		perror(comando[0]);
		_exit(1);
	}
	
	wait(NULL);
	
	return 0;
}


int main(int argc, char* argv[]){
	int i;
//	char cmd[] = "ls -l -a";

//	char cmd[] = "ls";

//	char cmd[] = "ls -la";

	char cmd[] = "ls -la *.c"; //------> fazer função para que *.c dê todos os ficheiros c(man glob)	
	
	i = mysystem(cmd);

	printf("EXECUTEI %d\n", i);

	return 0;
}
