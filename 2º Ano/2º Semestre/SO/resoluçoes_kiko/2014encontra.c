#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */
#include <limits.h>
#include <signal.h>
#include <sys/wait.h>
#include <string.h>


int readLine(int filedes, char* buffer, int size){
    int i=0, r;
    while( i<size && (r=read(filedes,&buffer[i],1))>0 && buffer[i++]!='\n' );
    if(r==-1){
        perror("FALHA!");
        exit(-1);
    }
    return i;
}

void main(int argc, char *argv[]){
    char *palavra = argv[1];
    char *fich = argv[2];
    char buffer[PIPE_BUF];
    int i=0, r;
    int fd = open(fich,O_RDONLY);

    while ((r = readLine(fd, buffer, PIPE_BUF))>0){
        i++;
        if( strstr(buffer,palavra)!=NULL ){
            write(1, buffer, r);
        }
    }
    exit(0);
}