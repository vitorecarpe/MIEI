#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */
#include <limits.h>
#include <signal.h>
#include <sys/wait.h>
#include <string.h>

int readLine(int filedes, char *buffer, int size){
    int i = 0, r;
    while (i < size && (r = read(filedes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
    if (r == -1){
        perror("FALHA!");
        exit(-1);
    }
    return i;
}

void main(int argc, char *argv[]){
    char *palavra = argv[1];
    char buffer[PIPE_BUF];
    int total=0;
    int r;

    for(int i=2; i<argc; i++){
        char *fich = argv[i];

        int batatas[2];
        pipe(batatas);

        int pidFilho = fork();

        if( pidFilho==0 ){ // filho
            close(batatas[0]);
            dup2(batatas[1],STDOUT_FILENO);
            close(batatas[1]);
            execlp("./encontra","./encontra",palavra,fich,NULL);
            exit(-1);
        }
        else{ // pai
            close(batatas[1]);
            while( (r=readLine(batatas[0],buffer,PIPE_BUF))>0 ) {
                total++;
                write(1,buffer,r);
            }
            close(batatas[0]);
        }
    }
    printf("Ocorrem %d vezes a palavra \"%s\" \n",total,palavra);
}