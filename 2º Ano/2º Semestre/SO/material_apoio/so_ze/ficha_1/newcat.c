#include <unistd.h> 
#include <fcntl.h>
#include <stdlib.h>

int main (int argc, char** argv) {
	int n;
	int N = atoi(argv[1]);
	char *buffer = malloc(N+1);

	while (( n = read(0,buffer,N)) > 0) {
         write(1,buffer,n);	
   }

}