#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <string.h>

int nPals;
int nChars;
int nLines;

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;

	while(i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');

	buffer[i] = '\0';
	return i;

}

int contaPal(char* line) {
	int pal = 0;
	int flag = 1, x;
	for (int i = 0; line[i] != '\0'; i++) {
		x = (line[i] <= ' ');
		if (flag && !x) pal++;
		flag = x;
	}
	return pal;
}

void processaFicheiro(char* fich) {
	int nPal = 0, nChar = 0, nLine = 0, r;
	int fd;
	if (fich != NULL) fd = open(fich, O_RDONLY, 0600);
	else fd = 0;
	char buffer[PIPE_BUF];

	while((r = readln(fd, buffer, PIPE_BUF)) > 0) {
		nChar += r;
		nPal += contaPal(buffer);
		nLine++;
	}

	nLines += nLine;
	nChars += nChar;
	nPals += nPal;

	if (fich != NULL)
		printf("%d\t%d\t%d %s\n", nLine, nPal, nChar, fich);
}

void main(int argc, char** argv) {
	if (argc == 1) {
		processaFicheiro(argv[1]);
	}
	int i;
	for (i = 1; i < argc; i++)
		processaFicheiro(argv[i]);

	printf("%d\t%d\t%d total\n", nLines, nPals, nChars);
}