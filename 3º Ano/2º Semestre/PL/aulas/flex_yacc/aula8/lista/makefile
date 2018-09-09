lista : y.tab.o lex.yy.o
	gcc -o lista y.tab.o lex.yy.o -ll
	
y.tab.o : y.tab.c
	gcc -c y.tab.c

lex.yy.o : lex.yy.c y.tab.h
	gcc -c lex.yy.c

y.tab.c y.tab.h : lista.y
	yacc -d lista.y

lex.yy.c : lista.l
	flex lista.l