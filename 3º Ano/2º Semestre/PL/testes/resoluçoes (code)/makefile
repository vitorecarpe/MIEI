teste:compile
	./ramdom

compile: ramdom.l
	flex ramdom.l
	gcc -o ramdom -w lex.yy.c
	rm lex.yy.c
