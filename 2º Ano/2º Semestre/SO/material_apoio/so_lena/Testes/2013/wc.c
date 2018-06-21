#include <sys/types.h>
#include <sys/stat.h>
#include <limits.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/wait.h>
 
void cleanBuf(char* buf,int size){
    for(int i = 0; i < size; i++)
        buf[i] = '\0';
}
 
int readLine(int fp,char* buf,int size){
    char c;
    int status;
    int nbytes = 0;
    while( (status = read(fp,&c,1)) > 0){
        if(status == -1) return -1;
        else if(c == '\n') return nbytes;
        else buf[nbytes++] = c;
    }
    return nbytes;
}
 
int main(int argc,char** argv){
    if(argc != 2){ perror("usage: ./countLines <file name>"); exit(-1); }
 
    int p[2];
    pipe(p);
    pid_t pid;

    int fp = open(argv[1],O_RDONLY);
    if(fp == -1){ perror("error opening file\n"); exit(-1); }
    char buffer[PIPE_BUF]; cleanBuf(buffer,PIPE_BUF);
    char line[PIPE_BUF]; cleanBuf(line,PIPE_BUF);
 
    if( (pid = fork()) == 0){
        dup2(p[0],0); close(p[0]); close(p[1]);
        execl("./wc2","./wc2",NULL);
        exit(-1);
    }
    close(p[0]);
    int r;
    while((r = readLine(fp,buffer,PIPE_BUF)) > 0){
        buffer[r] = '\n';
        write(p[1],buffer,strlen(buffer));
        cleanBuf(buffer,PIPE_BUF);
        sleep(1);
    }
    
    close(p[1]);
    wait(NULL);

    kill(pid,SIGKILL);
 
    return 0;
}