#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

int main(int argc, char *argv[]){


	int fd [2], s;

	pipe(fd);

	if (fork () == 0){

		close(fd[1]);
		char buf [100];

		int i = 0, n;

		while ((n = read(fd[0], buf+i, 5)) > 0){
			i += n;
		}

		close (fd[0]);
		printf("%s\n", buf);
		_exit(1);
	}

	else{
		close(fd[0]);

		char* b= "OLAsad";

		write (fd[1], b, strlen(b));

		b = "sadsmakodiasmndasoidmasdmaa";
		write (fd[1], b, strlen(b));
		
		close(fd[1]);

		wait(&s);

		if (WIFEXITED(s))
			printf("%d\n", WEXITSTATUS(s));
	}
	return 0;
}