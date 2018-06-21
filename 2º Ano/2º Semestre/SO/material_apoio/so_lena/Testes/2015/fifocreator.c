

void main(int argc, char** argv) {
	
	mkfifo(argv[1], 0600);
	exit(0);
}