/** mysystem.h
 * Ficheiro header da função mysystem, representando uma versão simplificada da função system().
 * Ao contrário da função original não suporta redireccionamento ou composição/encadeamento
 * de programas executáveis.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#ifndef _SO_MYSYSTEM_
#define _SO_MYSYSTEM_

#include <stdlib.h>

int mysystem(const char* command);

#endif