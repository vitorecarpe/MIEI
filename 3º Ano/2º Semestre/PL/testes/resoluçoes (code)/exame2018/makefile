teste:compile
	./q2a

compile: q2a.l
	flex q2a.l
	gcc -o q2a -w lex.yy.c
	rm lex.yy.c
