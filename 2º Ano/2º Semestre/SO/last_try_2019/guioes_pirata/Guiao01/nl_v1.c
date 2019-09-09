/**
 * Funcionamento similar ao nl pré-definido da bash usando o readln. Repete linha a linha o standard input ou
 * o conteúdo de um ficheiro especificado como argumento. 
 * Cada linha é numerada sequencialmente. 
 * 
 * @author (Pirata) 
 * @version (2018.02)
 */

#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include "readln_v1.h"

/* Tamanho máximo do buffer, ou seja do comprimento da linha.
 * Se a linha tiver mais que este valor marca como se fossem duas linhas. */
#define MAX_SIZE 1024


/* Função com muitos extras, para minor bugs, falta limpar/reduzir */
int main(int argc,char** argv)
{
	char buffer[MAX_SIZE];
	char pretxt[22];
	int i = 0, k, j, n = 0, m = 0, fd;
	// Só aceita até um argumento além do comando
	if (argc <= 2) {
		// No caso de ser só o comando nl_v1 lê do STDIN
		if (argc == 1) {
			// enquanto conseguir ler alguma coisa. mesmo que seja a linha vazia
			while (n >= 0) {
				n = readln_v1(STDIN_FILENO,buffer,MAX_SIZE); // lê uma linha até tamanho MAX_SIZE
				if (n > 0) {
					buffer[n] = '\n';	// adiciona uma mudança de linha no fim da frase.
					i++; 				// acresce ao contador de linhas
					/* cria um texto pre-linha com as tabulaçoes e o numero da linha */
					pretxt[0] = '\t';
					sprintf(&pretxt[1],"%d",i);
					for(k = i, j = 0; k > 0; j++)
						k = k / 10;
					j++;
					pretxt[j] = '\t';
					pretxt[j+1] = EOF;

					m = write(STDOUT_FILENO,pretxt,j+1);		// escreve o pre-texto
					m = m + write(STDOUT_FILENO,buffer,n+1);	// escreve a linha
					// no caso de erro ao escrever
					if (m < 0) {
						perror("Houston, we lost writing power, MAYDAY");
						exit(-3);
					}
				}
			}
			exit(0);
		} else {
			// argc == 2 - no caso de um ficheiro ser passado como argumento
			if ((fd = open(argv[1],O_RDONLY)) > 0) {
				while (n >= 0) {
					n = readln_v1(fd,buffer,MAX_SIZE);
					if (n > 0) {
						buffer[n] = '\n';
						i++;
						pretxt[0] = '\t';
						sprintf(&pretxt[1],"%d",i);
						for (k = i, j = 0; k > 0; j++) {
							k = k / 10;
                        }
						j++;
						pretxt[j] = '\t';
						pretxt[j+1] = EOF;
						m = write(STDOUT_FILENO,pretxt,j+1);
						m = m + write(STDOUT_FILENO,buffer,n+1);
						if (m < 0) {
							perror("Houston, we lost writing power, MAYDAY");
							exit(-3);
						}
					} else {
						// Serve só para imprimir uma mudança de linha no caso de a linha estar vazia ou encontrar EOF.
						if (write(STDOUT_FILENO,"\n",1) < 0) {
							perror("Houston, we lost writing power, MAYDAY");
							exit(-3);
						}
					}
				}
				exit(0);
			} else {
				perror("Yargh,... Pirata is a boss but still can't guess what mistake your doing in this path to file");
				exit(-2);
			}

		}
	} else {
		perror("Hold up! One file alone is all I was asked to handle!");
		exit(-1);
	}
}
