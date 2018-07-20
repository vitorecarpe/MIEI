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
    while( i<size && (r=read(filedes,&buffer[i],1))>0 && buffer[i]!='\n' ) { i++; }
    if(r==-1){
        perror("FALHA!");
        exit(-1);
    }
    return i;
}

int main(int argc, char const *argv[]){
    int pipe = open("ordenar",RDONLY);
    if( pipe==0 ){
        perror("FALHA! pipe");
        exit(-1);
    }

    int r1;
    char file[PIPE_BUF];
    while( readLine(pipe,file,PIPE_BUF)>0 ){
        r = open(file,O_RDONLY,0600);
        char fileNew[strlen(file)+7];
        sprintf(fileNew,"%s.sorted",file);
        int w = open(fileNew, O_WRONLY|O_CREAT|O_TRUNC,0600);

        int pid = fork();
        if( pid==0 ){
            dup2(r, 0); close(r);
            dup2(w, 1); close(w);
            execlp("sort","sort",NULL);
            exit(-1);
        }
        else wait(NULL);
    }
    return 0;
}