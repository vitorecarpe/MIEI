#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(int argc, char* argv[]){
	int i;
	for(i=1;i<argc;i++) printf("%s\n",argv[i]);
}