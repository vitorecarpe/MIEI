#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h>

int pid; /* Variavel que guarda o pid do filho */

/* Pode receber SIGCHLD ou SIGALRM
 * Se receber o 1o, significa que o filho morreu antes de atingir um dos limites
    e queremos que simplesmente saia
 * Se receber o 2o, significa que atingiu o limite de tempo. Antes de sair, temos de terminar o filho
 */
void sig_handl(int s){
    if(s == SIGALRM)
        kill(SIGINT, pid);

    exit(0);
}

int main( int argc, char *argv[]){
    int timeLimit = atoi( argv[1] );
    int size = atoi( argv[2] );

    int fd[2];
    pipe(fd);

    pid = fork();

    if(!pid){
        close(fd[0]);
        dup2(fd[1], 1); /* Output do filho é enviado para o pipe anonimo */
        execvp(argv[3], argv + 3);
    }

    else{
        /* Os sinais e o alarme so sao criados no else porque se for antes do fork,
            o filho vai herda-los e vai reagir ao alarme */
        signal(SIGALRM, sig_handl);
        signal(SIGCHLD, sig_handl);
        alarm(timeLimit);

        close(fd[1]);

        int count = 0;
        char buf;

        while(read(fd[0], &buf, 1) > 0) {   /* Pai le do pipe */
            count++;                        /* Conta o nr de caracteres que o filho tem como output */
            write(1, &buf, 1);              /* Escreve para o user ver */
            if(count == size){              /* Testa o limite de carateres */
                kill(SIGINT, pid);
                exit(0);
            }
        }

        close(fd[0]);
    }

    return 0;
}
