#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <stdio.h>
#include <stdlib.h>

int main (int argc,char *argv[]){
	int status;
	argv[0] = "f3_03";
	if (!fork())
		execv("f3_03",argv);
	else wait(&status);
	exit(-1);//retornamos -1 porque se chegar ao exit,é porque algo correu mal, já que o exec passa para outro programa e termina este
}