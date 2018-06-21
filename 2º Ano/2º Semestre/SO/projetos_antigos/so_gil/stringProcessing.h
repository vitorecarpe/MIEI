/**@file stringProcessing.h
 *
 * \brief Módulo que trata de string
 *
 * Este módulo contem todas as funções auxiliares
 * sobre strings usadas nos outros ficheiros.
 */

#ifndef STRINGPROCESSING_H
#define STRINGPROCESSING_H

char** divideString(char x[], char* divider);
int strlenMulti(char **c);
char* strcatWithSpaces(char **c);
char* addCommandPrefix(char* cmd);
int numberOfDigits(int n);
char* fifoName(int id, char* io);
int readline(int fildes, char *buffer, int size);

#endif