# executa
exec : teste png

#########################
# gera o png e svg apartir do dot
png : graph/graph.dot
	dot -Tpng -o graph/graphDOT.png graph/graph.dot
	dot -Tsvg -o graph/graphDOT.svg graph/graph.dot
	dot -Kfdp -Tpng -o graph/graphFDP.png graph/graph.dot
	dot -Kfdp -Tsvg -o graph/graphFDP.svg graph/graph.dot

# gera o dot apartir do .test file
teste : museu original.test
	./museu < original.test > graph/graph.dot
	make clear

#########################
# gera o executavel
museu : y.tab.o lex.yy.o
	gcc -o museu y.tab.o lex.yy.o -ll -lm
	
y.tab.o : y.tab.c
	gcc -c y.tab.c

lex.yy.o : lex.yy.c y.tab.h
	gcc -c lex.yy.c -lm

########################
y.tab.c y.tab.h : museu.y
	yacc -d museu.y

lex.yy.c : museu.l
	flex museu.l

########################
# limpa ficheiros auxiliares
clear: 
	rm lex.yy.c lex.yy.o
	rm y.tab.h y.tab.c y.tab.o
