%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em logica estendida

%consult('/Users/vitorpeixoto/Documents/Eng. Informática/3º Ano/2º Semestre/SRCR/Guioes/guiao5.pl').

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

:-op(900,xfy,'::').
:- dynamic '-'/1.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

voo(X) :- ave(X), nao(excepcao(voo(X))).
-voo(X) :- mamifero(X), nao(excepcao(-voo(X))).
-voo(tweety).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

ave(pitigui).
ave(X) :- canario(X).
ave(X) :- periquito(X).

canario(piupiu).

mamifero(silvestre).
mamifero(X) :- cao(X).
mamifero(X) :- gato(X).

cao(boby).

ave(X) :- avestruz(X).
ave(X) :- pinguim(X).

avestruz(trux).
pinguim(pingu).

mamifero(X) :- morcego(X).

morcego(batememan).

excepcao(voo(X)) :- avestruz(X).
excepcao(voo(X)) :- pinguim(X).
excepcao(-voo(X)) :- morcego(X).

% Inserção de conhecimento
evolucao(Termo) :- solucoes(Invariante,+Termo::Invariante,Lista), 
    		    inserir(T),
    			teste(L).

inserir(T) :- assert(T).
inserir(T) :- retract(T),!,fail.

teste([]).
teste([R|LR]) :- R, teste(LR).

solucoes (X,Y,Z) :- 
			findall(X,Y,Z). 

% consult('ficha06.pl').

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado terminal: N -> {V,F,D}

-terminal(X) :- nodo(X), arco(X,Y).

terminal(X) :- nodo(X), nao(-terminal(X)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado demo: Questao,Resposta -> {V,F}

demo( Questao,verdadeiro ) :-
    Questao.
demo( Questao,falso ) :-
    -Questao.
demo( Questao,desconhecido ) :-
    nao( Questao ),
    nao( -Questao ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).
