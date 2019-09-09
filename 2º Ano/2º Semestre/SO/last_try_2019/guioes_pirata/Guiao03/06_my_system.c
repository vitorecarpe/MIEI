/**
 * Programa para testar a função mysystem() criada por nós.
 * Função mysystem definida nos ficheiros mysystem.h e mysystem.c
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <stdio.h>
#include "mysystem.h"

#define COMMAND "ls -la"

int main()
{
	int res;
	res = mysystem(COMMAND);
	printf("mysystem function returned value %d\n", res);
	return 0;
}
