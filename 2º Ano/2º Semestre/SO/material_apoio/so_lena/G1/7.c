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

void create_buffer(int fildes, Buffer buffer, int nbyte) {
	buffer = malloc(sizeof(struct buffer_t));
	buffer -> fildes = fildes;
	buffer -> pos = 0;
	buffer -> r = 0;
	buffer -> buf = malloc(sizeof(nbyte));
}

int destroy_buffer(Buffer buffer) {
	free(buffer -> buf);
	free(buffer);
}

int readln(Buffer buffer, char* line) {
	int i;
	if (buffer -> pos == 0)
		buffer -> r = read(0, buffer -> buf, PIPE_BUF);
	else if (buffer -> pos == buffer -> r) {
		buffer -> r = read(0, buffer -> buf, PIPE_BUF);
		buffer -> pos = 0;
	}
	for (i = 0; buffer -> buf[buffer -> pos] != '\n'; (buffer -> pos)++, i++) {
		line[i] = buffer -> buf[buffer -> pos];
	}
	if (buffer -> r == -1) {
		perror("Erro na leitura!");
		_exit(-1);
	}
	buffer->pos++; // A próxima vez que vier ler começa na pos a seguir ao \n.
	line[i++] = '\n';
	line[i] = '\0';
	return (buffer -> r == 0) ? 0 : i;
}

int main(int argc, char** argv) {
	int r;
	Buffer buffer;
	create_buffer(0, buffer, PIPE_BUF);
	char* line = malloc(sizeof(PIPE_BUF));
	while ((r = readln(buffer, line)) > 0) {
		line[++r] = '\0';
		write(1, line, r);
	}
	exit(0);
}