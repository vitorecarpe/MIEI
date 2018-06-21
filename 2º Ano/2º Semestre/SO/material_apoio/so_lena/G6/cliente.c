#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <limits.h>
#include <string.h>


int main(int argc, char** argv) {
	int o = open("fifo", O_WRONLY);
	if (o == -1) {
		perror("error opening fifo!");
		exit(-1);
	}
	char buf[PIPE_BUF]; int r;
	for (int i = 1; i < argc; i++) {
		int l = strlen(argv[i]);
		write(o, argv[i], l);
		write(o, " ", 1);
	}
	exit(0);
}