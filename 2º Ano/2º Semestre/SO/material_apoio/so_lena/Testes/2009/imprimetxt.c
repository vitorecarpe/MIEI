#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>


void main(int argc, char** argv) {
	execlp("cat", "cat", "fich.txt", NULL);
	exit(-1);
}