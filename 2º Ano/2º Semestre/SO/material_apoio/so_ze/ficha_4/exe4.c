#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/wait.h>
#include <string.h>

int main(int argc, char **argv){

	int i = 1, r = 0;

	if(strcmp(argv[i], "-i") == 0){
		i++;
		if(i < argc){
					int fd = open(argv[i], O_RDONLY, 0600);
					dup2(fd, 0);
					close(fd);
				}
	}

	else{

		if(strcmp(argv[i], "-oa") == 0){
			i++;
			if(i < argc){
					int fd = open(argv[i], O_CREAT|O_WRONLY|O_APPEND, 0600);
					dup2(fd, 1);
					close(fd);
			}
		}

		else{
			if(strcmp(argv[i], "-ot") == 0){
				printf("ENTREI\n");
				i++;
				if(i < argc){
						int fd = open(argv[i], O_CREAT|O_WRONLY|O_TRUNC, 0600);
						dup2(fd, 1);
						close(fd);
				}
			}

			else{
				if(strcmp(argv[i], "-ea") == 0){
					i++;
					if(i < argc){
							int fd = open(argv[i], O_CREAT|O_WRONLY|O_APPEND, 0600);
							dup2(fd, 2);
							close(fd);
					}
				}

				else{
					if(strcmp(argv[i], "-et") == 0){
						i++;
						if(i < argc){
								int fd = open(argv[i], O_CREAT|O_WRONLY|O_TRUNC, 0600);
								dup2(fd, 2);
								close(fd);
						}
					}	

					else{
						i--;
						r = 1;
					}
				}
			}
		}
	}

	i++;

	if(i < argc){
		if(fork() == 0){

			execvp(argv[i], argv+i);
			perror(argv[i]);
			_exit(1);
		}

		else{

			wait(NULL);
		}
	}

	return 0;

}