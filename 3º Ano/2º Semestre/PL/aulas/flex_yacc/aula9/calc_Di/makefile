programa:
	@flex calc.l
	@yacc -d calc.y
	@gcc -o calc y.tab.c -ll
	@./calc

clean: 
	@rm lex.yy.c y.tab.c y.tab.h calc