#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */
#include <limits.h>
#include <signal.h>
#include <sys/wait.h>
#include <string.h>

int pidFilho;
char buffer[PIPE_BUF];
int i = 0;
int secs;
int size;

void assassinar(){
    while( i<size && write(STDOUT_FILENO,&buffer[i],1)>0 ) 
        i++;
    write(STDOUT_FILENO,"\n",1);
    kill(pidFilho,SIGKILL);
}

int main(int argc, char const *argv[]){
    secs = atoi(argv[1]);
    size = atoi(argv[2]);

    signal(SIGALRM,assassinar);

    int batatas[2];
    pipe(batatas);
    alarm(secs);
    pidFilho = fork();
    if(pidFilho==0){
        close(batatas[0]);
        dup2(batatas[1], STDOUT_FILENO);
        close(batatas[1]);
        execvp(argv[3],argv+3);
        exit(-1);
    }
    else{
        close(batatas[1]);
        while( i<size && read(batatas[0],&buffer[i],1)>0 ){
            write(STDOUT_FILENO,&buffer[i],1);
            i++;
        }
        write(STDOUT_FILENO, "\n", 1);
        kill(pidFilho,SIGKILL);
        close(batatas[0]);
    }

    return 0;
}
