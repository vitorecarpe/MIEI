#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

//1
int main(int argc, char** argv){
	char a = 'a';
	int s=0;
	int file = open(argv[1], O_CREAT | O_RDWR);

	for(int i=0; i<10000000; i++){
		s=write(file, &a, 1);
	}

	return 0;

}


//2
int main(int argc, char** argv){
	int n;
	char buf[1024];

	while((n=read(0,buf,1))>0){
		write(1,buf,1);
	}

	return 0;
}

//3
int main(int argc, char** argv){
	int n, i;
	char buf[1024];
	int size = atoi(argv[1]);


	while((n=read(0,buf,size))>0){
		write(1,buf,n);
	}



	return 0;
}

//5
ssize_t readln(int fildes, void *buf, size_t nbyte){
	int i, n=0;
	char c;
	char *buffer = (char *)buf;
	while(i<nbyte && (n=read(fildes,&c,1))>0 && c!='\n'){
		buffer[i++]=c;
	}
	return i;
}

int main(int argc, char** argv){
	char buf[1024];
	int fd = open(argv[1],O_RDWR, 0666);
	int rl = readln(fd,buf,1024);
	write(1,buf,rl);
	return 0;
}

//6
ssize_t readln(int fildes, void *buf, size_t nbyte){
	int n=0;
	ssize_t i=0;
	char c;
	char *buffer = (char *)buf;
	while(i<nbyte && (n=read(fildes,&c,1))>0 && c!='\n')
		buffer[i++]=c;

	(i<nbyte) ? (buffer[i] = '\0') : (buffer[i-1]='\0');

	return i;
}

char *concatreadln(char *buf, int n, int i){
	char *string = malloc(sizeof(char) * (n+10));
	sprintf(string, "\t%d   %s\n",i, buf);
	return string;
}

int main(int argc, char** argv){
	char buf[1024];
	char *string;
	int fd = open(argv[1],O_RDWR, 0666);
	int i=1;
	//for(int i=0; (int n=read(fd,buf,1024))>0 )

	int n;
	while( (n = readln(fd,buf,1024)) > 0){
		string = concatreadln(buf, n, i++);
		write(1, string, strlen(string));
	}
	return 0;
}

