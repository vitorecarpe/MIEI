//SO - AULA 8 - GUIÃO 6

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>

int mkfifo(const char* pathname, mode_t mode);

int main()
{
    char b[256];
    int n, fd;

    if(open("px",O_RDONLY)<0){
        perror("oops!\n");
    }

    while(n=read(fd, b, 256)>0){
        write(1,b,n);
    }

    return 0;
}
