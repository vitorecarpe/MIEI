#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(int argc, char** argv) {
	int res;
	srandom(time(NULL));
	res = rand() % 3;
	return res;
}