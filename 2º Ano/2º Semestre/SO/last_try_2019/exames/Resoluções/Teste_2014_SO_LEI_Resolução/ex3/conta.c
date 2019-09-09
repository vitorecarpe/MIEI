#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h>


/** Simples resolucao de uma questao do teste de SO.
  * Nao e a melhor solucao nem tem qualquer tipo de controlo de erros.
  */

int finder(char *word, char *file) {
    int total, n;
    int pd[2];
    char buf[1024];

    total = 0;

    pipe(pd);

    if (!fork()) {
        /* Definir a saida do pipe como sendo o stdout */
        close(pd[0]);
        dup2(pd[1], 1);
        close(pd[1]);

        /* Substituir grep por encontra */
        execlp("grep", "grep", word, file, NULL);
    }
    else {
        close(pd[1]);

        /* Ler o resultado do exec da funcao encontra executada no processo filho */
        while ( (n = read(pd[0], buf, 1024)) ) {
            while (n--)
                if (buf[n] == '\n') total += 1;
        }

        return total;
    }
}

int main(int argc, char* argv[]) {
    int i, total;
    int status;
    char *word;
    char number[16];

    word = argv[1];
    total = 0;

    for (i = 2; i < argc; i++) {
        /* Fazer filhinhos */
        if (!fork()) {
            /* Provavelmente deveria ser feito com pipes */
            _exit(finder(word, argv[i]));
        }
    }

    for (i = 2; i < argc; i++) {
        /* Esperar pelos filhos todos */
        wait(&status);
        total += WEXITSTATUS(status);
    }

    /* Para quem nao gostar do sprintf, fazer um itoa tambÃ©m Ã© simples */
    sprintf(number, "%d\n", total);

    write(1, number, strlen(number));

    return 0;
}