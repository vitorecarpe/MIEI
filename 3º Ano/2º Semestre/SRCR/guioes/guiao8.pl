%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais
 
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).
 
%consult('/Users/vitorpeixoto/Documents/Eng. Informática/3º Ano/2º Semestre/SRCR/Guioes/guiao7.pl').

excepcao(pais(F,P,M)):- filho(desconhecido,P,M).
excepcao(pais(F,P,M)):- filho(F,desconhecido,M).
excepcao(pais(F,P,M)):- filho(F,P,desconhecido).
excepcao(nasc(N,data(dia,mes,ano))):- nasc(N,data(desconhecido,mes,ano)).
excepcao(nasc(N,data(dia,mes,ano))):- nasc(N,data(dia,desconhecido,ano)).
excepcao(nasc(N,data(dia,mes,ano))):- nsc(N,data(dia,mes,desconhecido)).
excepcao(nasc(N,D)):- nasc(N,interdito).
nulo(interdito).

%---------------------------------------------------
% SICStus PROLOG: definicoes iniciais
 
:- op( 900,xfy,'::' ).
:- dynamic '-'/1.

-jogo(Jogo,Arbitro,Ajudas):- nao(jogo(Jogo,Arbitro,Ajudas)),
								nao(excepcao(jogo(Jogo,Arbitro,Ajudas))).

%-----------------i)--------------------------------
pais(ana,abel,alice).
nasc(ana,data(1,1,2010)).

%-----------------ii)-------------------------------
pais(anibal,antonio,alberta).
nasc(anibal,data(2,1,2010)).

%-----------------iii)------------------------------
pais(berta,bras,belem).
pais(berto,bras,belem).
nasc(berta,data(2,2,2010)).
nasc(berto,data(2,2,2010)).

%-----------------iv)-------------------------------
nasc(catia,data(3,3,2010)).

%-----------------v)--------------------------------
pais(crispim,desconhecido,catia).
excepcao(pais(crispim,celsio,catia)).
excepcao(pais(crispim,caio,catia)).

%-----------------vi)-------------------------------
pais(danilo,daniel,desconhecido).
nasc(danilo,data(4,4,2010)).

%-----------------vii)------------------------------
pais(eurico,elias,elsa).
nasc(eurico,data(desconhecido,5,2010)).
excepcao(nasc(eurico,data(5,5,2010))).
excepcao(nasc(eurico,data(15,5,2010))).
excepcao(nasc(eurico,data(25,5,2010))).

%-----------------viii)-----------------------------
pais(desconhecido,fausto,desconhecido).
excepcao(pais(fabia,fausto,desconhecido)).
excepcao(pais(octavia,fausto,desconhecido)).

%-----------------ix)-------------------------------
pais(golias,guido,guida).
nasc(golias,data(desconhecido,desconhecido,desconhecido)).

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