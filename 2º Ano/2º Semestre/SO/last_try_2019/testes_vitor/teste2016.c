
#include <sys/types.h>
#include <sys/stat.h>

int main(int argc, char *argv[])
{
	int r,i;
	int pn = argc;
	int filhos[pn];
	int fd[pn][2];
	int time[pn][1];
	
	//start the pn process and redirect from stdout do pipe
	for (i=1; i<=pn; i++;)
	{
		r=fork();
		filhos[i] = getpid();
		pipe(fd[i]);
		if (r==0)
		{
			close(fd[i][0]);
			dup2(fd[i][1],1); //redirect stdout to pipe
			close(fd[i][1]);
			execlp(argv[i],argv[i],NULL);
		}
		else
		{
			//how to check every 3s to confirm its printing the "ok"?
			open(fd[i][0]);
			wait(status); //should be a while(1)?
			
			//check if its receiving the "ok" and kill the process if not
			kill(filhos[i],SIGUSR1);
			
			//print the time it took - how to now the time passed?
			printf("Process %s has terminated over %i seconds",argv[i],time[i]);
		}
	}
	
	//when all of them stops
	return 0;
}
