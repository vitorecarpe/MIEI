CC = gcc
CFLAGS = -g -Wall -Wno-unused-result -O2
LIBS = -lm

all: controller const filter spawn window client

controller: stringProcessing.o controller.c
	$(CC) $(CFLAGS) -o controller stringProcessing.o controller.c $(LIBS)

const: stringProcessing.o const.c
	$(CC) $(CFLAGS) -o const stringProcessing.o const.c

filter: stringProcessing.o filter.c
	$(CC) $(CFLAGS) -o filter stringProcessing.o filter.c

spawn: stringProcessing.o spawn.c
	$(CC) $(CFLAGS) -o spawn stringProcessing.o spawn.c

window: stringProcessing.o window.c
	$(CC) $(CFLAGS) -o window stringProcessing.o window.c

stringProcessing.o: stringProcessing.c stringProcessing.h
	$(CC) $(CFLAGS) -c stringProcessing.c

client: stringProcessing.o client.c
	$(CC) $(CFLAGS) -o client stringProcessing.o client.c

clean:
	rm fifo* *.o inject_*

