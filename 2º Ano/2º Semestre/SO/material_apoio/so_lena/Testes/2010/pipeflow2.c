#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>
#include <errno.h>

size_t flux = 0;

ssize_t readln(int fd, char *buf, size_t count) {

    ssize_t i = 0, br;

    while (i < count && (br = read(fd, buf + i, 1)) > 0 && buf[i++] != '\n');

    return (br != -1) ? i : br;
}

void a_handler(int sig) {

    printf("O fluxo foi de %d bytes.\n", (int)flux);
    flux = 0;
    alarm(1);
}

int main(int argc, char **argv) {

    if (argc != 3) {
        perror("Usage : ./pipeflow _program1_ _program2_\n");
        exit(-1);
    }
    int p1fd[2];
    pipe(p1fd);
    pid_t p1 = fork();
    if (p1 == -1) {
        perror("Erro no fork() para o primeiro programa.\n");
        exit(-1);
    }
    else if (!p1) {
        dup2(p1fd[1], 1); close(p1fd[1]); close(p1fd[0]);
        execlp(argv[1], argv[1], NULL);
    }
    else {
        close(p1fd[1]);
        int p2fd[2];
        pipe(p2fd);
        pid_t p2 = fork();
        if (p2 == -1) {
            perror("Erro no fork() para o segundo programa.\n");
            exit(-1);
        }
        else if (!p2) {
            dup2(p2fd[0], 0); close(p2fd[0]); close(p2fd[1]);
            execlp(argv[2], argv[2], NULL);
        }
        else {
            close(p2fd[0]);
            char buf[PIPE_BUF];
            ssize_t br;
            signal(SIGALRM, a_handler);
            alarm(1);
            while ((br = readln(p1fd[0], buf, PIPE_BUF)) > 0)
                if (br != -1) {
                    flux += br;
                    write(p2fd[1], buf, strlen(buf));
                }
                else {
                    perror("Erro no read.\n");
                    exit(-1);
                }
        }
    }

    exit(0);
}