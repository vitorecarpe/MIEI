#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>


int main () {
	if (mkfifo("fifo",0666) == -1){ // se já existir o fifo
		perror("Erro\n");
		exit(-1);
	}
}