#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

ssize_t readln(int fildes, void* buf, size_t nbyte) {
	int i = 0, r;
	char* buffer = buf;
	while (i < nbyte && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Falha na leitura!\n");
		exit(-1);
	}
	buffer[i] = '\0';
	return i;
}

void mynl(char* filename) {
	int fd, nl = 1, r;
	if (filename == NULL) fd = 0;
	else fd = open(filename, O_RDONLY, 0600);
	char line[PIPE_BUF];
	while ((r = readln(fd, line, PIPE_BUF)) > 0) {
		char linha[r + 5];
		int nr = sprintf(linha, "%d\t%s", nl, line);
		nl++;
		write(1, linha, nr);
	}
}


/* int sprintf(char *str, const char *format, ...);
 * int snprintf(char *str, size_t size, const char *format, ...);
 */

/* int sprintf(char *str, const char *format, ...)vi
 * str − This is the pointer to an array of char elements where the
 * resulting C string is stored.
 * format − This is the String that contains the text to be written
 * to buffer. It can optionally contain embedded format tags that are
 * replaced by the values specified in subsequent additional arguments
 * and formatted as requested. Format tags prototype: %[flags][width]
 * [.precision][length]specifier, as explained below −
 * 
 * If successful, the total number of characters written is returned
 * excluding the null-character appended at the end of the string,
 * otherwise a negative number is returned in case of failure.
 */

/* The functions snprintf() and  vsnprintf()  write  at  most  size
 * bytes (including the terminating null byte ('\0')) to str.
 */

int main(int argc, char** argv) {
	if (argc != 1 && argc != 2) {
		perror("Número de argumentos inválido!\n");
		exit(1);
	}
	mynl(argv[1]);
	exit(0);
}