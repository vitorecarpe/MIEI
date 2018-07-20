#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>

void main(int argc, char** argv) {
	mkfifo("ordena", 0777);
	exit(0);
}