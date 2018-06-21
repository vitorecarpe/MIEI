#include <string.h>

int parteString(char* args[], char *comando){
	int i = 0;

	args[i] = strtok(comando, " \t\n");

	while(args[i] != NULL){
		args[++i] = strtok(NULL, " \t\n");
	}

	return i;	
}