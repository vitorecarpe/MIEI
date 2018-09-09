notLatex: notLatex.l
	flex notLatex.l
	gcc -o notLatex -w lex.yy.c
	rm lex.yy.c

teste:notLatex
	./notLatex notLatex.test

show:notLatex
	cat ./latex/notLatex.test.tex
