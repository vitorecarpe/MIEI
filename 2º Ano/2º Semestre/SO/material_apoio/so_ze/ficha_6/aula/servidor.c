int main(){
	int fd;
	char buf[100];

	mkfifo("fifo", 0644);

	while(1){
		fd = open("fifo", O_RDONLY);
		printf("Estou à espera de escritores\n");

		while((n = read(fd, buf, 100)) > 0){

			write(0, buf, n);
		}
		printf("Fiquei sem escritores\n");
		close(fd);
	}
	return 0;
}