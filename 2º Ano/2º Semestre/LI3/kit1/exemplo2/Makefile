CC = gcc
CFLAGS = -Wall


#dois targets diferentes por modulo

program_out: stdout.o program.o
	$(CC) $(CFLAGS) -o program stdout.o program.o

program_log: logger.o program.o
	$(CC) $(CFLAGS) -o program logger.o program.o


#modulo stdout

stdout.o:
	$(CC) $(CFLAGS) -c stdout.c

#modulo logger

logger.o:
	$(CC) $(CFLAGS) -c logger.c

program.o:
	$(CC) $(CFLAGS) -c program.c

clean:
	rm *.o log program
