SRC = const.c filter.c spawn.c window.c rdto.c controller.c
BIN = $(patsubst %.c,%,$(SRC))

all: $(BIN)

clean:
	rm -f $(BIN)
	find .  -maxdepth 1 -type p -delete
