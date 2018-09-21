%consult('/Users/vitorpeixoto/Documents/Eng. Informática/3º Ano/2º Semestre/SRCR/Guioes/guiao3.pl').

nao(Questao) :- Questao, !, fail.
nao(Questao).

%i
pertence(X,[X|_]).
pertence(X,[Y|T]) :- X\=Y, %for backtracking purposes...
			     	 pertence(X,T). 

n_pertence(X,L):- \+ pertence(X,L). % "\+" é o mesmo que "not"

%ii
comprimento([],0).
comprimento([_|T],R) :- comprimento(T,R1), R is R1+1.

%iii
diferentes([],0).
diferentes([H|T],R) :- pertence(H,T), diferentes(T,R). %se pertence, só avança para o proximo.
diferentes([H|T],R) :- n_pertence(H,T), diferentes(T,R1), R is R1+1. %se não pertence incrimenta no R.

quantos([],0).
quantos([X|L],N) :- pertence(X,L), quantos(L,N).
quantos([X|L],N) :- nao(pertence(X,L)), quantos(L,N1), N is N1+1.

%iv
% o X \= H resolve o problema do backtracking
%apaga1(_,[],[]). não é necessário
apaga1(X,[X|T],T).
apaga1(X,[H|T],[H|R]) :- X \= H, apaga1(X,T,R).

%v
apagaT(_,[],[]).
apagaT(X,[X|T],R) :- apagaT(X,T,R).
apagaT(X,[H|T],[H|R]) :- X \= H, apagaT(X,T,R).

%vi
adicionar(X,[],[X]).
adicionar(X,L,L) :- pertence(X,L).
adicionar(X,[H|T],[X,H|T]) :- X \= H.
%adicionar(X,L,[X|L]) :- n_pertence(X,L).
adicionar(X,L,L) :- pertence(X,L).
adicionar(X,L,[X|L]) :- n_pertence(X,L).

%vii
concat([],L,L).
concat([X|L1],L2,[X|L3]):- concat(L1,L2,L3).

%viii
inverter([],[]).
inverter([H|T],R) :-  inverter(T,R1), concat(R1,[H],R).

%copy pasta net
/*
reverse([],R,R).
reverse([H|T],X,R) :- reverse(T,[H|X],R).
*/