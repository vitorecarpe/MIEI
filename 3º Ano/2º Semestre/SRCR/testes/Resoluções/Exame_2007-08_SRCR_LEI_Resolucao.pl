% escritor(escritor,livro).
% editora(livro,editora).
% lancamento(livro,data(D,M,A)).

:- op( 900,xfy,'::').
:-dynamic escritor/2.
:-dynamic editora/2.
:-dynamic lancamento/2.

% ESCRITORES
escritor(silva,bancadaNascente).
escritor(maria,bancadaNascente).
escritor(quimZe,prolongamento).
escritor(mario,livreEmDirecto).
escritor(maria,livreEmDirecto).
escritor(sousa,amarElo).
escritor(pereira,encarnado).
escritor(alguem,oEncantadorDeBaleias).

excepcao(escritor(francisco,ontem)).
excepcao(escritor(francisco,amanha)).
excepcao(escritor(E,L)):- escritor(alguem,L).

-escritor(E,L):-
	nao(escritor(E,L)),
	nao(excepcao(escritor(E,L))).

	
% EDITORAS
editora(tempoExtra,edicaoMeia).
editora(prolongamento,caraVela).
editora(livreEmDirecto,editorium).
editora(amarElo,textorius).
editora(oEncantadorDeBaleias,antigus).

-editora(L,E):-
	nao(editora(L,E)),
	nao(excepcao(editora(L,E))).

excepcao(editora(encarnado,librarium)).
excepcao(editora(encarnado,notorius)).


% LANÇAMENTOS
% lancamento(,data(,,)).

lancamento(tempoExtra,data(1,3,2008)).
lancamento(prolongamento,data(1,3,2008)).
lancamento(livreEmDirecto,data(desconhecido,4,2008)).
lancamento(amarElo,adiado).

-lancamento(L,data(D,M,A)):-
	nao(lancamento(L,data(D,M,A))),
	nao(excepcao(lancamento(L,data(D,M,A)))).

excepcao(lancamento(L,data(D,M,A))):-
	lancamento(L,data(desconhecido,M,A)).
excepcao(lancamento(L,data(D,M,A))):-
	lancamento(L,adiado).
	
excepcao(lancamento(encarnado,data(1,5,2008))).
excepcao(lancamento(encarnado,data(1,6,2008))).
excepcao(lancamento(oEncantadorDeBaleias,data(X,12,2008))):- X>=25, X=<31.


% INVARIANTES

% alinea d
+escritor(E,L) :: (L\==violencia, L\==oreo, L\==porno, L\==barbosa).
+editora(L,E) :: (L\==violencia, L\==oreo, L\==porno, L\==barbosa).
+lancamento(L,D) :: (L\==violencia, L\==oreo, L\==porno, L\==barbosa).

% alinea e
-escritor(E,L) :: (findall(L,lancamento(L,D),LI), length(LI,N), N==0).
-editora(L,E) :: (findall(L,lancamento(L,D),LI), length(LI,N), N==0).


% SUPOSTAMENTE ISTO É A ALINEA F)

demo(Q,verdadeiro):- Q.
demo(Q,falso):- -Q.
demo(Q,desconhecido):- nao(Q),nao(-Q).

nao(X):- X,!,fail.
nao(X).

teste([]).
teste([H|T]):- H, teste(T).

inserir(X):- assert(X).
inserir(X):- retract(X),!,fail.

remover(X):-retract(X).
remover(X):-assert(X),!,fail.

evolucao(X):- 
	findall(I,+X::I,L), 
	inserir(X),
	teste(L).
	
retorcesso(X):-
	findall(I,-X::I,L),
	remover(X),
	teste(L).
