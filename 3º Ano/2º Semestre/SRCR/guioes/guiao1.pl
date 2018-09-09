%consult('/Users/vitorpeixoto/Documents/Eng. Informática/3º Ano/2º Semestre/SRCR/Guioes/guiao1.pl').

filho(joao,jose).
filho(jose,manuel).
filho(carlos,jose).
pai(paulo,filipe).
pai(paulo,maria).
avo(antonio,nadia).
masculino(joao).
masculino(jose).
feminino(maria).
feminino(joana).

%xi
pai(X,Y) :- filho(Y,X).

%xii
avo(A,N) :- filho(N,X),pai(A,X).

%xiii
neto(X,Y) :- avo(Y,X).

%xiv
descendente(X,Y) :- filho(X,Y).
descendente(X,Y) :- filho(X,Z),descendente(Z,A).

