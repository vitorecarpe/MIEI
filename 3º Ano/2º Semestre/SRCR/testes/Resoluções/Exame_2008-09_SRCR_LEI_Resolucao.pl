% COMPILA MAS NAO TESTEI, Á PARTIDA FUNCIONA TUDO

% aluno(numero,aluno,curso).
% teorica(numero,nota).
% pratica(numero,nota).

:- op( 900,xfy,'::').
:- dynamic identificacao/3.
:- dynamic teorica/2.
:- dynamic pratica/2.

% ALUNOS

aluno(12333,joaocosta,lei).
aluno(13444,anarocha,desconhecido).
aluno(18999,joaomota,lcc).
aluno(15000,joaosilva,lei).
aluno(1,naopermitido,lesi).

-aluno(N,A,C):- 
	nao(aluno(N,A,C)),
	nao(excepcao(aluno(N,A,C))).

excepcao(aluno(N,A,C)):- aluno(N,A,desconhecido).

excepcao(aluno(14000,paulojorge,lei)).
excepcao(aluno(14000,pedrojorge,lei)).

excepcao(aluno(15233,isabelmendes,lei)).
excepcao(aluno(15233,isabelmendes,lcc)).

excepcao(aluno(N,A,C)):- aluno(N,naopermitido,C).

nulo(naopermitido).

+aluno(N,A,C) :: (findall( D , (aluno(N,A,C) , nao(nulo(A))) , L) , length(L,N) , N==0).

% NOTA TEORICA

teorica(13444,12).
teorica(18900,10).
teorica(1,20).

-teorica(N,T):-
	nao(teorica(N,T)),
	nao(excepcao(teorica(N,T))).

excepcao(teorica(15000,12)).
excepcao(teorica(15000,14)).

% NOTA PRATICA

pratica(18900,desconhecido).
pratica(15000,alta).
pratica(1,20).

-pratica(N,T):-
	nao(pratica(N,T)),
	nao(excepcao(pratica(N,T))).

excepcao(pratica(N,T)):- pratica(N,desconhecido).
excepcao(pratica(N,T)):- pratica(N,alta).

excepcao(pratica(13444,T)):- T>=14,T=<16.


% ALINEA D

aluno(13130,josecabral,desconhecido). % para testar apenas, nao é necessario
-aluno(13130,josecabral,lcc).

% ALINEA E

+pratica(N,T):: (T>=0 , T=<20).
+teorica(N,T):: (T>=0 , T=<20).


% ALINEA F

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


