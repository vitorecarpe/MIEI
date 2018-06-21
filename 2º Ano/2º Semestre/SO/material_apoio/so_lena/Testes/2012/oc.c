#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>


ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while(i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');

	buffer[i] = '\n';
	return i;
}


void main(int argc, char** argv) {
	int ocr = 0, r;
	char buffer[PIPE_BUF];
	char* term = argv[1];

	while((r = readln(0, buffer, PIPE_BUF)) > 0) {
		buffer[r] = '\0';
		if (strstr(buffer, term)) ocr++;
	}

	printf("%d\n", ocr);
	exit(0);
}