#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <limits.h>
#include <unistd.h>

int main(int argc, char** argv) {
	int o = open("fifo", O_WRONLY);
	if (o == -1) {
		perror("error opening fifo!");
		exit(-1);
	}
	char buf[PIPE_BUF]; int r;
	while((r = read(0, buf, PIPE_BUF)) > 0)
		write(o, buf, r);
	exit(0);
}