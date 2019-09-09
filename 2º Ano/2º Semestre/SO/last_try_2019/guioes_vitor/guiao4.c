#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>
#include <string.h>

/*
int dup(int fd);
int dup2(int fd1, int fd2);
*/

//1
/*
int main(int argc, char** argv){

	printf("ola\n");

	int fd = open("/etc/passwd", O_CREAT | O_RDWR, 0666);
	dup2(fd,0);
	close(fd);

	int fd1 = open("saida.txt", O_CREAT | O_RDWR, 0666);
	dup2(fd1,1);
	printf("111\n");
	close(fd1);
	printf("222\n");

	int fd2 = open("erros.txt", O_CREAT | O_RDWR, 0666);
	dup2(fd2,2);
	close(fd2);

	return 0;
}
*/

//2
/*
int main(int argc, char** argv){

	int fd = open("/etc/passwd", O_CREAT | O_RDWR, 0666);
	dup2(fd,0);
	close(fd);

	int fd1 = open("saida.txt", O_CREAT | O_RDWR, 0666);
	dup2(fd1,1);
	close(fd1);

	int fd2 = open("erros.txt", O_CREAT | O_RDWR, 0666);
	dup2(fd2,2);
	close(fd2);

	pid_t pid=0;

	if((pid=fork())==0){
		printf("as saudades que eu ja tinha\nda minha alegre casinha.\n");
		printf("%d\n", getpid());
		printf("%d\n", getppid());
	} else {
		wait(NULL);
	}
	return 0;
}
*/

//3
/*
int main(int argc, char** argv){

	int fd = open("entrada.txt", O_CREAT | O_RDWR, 0666);
	dup2(fd,0);
	close(fd);

	int fd1 = open("saida.txt", O_CREAT | O_RDWR, 0666);
	dup2(fd1,1);
	close(fd1);

	int fd2 = open("erros.txt", O_CREAT | O_RDWR, 0666);
	dup2(fd2,2);
	close(fd2);

	execlp("wc", "wc", NULL);

	return 0;
}
*/

//4
/*
int main(int argc, char** argv){
    int i = 1;

    if(!strcmp(argv[i], "-i")){
        i++;
        int file_in = open(argv[i],O_RDONLY | O_CREAT,0666);
        dup2(file_in,0);
        close(file_in);
        i++;
    }
    if(!strcmp(argv[i], "-o")) {
        i++;
        int file_out = open(argv[i],O_WRONLY | O_CREAT,0666);
        dup2(file_out,1);
        close(file_out);
        i++;
    }
    execvp(argv[i],argv+i);

    return 0;

}
*/




















































