/** Função de leitura de uma linha.
 * Funcionamento compatível com a chamada ao sistema read()
 * Nesta versão, lê apenas um carater de cada vez.
 * 
 * @author (Pirata) 
 * @version (2018.02)
 */

#ifndef _SO_READLN_V1_
#define _SO_READLN_V1_

#include <unistd.h>

ssize_t readln_v1(int fildes, void *buf, size_t nbyte);

#endif