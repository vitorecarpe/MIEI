//SO - AULA 6 - GUIÃO 4

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>


/*
Esta matéria é essencial para sabermos trabalhar com pipes do
Guião 5.
O tubo(pipe) tem duas extremidades, uma onde escrevemos e uma
onde lemos.

-> 0 - std input (teclado por omissão)
-> 1 - std output (ecrã por omissão)
-> 2 - std error
-> 3 - 1ª posição livre
*/

//Função dup2 (Duplicate To - Duplica para)
int dup2(int fd1, int fd2);

//EXERCICIO 1
int main(){
  int r, w, e;
  char c;
  r = open("Guiao04_Files/passwd.txt", O_RDONLY | O_APPEND, 0666);
  w = open("Guiao04_Files/saida.txt", O_CREAT | O_WRONLY, 0666);
  e = open("Guiao04_Files/erros.txt", O_CREAT | O_WRONLY, 0666);
  printf("%d\n", r); // Imprime 3
  printf("%d\n", w); // Imprime 4
  printf("%d\n", e); // Imprime 5

  dup2(r,0);
  dup2(w,1);
  dup2(e,2);

  while(read(0, &c, 1)>0){
    write(1,&c,1);
    write(2,&c,1);

  }
}
