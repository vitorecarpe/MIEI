%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: definicoes iniciais

:- op(900,xfy,'::').
:- dynamic utente/5.
:- dynamic prestador/4.
:- dynamic cuidado/5.
:- dynamic excecao/1.
:- dynamic '-'/1.


% Include dos ficheiros
:- include(conhecimento).
:- include(invariantes).
:- include(con_imperfeito).

%-----------------------------------------------------------------------------------------------------------------------
% Negação Forte

-utente(21,'Tobias',20,'Carvalha','Masculino').
-prestador(20,'Marta','Ortopedia','Hospital de Braga').
-cuidado('05-06-2018',3,1,'Febre',3).

%-----------------------------------------------------------------------------------------------------------------------
% Negação por falha

-utente(IDU,N,I,M,G) :- 
					nao(utente(IDU,N,I,M,G)),
					nao(excecao(utente(IDU,N,I,M,G))).

-prestador(IDP,N,E,I) :-
					nao(prestador(IDP,N,E,I)),
					nao(excecao(prestador(IDP,N,E,I))).

-cuidado(D,IDU,IDP,Des,C) :-
						nao(cuidado(D,IDU,IDP,Des,C)),
						nao(excecao(cuidado(D,IDU,IDP,Des,C))).


%-----------------------------------------------------------------------------------------------------------------------
% Evolução do Conhecimento Incerto

% Utentes
% Para o Nome
% Extenão do predicado evolucaoIncertoN: utente -> {V,F}
evolucaoIncertoN(utente(IDU,N,I,M,G)):-
	demo(utente(IDU,N,I,M,G),desconhecido),
	solucoes( (excecao(utente(IDU,N,I,M,G)) :- utente(IDU,X,I,M,G)), (utente(IDU,X,I,M,G),nao(nulo(X))), L),
	retractL(L),
	remove(utente(IDU,X,I,M,G)),
	evolucao(utente(IDU,N,I,M,G)).

% Para a Idade
% Extenão do predicado evolucaoIncertoI: utente -> {V,F}
evolucaoIncertoI(utente(IDU,N,I,M,G)):-
	demo(utente(IDU,N,I,M,G),desconhecido),
	solucoes( (excecao(utente(IDU,N,I,M,G)) :- utente(IDU,N,X,M,G)), (utente(IDU,N,X,M,G),nao(nulo(X))), L),
	retractL(L),
	remove(utente(IDU,N,X,M,G)),
	evolucao(utente(IDU,N,I,M,G)).

% Para a Morada
% Extenão do predicado evolucaoIncertoM: utente -> {V,F}
evolucaoIncertoM(utente(IDU,N,I,M,G)):-
	demo(utente(IDU,N,I,M,G),desconhecido),
	solucoes( (excecao(utente(IDU,N,I,M,G)) :- utente(IDU,N,I,X,G)), (utente(IDU,N,I,X,G),nao(nulo(X))), L),
	retractL(L),
	remove(utente(IDU,N,I,X,G)),
	evolucao(utente(IDU,N,I,M,G)).


% Prestadores
% Para o nome
% Extenão do predicado evolucaoIncertoN: prestador -> {V,F}
evolucaoIncertoN(prestador(IDP,N,E,I)):-
	demo(prestador(IDP,N,E,I),desconhecido),
	solucoes( (excecao(prestador(IDP,N,E,I)) :- prestador(IDP,X,E,I)), (prestador(IDP,X,E,I),nao(nulo(X))), L),
	retractL(L),
	remove(prestador(IDP,X,E,I)),
	evolucao(prestador(IDP,N,E,I)).

% Para a Especialidade
% Extenão do predicado evolucaoIncertoE: prestador -> {V,F}
evolucaoIncertoE(prestador(IDP,N,E,I)):-
	demo(prestador(IDP,N,E,I),desconhecido),
	solucoes( (excecao(prestador(IDP,N,E,I)) :- prestador(IDP,N,X,I)), (prestador(IDP,N,X,I),nao(nulo(X))), L),
	retractL(L),
	remove(prestador(IDP,N,X,I)),
	evolucao(prestador(IDP,N,E,I)).

% Para a Instituicao
% Extenão do predicado evolucaoIncertoI: prestador -> {V,F}
evolucaoIncertoI(prestador(IDP,N,E,I)):-
	demo(prestador(IDP,N,E,I),desconhecido),
	solucoes( (excecao(prestador(IDP,N,E,I)) :- prestador(IDP,N,E,X)), (prestador(IDP,N,E,X),nao(nulo(X))), L),
	retractL(L),
	remove(prestador(IDP,N,E,X)),
	evolucao(prestador(IDP,N,E,I)).


