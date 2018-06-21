#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include "readln.h"
#include "auxiliar_funcoes.h"

int main(int argc, char** argv){
	int fd = 0, n;
	char buffer[1024];
	while((n = readln(fd, buffer, 1000)) > 0){

		n = acrescenta(buffer, n, argv[1]);
		write(1, buffer, n);
	}
}