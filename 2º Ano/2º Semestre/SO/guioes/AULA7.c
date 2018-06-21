//SO - AULA 7 - GUIÃO 5

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>


/*
Matéria mais importante da disciplina. Conceito que está por trás de todas as
ligações entre computadores.
Posição 0 é de onde leio.
Posição 1 é de onde escrevo.

         PIPE
          V
 1 -> ########## -> 0

Não ha dois processos a escrever para o pipe, nem há dois processos a ler do pipe.

*/


//EXERCICIO 1
/*int main(void){
    int     fd[2], n;
    char    string[] = "OLA MUNDO\n";
    char    buffer[256];

    if(pipe(fd)<0){
          exit(-1);
    }

    if(!fork()){
        close(fd[1]);
        n = read(fd[0], buffer, 256);
        write(1, buffer, n);
        exit(0);
    }

    else{
        close(fd[0]);
        write(fd[1], string, sizeof(string));
        close(fd[1]);
        wait(NULL);
    }
    return 0;
}
*/

//EXERCICIO 2
/*int main(){
    int     fd[2], n=1;
    char    string[] = "OLA MUNDO\n";
    char    buffer[256];

    pipe(fd);

    if(fork()==0){
        close (fd[1]);

        while(n>0){
            n = read(fd[0], buffer, 256);
            write(1,buffer,n);

        }
        exit(0);
    }

    else{
        close(fd[0]);
        write(fd[1], string, sizeof(string));
        close(fd[1]);
        wait(NULL);
    }
    return 0;
}
*/

//EXERCICIO 3
int main(){
    int     fd[2], n=1;
    char    buffer[256];

    if(pipe(fd)<0) exit(-1);

    if(!fork()){
        close(fd[1]);
        dup2(fd[0],0);
        execlp("wc","wc",NULL);
        exit(-1);
    }

    else{
        close(fd[0]);
        while(n=read(0,buffer,256)>0){
            write(fd[1], buffer, n);
        }
        close(fd[1]);
        wait(NULL);
    }

    return 0;
}
