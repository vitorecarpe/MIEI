#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;
	while (i < nbytes && (r = read(fildes, &buffer[i], 1))>0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("FALHA!\n");
		exit(-1);
	}
	buffer[i] = '\n';
	return i;
}

void create_captcha_file(const char *palavra){
    char pid[PIPE_BUF];
    sprintf(pid,"%d",getpid());

    int wr = open("server", O_WRONLY, 0600);
    write(wr,palavra,strlen(palavra));
    write(wr,pid,strlen(pid));

    char fileNew[strlen(palavra)+4];
    sprintf(fileNew,"%s.png",palavra);

    int rd = open(pid,O_RDONLY,0600);
    int file = open(fileNew,O_CREAT|O_WRONLY|O_TRUNC,0600);

    int r = read(rd,buffer,sizeof(buffer));
    write(file,bem,r);

    exit(0);
}

void main(int argc, char *argv[]){
    int rd = open("server", O_RDONLY, 0600);
    char png[16384], palavra[6], pid[16384];

    int r = read(rd, palavra, 6);
    r = read(rd, pid, 16384);
    pid[r] = '\0';

    int wr = open(pid, O_WRONLY, 0600);
    size_t size = captcha(palavra, png);
    write(wr, png, size);

    exit(0);
}