#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>


//#include "readln1.h"

int pos_inicial = 0;
int n_elementos = 0;
char aux[1024];

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

void parteString(char* args[], char *comando){
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
}

int mysystem(char *cmd){
	char *comando[10];
	
	parteString(comando, cmd);

	if(fork() == 0){
		execvp(comando[0], comando);
		perror(comando[0]);
		_exit(1);
	}
	
	wait(NULL);
	
	return 0;
}

char ultimo(char *buf, int n){
	int r = 0, i;
	char res = ' ';

	for(i = n-1; i > 0 && !r; i--){

		if(buf[i] != ' '){
			r = 1;
			res = buf[i];
		}
	}
	return res;
}

int main(){
	
	char buffer[128 + 1];

	int fd = 0;
	int i, n;
	
	n = readln(fd, buffer, 128);
	
	while( n > 0){
//		printf("%c\n", buffer[n-2]);

		if(ultimo(buffer, n-1) == '&') {

			printf("%c\n", buffer[n-2]);

			buffer[n-2] = '\n';
			
			if(fork() == 0){

				printf("Entrei\n");
				i = mysystem(buffer);
				n = readln(fd, buffer, 128);
			}
		}

		else{
			i = mysystem(buffer);
			n = readln(fd, buffer, 128);
		}
	}
	
	return 0;
}
