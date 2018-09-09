%i soma: X,Y,Z {V,F}
soma(X,Y) :- Z is X+Y, write(Z).s

% res do prof
soma2(X,Y,R) :- R is X+Y.

%ii
soma3(X,Y,Z) :- K is X+Y+Z, write(K).
 
%iii
%somaL : L,R ->{V,F}
somaL([],0).
somaL([H|T],R) :- somaL(T,R1), R is R1+H.

%iv O-Operação, T-Resultado
% ope: X,O,Y,R -> {V,F}
ope(X,+,Y,R) :- R is X+Y.
ope(X,-,Y,R) :- R is X-Y.
ope(X,*,Y,R) :- R is X*Y.
ope(X,/,Y,R) :- Y \= 0, R is X/Y.

%v
mulL([],1).
mulL([H|T],R) :- mulL(T,R1), R is R1*H.

opeL([H|T],+,R) :- somaL([H|T],R).
opeL([H|T],*,R) :- mulL([H|T],R).

%solução do vitor
arit([],+,0).
arit([],*,1).
arit([H|T],+,X) :- arit(T,+,X1), X is X1+H.
arit([H|T],*,X) :- arit(T,*,X1), X is X1*H.

%vi
maior(X,Y) :- X >= Y -> write(X);
			  Y > X -> write(Y).

%solução do vitor
maior2(X,Y,Z) :- X>=Y, Z is X;
				 X<Y,  Z is Y.

%vii
maior3(X,Y,Z) :- X >= Y, X >= Z, write(X);
				 Y >= X, Y >= Z, write(Y);
				 Z >= X, Z >= Y, write(Z).

%viii
maiorL([]).
maiorL([M],M).
maiorL([H,M|T],Z) :- H >= M, maiorL([H|T],Z);
				     M >= H, maiorL([M|T],Z).

menorL([]).
menorL([M],M).
menorL([H,M|T],Z) :- H=<M, menorL([H|T],Z);
					 M=<H, menorL([M|T],Z).

%ix
menor(X,Y) :- X =< Y -> write(X);
			  Y < X -> write(Y).

%x
menor3(X,Y,Z) :- X =< Y, X =< Z, write(X);
				 Y =< X, Y =< Z, write(Y);
				 Z =< X, Z =< Y, write(Z).

%xi

menorL([]).
menorL([M]) :- write(M).
menorL([H,M|T]) :- H >= M, menorL([M|T]);
				   M >= H, menorL([H|T]).

%xii
tamanho([],0).
tamanho([_|T],R) :- tamanho(T,R1), R is R1+1.

media(L,R) :- somaL(L,R1), tamanho(L,R2), R is R1/R2.


%xiii
%net coppy pasta
insert(X,[H|T],[H|NT]):- X > H,insert(X,T,NT).
insert(X,[],[X]).

%o sicstus morre com esta
ordena([],R).
ordena([H|T],R) :-  ordena(R1,R), insert(H,T,R1).


%xiv

%exer extra
%nelem(N,L,X) – onde N é um número e X é o elemento da lista L na posição L

nelem(N,L,X):-nelem(N,1,L,X).
nelem(N,N,[X|_],X).
nelem(N,I,[_|R],X):- I1 is I+1, nelem(N,I1,R,X).

%xv

conj([],0).
conj([H|T],I) :- (H==[],conj(T,I1), I is I1+1); (H\=[],conj(T,I)).
%xvi


/*
Notas:
! ->  It is an instruction to Prolog not to back-track looking for other solutions.
"singleton variables" é só subsituir essa variável por _ visto que n se a está a usar


*/