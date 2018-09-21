calc : y.tab.o lex.yy.o
	gcc -o calc y.tab.o lex.yy.o -ll -lm
	
y.tab.o : y.tab.c
	gcc -c y.tab.c

lex.yy.o : lex.yy.c y.tab.h
	gcc -c lex.yy.c -lm

y.tab.c y.tab.h : calc.y
	yacc -d calc.y

lex.yy.c : calc.l
	flex calc.l

clear: 
	rm lex.yy.c
	rm lex.yy.o
	rm y.tab.c
	rm y.tab.h
	rm y.tab.o
	rm calc