#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <signal.h>
#include <unistd.h>
 
/* Cria 10 processos filho, pausa-os a todos, e depois continua-os a todos */
 
void handler(int sig){
    switch(sig){
        case(SIGINT):
            printf("\nCTR+C pressed\n");
            exit(0);
        default:
            break;
    }
}
 
int main(){
 
    signal(SIGINT,handler);
    int pidArray[10];
    pid_t pd;
 
 
    for(int i = 0; i < 10; i++){
        pd = fork();
 
        if(pd == -1) { fprintf(stderr,"error: fork");exit(-1); }
        if(pd == 0){
            printf("processo %d\n",getpid());
            kill(getpid(),SIGSTOP);
            printf("processo %d acabado\n",getpid());
            execlp("ls", "ls", NULL);
            exit(0);
        }
        if(pd > 0){
            waitpid(-1,0,WUNTRACED); /* USADO PARA PROCESSOS QUE FICAM PAUSED, NAO TERMINADOS */
            pidArray[i] = pd;
        }
    }
 
    printf("----------\n");
 
    for(int i = 0; i < 10; i++){
        kill(pidArray[i],SIGCONT);
        wait(NULL);
    }
 
    printf("leaving\n");
    exit(0);
 
 
    return 0;
}
