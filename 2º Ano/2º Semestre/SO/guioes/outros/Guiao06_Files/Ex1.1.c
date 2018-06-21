//SO - AULA 8 - GUIÃO 6

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>

int main(){
    if(mkfifo("px",0666)<0){
        perror("oops!\n");
    }
    else mkfifo("px",0666)<0;
}
