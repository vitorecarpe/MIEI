#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(){
	char buffer[4096];
	int stringLength;
	int readStat;
	while(readStat = read(0,buffer,sizeof(buffer))){
		if(readStat < 0){
			printf("read error\n");
			exit(-1);
		}
		if(readStat == 0){
			printf("read ended\n");
			exit(0);
		}
		else{
			write(1,buffer,strlen(buffer));
			for(int i = 0; i < strlen(buffer); i++) //limpar o buffer
				buffer[i] = '\0';
		}
	}
	return 0;
	}
