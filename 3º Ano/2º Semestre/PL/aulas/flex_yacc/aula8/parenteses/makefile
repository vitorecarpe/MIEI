compile:
		flex parent.l
		yacc -d parent.y
		gcc -c lex.yy.c
		gcc -c y.tab.c
		gcc -o parent y.tab.o lex.yy.o -ll