% Cuidados
% Para a Data
% Extenão do predicado evolucaoIncertoD: cuidado -> {V,F}
evolucaoIncertoD(cuidado(D,IDU,IDP,Desc,C)):-
	demo(cuidado(D,IDU,IDP,Desc,C),desconhecido),
	solucoes( (excecao(cuidado(D,IDU,IDP,Desc,C)) :- cuidado(X,IDU,IDP,Desc,C)), (cuidado(X,IDU,IDP,Desc,C),nao(nulo(X))), L),
	retractL(L),
	remove(cuidado(X,IDU,IDP,Desc,C)),
	evolucao(cuidado(D,IDU,IDP,Desc,C)).

% Para a Descricao
% Extenão do predicado evolucaoIncertoDesc: cuidado -> {V,F}
evolucaoIncertoDesc(cuidado(D,IDU,IDP,Desc,C)):-
	demo(cuidado(D,IDU,IDP,Desc,C),desconhecido),
	solucoes( (excecao(cuidado(D,IDU,IDP,Desc,C)) :- cuidado(D,IDU,IDP,X,C)), (cuidado(D,IDU,IDP,X,C),nao(nulo(X))), L),
	retractL(L),
	remove(cuidado(D,IDU,IDP,X,C)),
	evolucao(cuidado(D,IDU,IDP,Desc,C)).

% Para o Custo
% Extenão do predicado evolucaoIncertoC: cuidado -> {V,F}
evolucaoIncertoC(cuidado(D,IDU,IDP,Desc,C)):-
	demo(cuidado(D,IDU,IDP,Desc,C),desconhecido),
	solucoes( (excecao(cuidado(D,IDU,IDP,Desc,C)) :- cuidado(D,IDU,IDP,Desc,X)), (cuidado(D,IDU,IDP,Desc,X),nao(nulo(X))), L),
	retractL(L),
	remove(cuidado(D,IDU,IDP,Desc,X)),
	evolucao(cuidado(D,IDU,IDP,Desc,C)).

%-----------------------------------------------------------------------------------------------------------------------
% Evolução do Conhecimento Impreciso

% Extenão do predicado evolucaoImpreciso: utente -> {V,F}
evolucaoImpreciso(utente(IDU,Nome,Idade,Morada,Genero)):-
		demo(utente(IDU,Nome,Idade,Morada,Genero),desconhecido),
		solucoes(excecao(utente(IDU,N,I,M,G)), excecao(utente(IDU,N,I,M,G)),L),
		retractL(L),
		evolucao(utente(IDU,Nome,Idade,Morada,Genero)).

% Extenão do predicado evolucaoImpreciso: utente -> {V,F}
evolucaoImpreciso(prestador(IDP,Nome,Especialidade,Instituicao)):-
		demo(prestador(IDP,Nome,Especialidade,Instituicao),desconhecido),
		solucoes(excecao(prestador(IDP,N,E,I)), excecao(prestador(IDP,N,E,I)),L),
		retractL(L),
		evolucao(prestador(IDP,Nome,Especialidade,Instituicao)).


% Extenão do predicado evolucaoImpreciso: cuidado -> {V,F}
evolucaoImpreciso(cuidado(Data,IDU,IDP,Descricao,Custo)):-
		demo(cuidado(Data,IDU,IDP,Descricao,Custo),desconhecido),
		solucoes(excecao(cuidado(D,IDU,IDP,Desc,C)), excecao(cuidado(D,IDU,IDP,Desc,C)),L),
		retractL(L),
		evolucao(cuidado(Data,IDU,IDP,Descricao,Custo)).



%-----------------------------------------------------------------------------------------------------------------------
% Inserir Conhecimento Imperfeito Impreciso

% Extenão do predicado inserierImpreciso: utente -> {V,F}
inserirImpreciso(utente(IDU,[],I,M,G)).
inserirImpreciso(utente(IDU,[N|T],I,M,G)) :-
	    evolucao( (excecao(utente(IDU,N,I,M,G)))),
	    inserirImpreciso(utente(IDU,T,I,M,G)).

inserirImpreciso(utente(IDU,N,[],M,G)).
inserirImpreciso(utente(IDU,N,[I|T],M,G)) :-
	    evolucao( (excecao(utente(IDU,N,I,M,G)))),
	    inserirImpreciso(utente(IDU,N,T,M,G)).

inserirImpreciso(utente(IDU,N,I,[],G)).
inserirImpreciso(utente(IDU,N,I,[M|T],G)) :-
	    evolucao( (excecao(utente(IDU,N,I,M,G)))),
	    inserirImpreciso(utente(IDU,N,I,T,G)).


% Extenão do predicado inserierImpreciso: prestador -> {V,F}
inserirImpreciso(prestador(IDP,[],E,I)).
inserirImpreciso(prestador(IDP,[N|T],E,I)) :-
	    evolucao( (excecao(prestador(IDP,N,E,I)))),
	    inserirImpreciso(prestador(IDP,T,E,I)).

inserirImpreciso(prestador(IDP,N,[],I)).
inserirImpreciso(prestador(IDP,N,[E|T],I)) :-
	    evolucao( (excecao(prestador(IDP,N,E,I)))),
	    inserirImpreciso(prestador(IDP,N,T,I)).

