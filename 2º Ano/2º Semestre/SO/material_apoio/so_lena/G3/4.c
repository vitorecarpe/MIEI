#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main(int argc, char** argv) {
	argv[0] = "./3";
	execv("./3", argv);
	exit(-1);
}