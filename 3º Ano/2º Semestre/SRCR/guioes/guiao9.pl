:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

:- op(900,xfy,'::').
:- dynamic '-'/1.
:- dynamic filho/3.
:- dynamic nasceu/2.
:- dynamic data/3.
:- dynamic servico/2.
:- dynamic atoMedico/4.


nao(Q) :- Q,!,fail.
nao(Q).

demo(Q,verdadeiro) :- Q.
demo(Q,falso) :- -Q.
demo(Q,desconhecido) :- nao(Q), nao(-Q).

solucoes( X,Y,Z ) :-
    findall( X,Y,Z ).

comprimento( S,N ) :-
    length( S,N ).

evolucao( Termo ) :-
    solucoes( Invariante,+Termo::Invariante,Lista ),
    insercao( Termo ),
    teste( Lista ).

retrocesso(F) :- 
	solucoes(I,-F::I,L),
	testar(L),
	remove(F).


remove(T) :- retract(T).

insercao( Termo ) :-
    assert( Termo ).
insercao( Termo ) :-
    retract( Termo ),!,fail.

teste( [] ).
teste( [R|LR] ) :-
    R,
    teste( LR ).

%-----------------i)--------------------------------
servico('Amélia','Ortopedia').
servico('Ana','Obstetrícia').
servico('Maria','Obstetrícia').
servico('Mariana','Obstetrícia').
servico('Sofia','Geriatria').
servico('Susana','Geriatria').
excepcao(servico('Teodora','#007')).
excepcao(servico('#np9','Zulmira')).
nulo('#np9').
+servico(SS,'Zulmira') :- (solucoes((SS,'Zulmira'),(servico(SS,'Zulmira'),nao(nulo(SS))),L),
				 comprimento(L,C),
				 C==0).

ato('Penso','Ana','Joana','sábado').
ato('Gesso','Amélia','José','domingo').
excepcao(ato('#017','Mariana','Joaquina','domingo')).
excepcao(ato('Domicílio','Maria','#121','#251')).
excepcao(ato('Domicílio','Susana','João','segunda')).
excepcao(ato('Domicílio','Susana','José','segunda')).
excepcao(ato('Sutura','#313','Josué','segunda')).
excepcao(ato('Sutura','Maria','Josefa','terça')).
excepcao(ato('Sutura','Maria','Josefa','sexta')).
excepcao(ato('Sutura','Mariana','Josefa','terça')).
excepcao(ato('Sutura','Mariana','Josefa','sexta')).
ato('Penso','Ana','Jacinta',X) :- (X='segunda';
								   X='terça';
								   X='quarta';
								   X='quinta';
								   X='sexta').

%-----------------ii)-------------------------------
feriado("domingo").
+ato(A,E,U,D) :: (solucoes(D,(ato(A,E,U,D),feriado(D)),L),
				 comprimento(L,C),
				 C==0).

%-----------------iii)------------------------------
-servico(E,S) :: (solucoes(E,(servico(E,S),ato(_,E,_,_)),L),
				 comprimento(L,C),
				 C==0).

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