inserirImpreciso(prestador(IDP,N,E,[])).
inserirImpreciso(prestador(IDP,N,E,[I|T])) :-
	    evolucao( (excecao(prestador(IDP,N,E,I)))),
	    inserirImpreciso(prestador(IDP,N,E,T)).


% Extenão do predicado inserierImpreciso: cuidado -> {V,F}
inserirImpreciso(cuidado([],IDU,IDP,Desc,C)).
inserirImpreciso(cuidado([D|T],IDU,IDP,Desc,C)) :-
	    evolucao( (excecao(cuidado(D,IDU,IDP,Desc,C)))),
	    inserirImpreciso(cuidado(T,IDU,IDP,Desc,C)).

inserirImpreciso(cuidado(D,IDU,IDP,[],C)).
inserirImpreciso(cuidado(D,IDU,IDP,[Desc|T],C)) :-
	    evolucao( (excecao(cuidado(D,IDU,IDP,Desc,C)))),
	    inserirImpreciso(cuidado(D,IDU,IDP,T,C)).

inserirImpreciso(cuidado(D,IDU,IDP,Desc,[])).
inserirImpreciso(cuidado(D,IDU,IDP,Desc,[C|T])) :-
	    evolucao( (excecao(cuidado(D,IDU,IDP,Desc,C)))),
	    inserirImpreciso(cuidado(D,IDU,IDP,Desc,T)).

%-----------------------------------------------------------------------------------------------------------------------
% Sistema de Inferência 

% Extenão do predicado conjuncao: X, Y -> {V,F,D}
conjuncao(verdadeiro,verdadeiro,verdadeiro).
conjuncao(verdadeiro,desconhecido,desconhecido).
conjuncao(desconhecido,verdadeiro,desconhecido).
conjuncao(desconhecido,desconhecido,desconhecido).
conjuncao(falso,_,falso).
conjuncao(_,falso,falso).

% Extenão do predicado disjuncao: X, Y -> {V,F,D}
disjuncao(verdadeiro,_,verdadeiro).
disjuncao(_,verdadeiro,verdadeiro).
disjuncao(falso,falso,falso).
disjuncao(falso,desconhecido,desconhecido).
disjuncao(desconhecido,falso,desconhecido).
disjuncao(desconhecido,desconhecido,desconhecido).

% Extenão do predicado demoC: Lista, R -> {V,F,D}
demoC([],R).
demoC([Q],R) :- demo(Q,R).
demoC([Q1,e,Q2|T],R) :-
 	demo(Q1,R1),
  	demoC([Q2|T],R2),
  	conjuncao(R1,R2,R).

% Extenão do predicado demoD: Lista, R -> {V,F,D}
demoD([],R).
demoD([Q],R) :- demo(Q,R).
demoD([Q1,ou,Q2|T],R) :-
 	demo(Q1,R1),
  	demoD([Q2|T],R2),
  	disjuncao(R1,R2,R).

%-----------------------------------------------------------------------------------------------------------------------
% Funções Auxiliares

% Extensão do predicado que permite a registar conhecimento
teste([]).
teste([R|L]) :- R, teste(L).

insere(P) :- assert(P).
insere(P) :- retract(P),!,fail. 

evolucao(Termo) :- solucoes(Inv,+Termo::Inv,S),
				   insere(Termo),
				   teste(S).

%-----------------------------------------------------------------------------------------------------------------------

% Extensão do predicado que permite a remover conhecimento
remove(P) :- retract(P).
remove(P) :- assert(P),!,fail.

involucao( Termo ) :-  Termo,
					   solucoes(Inv,-Termo::Inv,S),
					   remove(Termo),
					   teste(S).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extenão do predicado retracL: Lista -> {V,F}
retractL([]).
retractL([X|L]):- 
		retract(X), 
		retractL(L).

% Extensao do predicado demo: Questao,Resposta -> {V,F,D}
demo( Questao,verdadeiro ) :- Questao.
demo( Questao, falso ) :- -Questao.
demo( Questao,desconhecido ) :- nao( Questao ), nao( -Questao ).

%-----------------------------------------------------------------------------------------------------------------------

%Extensao do predicado nao: Questao -> {V,F}
nao(Questao) :-
    Questao, !, fail.
nao(Questao).

% Extensão do predicado comprimento: (S,N) -> {V,F}
comprimento(S,N) :- length(S,N).

% Extensão do predicado solucoes: X,Y,Z -> {V,F}
solucoes(X,Y,Z) :-
    findall(X,Y,Z).

% Extensão do predicado genero: X -> {V,F}
genero(X) :- X == 'Masculino'.
genero(X) :- X == 'Feminino'.

% Extensão do predicado naturais: X -> {V,F}
naturais(0).
naturais(X) :- N is X-1, N >= 0, naturais(N).
