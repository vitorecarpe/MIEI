#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>

int main (){
	mkfifo("ordenar",0666);
	int o = open("ordenar",O_WRONLY | O_TRUNC);
	if (o == -1) perror("Erro");
	char buf [PIPE_BUF];
	int n = sprintf(buf,"f5_01.c\n");
	write(o,buf,n);
	n = sprintf(buf,"atrasa1.c\n");
	write(o,buf,n);
	//close(o);
	return 0;
}