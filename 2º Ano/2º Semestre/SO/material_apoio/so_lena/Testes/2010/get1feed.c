#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>

void main(int argc, char** argv) {
	char* fich = argv[1];
	execlp("cat", "cat", fich, NULL);
	exit(-1);
}