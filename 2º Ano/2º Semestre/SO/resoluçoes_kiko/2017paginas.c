#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>

int readchar(int filedes, char* buf){
    int r = read(filedes, buf, 1);
    if( r==-1 ){
        perror("FALHA\n");
        exit(-1);
    }
    return r;
}

int main(int argc, char const *argv[]){
    int pipe_r[argc];
    int pipe_w[argc];
    pipe_r[0] = argc-1;

    for (int i = 1; i < argc; ++i) {
        int batatas[2];
        pipe(batatas);
        pipe_r[i] = batatas[0];
        pipe_w[i] = batatas[1];

        int pid = fork();
        if( pid ) { // pai
            close(pipe_w[i]);
        }
        else { // filho
            close(pipe_r[i]);
            dup2(pipe_w[i], STDOUT_FILENO);
            close(pipe_w[i]);

            execlp(argv[i],argv[i],NULL);
            exit(-1);
        }
    }

    // char buf[1];
    char buf;
    while (pipe_r[0]!=0) {
        for (int i = 1; i < argc; i++) {
            if(pipe_r[i]!=-1){
                int l=10;
                while( l>0 && readchar(pipe_r[i], &buf)){
                    write(STDOUT_FILENO, &buf, 1);
                    if(buf=='\n') l--;
                }

                if(l>0) {
                    close(pipe_r[i]);
                    pipe_r[i] = -1;
                    pipe_r[0]--;
                }
            }
        }
    }
    exit(0);
}