
#include <sys/types.h>
#include <sys/stat.h>
#include <limits.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
 
void cleanBuf(char* buf,int size){
    for(int i = 0; i < size; i++)
        buf[i] = '\0';
}
 
int main(int argc, char** argv){
 
    if(argc != 1){
        perror("Usage: ./wc only");
        exit(-1);
    }
 
    char buffer[PIPE_BUF];
    cleanBuf(buffer,PIPE_BUF);
 
    for(int i = 0; i < PIPE_BUF; i++) buffer[i] = '\0';
 
    while(read(0,buffer,sizeof(buffer)) > 0){
        int len = strlen(buffer);
        char out[100];
 
        sprintf(out,"%d",len);
        write(1,out,strlen(out));
        write(1,"\n", 1);
        cleanBuf(buffer,PIPE_BUF);
    }
 
    return 0;
}