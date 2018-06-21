#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

typedef struct buffer_t {
	int fildes;
	char* buf;
	int pos;
	int r;
}* Buffer;


Buffer create_buffer(int fildes, int nbyte) {
	Buffer buffer = malloc(sizeof(struct buffer_t));
	buffer -> fildes = fildes;
	buffer -> pos = 0;
	buffer -> r = 0;
	buffer -> buf = malloc(sizeof(nbyte));
	return buffer;
}

int destroy_buffer(Buffer buffer) {
	free(buffer -> buf);
	free(buffer);
}

int readln(Buffer buffer, char* line) {
	int i;
	if (buffer -> pos == 0)
		buffer -> r = read(buffer -> fildes, buffer -> buf, PIPE_BUF);
	else if (buffer -> pos == buffer -> r) {
		buffer -> r = read(buffer -> fildes, buffer -> buf, PIPE_BUF);
		buffer -> pos = 0;
	}

	for (i = 0; buffer -> buf[buffer -> pos] != '\n'; (buffer -> pos)++, i++) {
		line[i] = buffer -> buf[buffer -> pos];
	}
	if (buffer -> r == -1) {
		perror("Erro na leitura!");
		_exit(-1);
	}
	buffer -> pos++; // A próxima vez que vier ler começa na pos a seguir ao \n.
	line[i++] = '\n';
	line[i] = '\0';
	return (buffer -> r == 0) ? 0 : i;
}

void mynl(char* filename) {
	int fd, nl = 1, r;
	if (filename == NULL) fd = 0;
	else fd = open(filename, O_RDONLY, 0600);
	Buffer buffer = create_buffer(fd, PIPE_BUF);
	char* line = malloc(sizeof(PIPE_BUF));
	while ((r = readln(buffer, line)) > 0) {
		char linha[r + 5];
		line[++r] = '\0';
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