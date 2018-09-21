abin : y.tab.o lex.yy.o
	gcc -o abin y.tab.o lex.yy.o -ll
	
y.tab.o : y.tab.c
	gcc -c y.tab.c

lex.yy.o : lex.yy.c y.tab.h
	gcc -c lex.yy.c

y.tab.c y.tab.h : abin.y
	yacc -d abin.y

lex.yy.c : abin.l
	flex abin.l