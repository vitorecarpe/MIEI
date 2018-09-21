compile: analisador.l
	flex analisador.l
	gcc -o analisador -ll lex.yy.c

teste: compile
	./analisador testejson.json

install: analisador
	cp analisador usr/local/bin

uninstall:
	sudo rm /usr/local/bin/analisador