%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariantes

%consult('/Users/vitorpeixoto/Documents/Eng. Informática/3º Ano/2º Semestre/SRCR/Guioes/guiao4.pl').

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: definicoes iniciais

:- op( 900,xfy,'::' ).
:- dynamic filho/2.
:- dynamic pai/2.
:- dynamic neto/2.
:- dynamic avo/2.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado filho: Filho,Pai -> {V,F,D}

filho( joao,jose ).
filho(joao,ana).
filho( jose,manuel ).
filho( carlos,jose ).
pai(kiko,carolina).
pai(mariana,helena).
pai(vitor,helena).
pai(vitor,carvalho).
neto(raul,quim).
neto(matias,tobias).
neto(matias,sara).
neto(matias,clara).
avo(tiago,alex).

%idade
idade(joao,13).
idade(jose,44).
idade(manuel,71).
idade(carlos,7).

% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

/* Invariante Estrutural:  nao permitir a insercao de conhecimento repetido
problemas de invariantes é correr os invariantes todos
para isso n acontecer pomos os invariantes com etiquetas 
o + é porque é um invariante de inserção, se for - é de remoção
depois vem a identificação do predicado
:: para servir de separador
*/

comprimento(S,N) :- length(S,N).

% Extensão do predicado solucoes: X,Y,Z -> {V,F}

solucoes(X,Y,Z) :-
    findall(X,Y,Z).

%i
+filho(F,Ps) :: (solucoes(Ps,(filho(F,Ps)),S ),
                  comprimento( S,N ), 
				  N == 1
                  ).
%ii
+pai(F,Ps) :: (solucoes(Ps,(pai(F,Ps)),S ),
                  comprimento( S,N ), 
				  N == 1
                  ).
%iii
+neto(F,Ps) :: (solucoes(Ps,(neto(F,Ps)),S ),
                  comprimento( S,N ), 
				  N == 1
                  ).
%iv
+avo(F,Ps) :: (solucoes(Ps,(avo(F,Ps)),S ),
                  comprimento( S,N ), 
				  N == 1
                  ).

% vi
+filho(F,P) :: ( solucoes(Ps,filho(F,Ps),S),
				   comprimento(S,N),
				   N =<2
                  ).
%vii
+pai(P,F) :: (solucoes(Ps,(pai(Ps,F)),S ),
                  comprimento( S,N ), 
				  N =< 2
                  ).
%viii
+neto(Ne,A) :: (solucoes(As,(neto(Ne,As)),S ),
                  comprimento( S,N ), 
				  N =< 4
                  ).

%ix
+avo(A,Ne) :: (solucoes(As,(avo(As,Ne)),S ),
                  comprimento( S,N ), 
				  N =< 4
                  ).


% Extensão do predicado que permite a evolucao do conhecimento
% se tivermos váris inv para uma cena temos d eir buscar todos os invariantes
teste([]).
teste([R|L]) :- R, teste(L).

insere(P) :- assert(P).
insere(P) :- retract(P),!,fail. %assim nunca há efeito secundário do retract

evolucao(Termo) :- solucoes(Inv,+Termo::Inv,S),
					 %assert(Termo), em vez de usar o assert podemos usar um insere
					 insere(Termo),
					 teste(S).
					 % retract funciona mas é perigosa

%evolucao(filho(carlos,paulo)).
%yes (agora fica a aparecer este exemplo no listing)
%evolucao(filho(carlos,jose))
%no




remove(P) :- retract(P).
remove(P) :- assert(P),!,fail.

inevolucao( Termo ) :- solucoes(Inv,-Termo::Inv,S),
					 remove(Termo),
					 teste(S).

%invariante em que n se pode remover se acontecer algo
/*
-filho(F,P) ::( solucoes(F,idade(F,X),S),
			   comprimento(S,N),
			   N==0
			   ).
*/