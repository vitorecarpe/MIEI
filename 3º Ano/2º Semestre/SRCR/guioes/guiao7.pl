%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais
 
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).
 
%consult('/Users/vitorpeixoto/Documents/Eng. Informática/3º Ano/2º Semestre/SRCR/Guioes/guiao7.pl').

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: definicoes iniciais
 
:- op( 900,xfy,'::' ).
:- dynamic '-'/1.

-jogo(Jogo,Arbitro,Ajudas):- nao(jogo(Jogo,Arbitro,Ajudas)),
								nao(excepcao(jogo(Jogo,Arbitro,Ajudas))).

%-----------------i)-----------------------------
jogo(1,'Almeida Antunes',500).

%-----------------ii)-----------------------------
jogo(2,'Baltazar Borges',ajudas).
excepcao( jogo( Jogo, Arbitro, Ajudas)) :- jogo(I,A,desconhecido).

nulo(desconhecido).

%-----------------iii)-----------------------------

excepcao(jogo(3,'Costa Carvalho',500).
excepcao(jogo(3,'Costa Carvalho',2500)).

%-----------------iv)-----------------------------
jogo(4,'Duarte Durão',X):- X>=250, X=<750.

%-----------------v)-----------------------------
jogo(5,'Edgar Esteves',interdito).
excepcao(jogo(I,A,C)):- jogo(I,A,interdito).
nulo(interdito).
+jogo(I,A,C)::+jogo( J,Ab,Aj ) :: ( solucoes( Ajudas, (jogo( J,Ab,Ajudas ), nao(nulo(Ajudas))), S), comprimento(S,N), N==0 ).

%-----------------vi)-----------------------------
jogo(6,'Francisco França',250).
excepcao(jogo(I,A,X)):- X>=5000.

%-----------------vii)-----------------------------
-jogo(7,'Guerra Godinho',2500).
jogo(7,'Guerra Godinho',desconhecido).
excepcao(jogo(I,A,X)) :- jogo( I,A,desconhecido).


%-----------------viii)-----------------------------
excepcao( jogo( 8,'Hélder Heitor',X )) :- cerca(1000,XSUP, XINF), 
                             X>XINF,
                             X<XSUP.
cerca(X,Sup,Inf):- Sup is X*1.25,
					Inf is X*0.75.
%-----------------ix)-----------------------------

 
excepcao( jogo( 9,'Ivo Inocêncio',X )) :- mproximo( 3000,CS,CI ),
                             X>=CI,
                             X<CS.
 
mproximo( X,Sup,Inf ) :- Sup is X*1.1,
                         Inf is X*0.9.

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

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a evolucao do conhecimento
 
evolucao( Termo ) :-
    solucoes( Invariante,+Termo::Invariante,Lista ),
    insercao( Termo ),
    teste( Lista ).
 
insercao( Termo ) :-
    assert( Termo ).
insercao( Termo ) :-
    retract( Termo ),!,fail.
 
teste( [] ).
teste( [A|B] ) :-
    A,
    teste( B ).
 
solucoes( X,Y,Z ) :-
    findall( X,Y,Z ).
 
comprimento( S,N ) :-
    length( S,N ).