CC = gcc
CFLAGS = -Wall -std=c11 -g -O2
DIRLIBS = -I/usr/include/libxml2
LIBS = -lxml2

program: hash.o load.o questoes.o
	$(CC) $(CFLAGS) $(DIRLIBS) program.c -o program hash.o load.o questoes.o $(LIBS)

hash.o:
	$(CC) $(CFLAGS) $(DIRLIBS) -c hash.c $(LIBS)
load.o:
	$(CC) $(CFLAGS) $(DIRLIBS) -c load.c $(LIBS)
questoes.o:
	$(CC) $(CFLAGS) $(DIRLIBS) -c questoes.c $(LIBS)

clean:
	rm program hash.o load.o questoes.o
