#include <unistd.h> 
#include <fcntl.h>


int main (int argc, char** argv) {
	int i,fd = open(argv[1], O_CREAT|O_WRONLY, 0644);

	for(i=0;i<10*1024*1024;i++) {
		write(fd,"x",1);
	}

	close(fd);
}