int* pid;
int l;
int i;

void termina() {
    int i;
    for (i = 0; i < l; i++)
        kill(pid[i], SIGKILL);
}

void main(int argc, char** argv){
    int status, nr, n = atoi(argv[1]);
    char buf [PIPE_BUF];
    int fd[n];
    l = n;
    pid = malloc(sizeof(int) * n);

    for (i = 0; i < n; i++) {
        int pd[2];
        pipe(pd);
        fd[i] = pd[1];
        int p = fork();

        if (p == 0) {
            signal(SIGCHLD, termina);
            int pid = fork();

            if (pid == 0) {
                close(pd[0]);
                dup2(pd[1], 1); close(pd[1]);
                execlp("./filtro", "./filtro", NULL);
                exit(-1);
            }
            else {
                close(pd[1]);
                dup2(pd[0], 0); close(pd[0]);
                execlp("./existe", "./existe", NULL);
                exit(-1);
            }
        } else {
            pid[i] = p;
        }
    }

    for (i = 0; i < n; i++) {
        printf("%d\n", pid[i]);
        prinf("%d")
        /*printf("estou aqui!\n");
        if((nr = readln(0, buf, PIPE_BUF)) > 0) {
            buf[nr] = '\0';
            printf("%s\n", buf);
            write(fd[i], buf, nr);
        }*/
    }

    /*for (i = 0; i < n; i++) {
        wait(&status);
    }*/
}