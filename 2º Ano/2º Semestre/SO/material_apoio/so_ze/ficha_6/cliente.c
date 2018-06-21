#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>

int main(int argc, char **argv){
	
	int i;

	int fl = open("servidor1", O_WRONLY);

	char *buf[1024];

	for(i = 1; i < argc; i++){
		write(fl, argv[i], strlen(argv[i]));
		write(fl, "\n", 1);
	}

	return 0;
}