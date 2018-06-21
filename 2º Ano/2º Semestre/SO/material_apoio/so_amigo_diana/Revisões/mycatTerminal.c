#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

/* Usar read() e write()
 * criar um buffer com tamanho de 4096 bytes onde será escrito e lido o input do terminal
 * no final de cada leitura, preencher todos os nodos com '\0' para não haver input antigo a ser lido. Por exemplo: sem isto, se primeiro escrever "amigo" e depois "ola" , segundo output vai ser "ola(\n)o" pois apenas para quando lê o carater '\0'
 * read(int fileDescriptor, ID do ficheiro a ler. 0 => leitura do terminal, 1 => escrita no terminal
 void* buffer,	buffer de onde é lido
 size_t count) 	nr de bytes para ler
 */

int main(){

        char buffer[4096];
        while(read(0,buffer,sizeof(buffer))>0){
                int tam = strlen(buffer);
                write(1,buffer,tam);
                for(int i = 0; i < tam;i++)
                        buffer[i] = '\0';
        }
		return 0;
}
