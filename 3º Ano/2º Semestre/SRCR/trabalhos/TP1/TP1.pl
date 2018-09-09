
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: definicoes iniciais

:- op( 900,xfy,'::' ).
:- dynamic utente/5.
:- dynamic prestador/4.
:- dynamic cuidado/5.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -

%utente(Id,Nome,Idade,Morada,Genero)
utente(1,'Raul',20,'Campos','Masculino').
utente(2,'Francisco',20,'Joane','Masculino').
utente(3,'Vitor',20,'Vermoim','Masculino').
utente(4,'Carlos',7,'Campos','Masculino').
utente(5,'Bruno',20,'Campos','Masculino').
utente(6,'Ana',3,'Cerveira','Feminino').
utente(7,'Susana',20,'Cerveira','Feminino').
utente(8,'Cristina',40,'Cerveira','Feminino').
utente(9,'Fatima',77,'Braga','Feminino').
utente(10,'Filipe',33,'Braga','Masculino').
utente(11,'Carla',11,'Porto','Feminino').
utente(12,'Fabio',88,'Famalicao','Masculino').

%prestador(IDPrestador,Nome,Especialidade,Instituição)
prestador(1,'Tiago','Ortopedia','Hospital de Braga').
prestador(2,'Guilherme','Urologia','Hospital de Braga').
prestador(3,'Renato','Radiologia','Hospital de Santa Maria').
prestador(4,'Filipe','Psiquiatria','Hospital de Santa Maria').
prestador(5,'Tiago','Cirurgia','Hospital de Braga').
prestador(6,'Vitor','Pediatria','Hospital de Santo Antonio').
prestador(7,'Gil','Cirurgia','Hospital de Braga').
prestador(8,'Joao','Ortopedia','Hospital de Braga').
prestador(9,'Diana','Psiquiatria','Hospital de Braga').

%cuidado(Data,    IDU,IDP, Descrição,Custo)
cuidado('01-01-2018', 3, 1,'Dor de barriga', 15). 
cuidado('04-04-2018', 7, 2,'Braco partido',20).    
cuidado('23-01-2018', 4, 2,'Perna partida', 30).   
cuidado('01-05-2018', 2, 3,'Reacao alergica', 5).  
cuidado('03-03-2018', 12,8, 'Febre', 4).  
cuidado('31-12-2018', 10,5, 'Febre', 5). 
cuidado('30-03-2018', 7, 9,'Analises', 5).  
cuidado('31-12-2018', 7, 6,'Urgencia', 200).  
cuidado('31-12-2018', 1, 6,'Braco partido', 1).  
cuidado('07-08-2018', 11, 9,'Reacao alergica', 5). 
cuidado('01-04-2018', 1, 4,'Dor de cabeca', 30).  

%-----------------------------------------------------------------------------------------------------------------------
%Registar utentes, prestadores e cuidados de saúde


% Não pode haver mais do que uma ocorrência de um utente
+utente(ID,No,I,M,G) :: (solucoes(ID,utente(ID,X,Y,Z,W),S ),
                      comprimento(S,N), 
				      N == 1).

% O utente a ser inserido não pode ter uma idade inválida
+utente(ID,N,I,M,G) :: naturais(I).

naturais(0).
naturais(X) :- N is X-1, N >= 0, naturais(N).

% O utente a ser inserido apenas pode ter dois géneros, masculino ou femininos
+utente(ID,N,I,M,G) :: genero(G).

genero(X) :- X == 'Masculino'; X == 'Feminino'.

% Não pode haver mais do que uma ocorrência de um prestador
+prestador(ID,No,E,I) :: (solucoes(ID,prestador(ID,X,Y,Z),S),
                		  comprimento(S,N), 
				 		  N == 1).

% Invariante que não permite a inserção de conhecimento de um cuidado quando o 
% id do utente/prestador não existem na base de conhecimento 
+cuidado(D,U,S,C,A) :: (solucoes(U, utente(U, _, _, _,_), Utentes),
				       comprimento(Utentes, N1),
				 	   N1 == 1).

+cuidado(D,U,S,C,A) :: (solucoes(S, prestador(S, _, _, _), Prest),
				    	comprimento(Prest, N2),
				 	    N2 == 1).

% O custo do cuidado inserido não pode ser negativo
+cuidado(D,U,S,C,A) :: C >= 0. 

%-----------------------------------------------------------------------------------------------------------------------
%Remover utentes, prestadores e cuidados de saúde

% Invariante que não permite a remoção de utentes caso exista cuidados de saúde com eles
-utente(ID,No,I,M,G) :: (solucoes(ID,cuidado(_,ID,_,_,_),L),
						 comprimento(L,X),
						 X == 0).

% Invariante que não permite a remoção de prestadores caso exista cuidados de saúde com eles
-prestador(ID,No,E,I) :: (solucoes(ID,cuidado(_,_,ID,_,_),L),
						 comprimento(L,X),
						 X == 0).


%-----------------------------------------------------------------------------------------------------------------------
%Identificar utentes por critérios de seleção

% Extensao do predicado utentesPorNome: Nome,R -> {V,F}
utentePorNome(Nome,R):- solucoes(utente(ID,Nome,Idade,Morada,Genero),utente(ID,Nome,Idade,Morada,Genero),R).

% Extensao do predicado utentesPorIdade: Idade,R -> {V,F}
utentePorIdade(Idade,R) :- solucoes(utente(ID,Nome,Idade,Morada,Genero),utente(ID,Nome,Idade,Morada,Genero),R).

% Extensao do predicado utentesPorMorada: Morada,R -> {V,F}
utentePorMorada(Morada,R) :- solucoes(utente(ID,Nome,Idade,Morada,Genero),utente(ID,Nome,Idade,Morada,Genero),R).

% Extensao do predicado utentesPorGenero: R -> {V,F}
% Retorna as informações de todos os utentes de um determinado genero
utentesPorGenero(Genero,R) :- solucoes(utente(IDU,Nome,Idade,Morada,Genero),utente(IDU,Nome,Idade,Morada,Genero),R).

% Extensao do predicado utentes_orderIdade: R -> {V,F}
% Ordenar utentes por idade
utentes_orderIdade(R) :- solucoes(utente(ID,Nome,Idade,Morada,Genero),utente(ID,Nome,Idade,Morada,Genero),L), ordenarPorIdade(L,R).

%-----------------------------------------------------------------------------------------------------------------------
% Identificar as instituições prestadoras de cuidados de saúde;

% Extensao do predicado inst_cuidados: R -> {V,F}
% Identificar as instituições prestadoras de cuidados de saúde, sem repetidos

inst_cuidados(R):- solucoes(Insti,(cuidado(_,_,IDP,_,_),prestador(IDP,_,_,Insti)),L),
				   removeDups(L,R).

%-----------------------------------------------------------------------------------------------------------------------
%Identificar cuidados de saúde prestados por instituição/cidade/datas

% Extensao do predicado cuidados_insti: Inst,R -> {V,F}
% Cuidados realizados por uma determinada Instituicao
cuidados_insti(Inst,R) :- solucoes(cuidado(D,IDU,IDP,Desc,C),(prestador(IDP,_,_,Inst),cuidado(D,IDU,IDP,Desc,C)),R).

% Extensao do predicado cuidados_morada: Morada,R -> {V,F}
% Retorna todos os cuidados de uma certa morada
cuidados_morada(Morada,R):- solucoes(cuidado(D,IDU,IDP,Desc,C),(utente(IDU,_,_,Morada,_),cuidado(D,IDU,IDP,Desc,C)),R).

% Extensao do predicado cuidados_data: Data,R -> {V,F}
% Retorna todos os cuidados numa determinada data
cuidados_data(Data,R) :- solucoes(cuidado(Data,X,Y,Z,W),cuidado(Data,X,Y,Z,W),R).

%-----------------------------------------------------------------------------------------------------------------------
%Identificar os utentes de um prestador/especialidade/instituição

% Extensao do predicado utentes_prest: IDP,R -> {V,F}
% Retorna os utentes de um certo prestador
utentes_prest(IDP,R) :- solucoes(utente(IDU,N,I,M,G), (cuidado(Data,IDU,IDP,Descricao,Custo),utente(IDU,N,I,M,G)),R).


% Extensao do predicado utentes_espe: Especialidade,R -> {V,F}
% Retorna os utentes de uma certa especialidade
utentes_espe(Espe,R) :- solucoes(utente(IDU,N,I,M,G), (cuidado(_,IDU,IDP,_,_),prestador(IDP,_,Espe,_),utente(IDU,N,I,M,G)),R).


% Extensao do predicado utentes_insti: Instituicao,R -> {V,F}
% Retorna os utentes  de uma determinada instituição 
utentes_inst(Inst,R) :- solucoes(utente(IDU,N,I,M,G), (cuidado(_,IDU,IDP,_,_),prestador(IDP,_,_,Inst),utente(IDU,N,I,M,G)),R).


%-----------------------------------------------------------------------------------------------------------------------
% Identificar cuidados de saúde realizados por utente/instituição/prestador

% Extensao do predicado cuidados_utente: Utente,R -> {V,F}
% Cuidados realizados por um utente especifico
cuidados_utente(Utente,R) :- solucoes(cuidado(X,Utente,Y,Z,W),cuidado(X,Utente,Y,Z,W),R).

% Extensao do predicado cuidados_prest: Prestador,R -> {V,F}
% Cuidados realizados por um prestador
cuidados_prest(Prestador,R) :- solucoes(cuidado(X,Y,Prestador,Z,W),cuidado(X,Y,Prestador,Z,W),R).


%-----------------------------------------------------------------------------------------------------------------------
%Determinar todas as instituições/prestadores a que um utente já recorreu

% Extensao do predicado insti_utente: Utente,R -> {V,F}
% Retorna a lista de instituições frequentadas por um utente sem repetições
insti_utente(IDU,R) :- solucoes(Insti,(cuidado(_,IDU,IDP,_,_),prestador(IDP,_,_,Insti),utente(IDU,_,_,_,_)),L),
					   removeDups(L,R).


% Extensao do predicado prest_utente: Utente,R -> {V,F}
%pega num utente e retorna todos os prestadores que cuidaram dele
prest_utente(IDU,R) :- solucoes(prestador(IDP,N,E,I),(cuidado(_,IDU,IDP,_,_),prestador(IDP,N,E,I)),L),
					   removeDups(L,R).

%-----------------------------------------------------------------------------------------------------------------------
%Calcular o custo total dos cuidados de saúde por utente/especialidade/prestador/datas.

% Extensao do predicado custo_utente : Utente,R -> {V,F}
% Calcular o custo total dos cuidados de saúde de um utente
custo_utente(Utente,R) :- solucoes(Custo,cuidado(_,Utente,_,_,Custo),L),
						  somaL(L,R).

% Extensao do predicado custo_espe : E,R -> {V,F}
% Calcular o custo total dos cuidados de saúde de uma especialidade
custo_espe(E,R) :- solucoes(Custo,(cuidado(_,_,IDP,_,Custo),prestador(IDP,_,E,_)),L),
				   somaL(L,R).

% Extensao do predicado custo_prestador: Prestador,R -> {V,F}
% Calcular o custo total dos cuidados de saúde de um prestador
custo_prestador(Prestador,R) :- solucoes(Custo,cuidado(_,_,Prestador,_,Custo),L),
						 		somaL(L,R).

% Extensao do predicado custo_data: Data,R -> {V,F}
%Calcular o custo total dos cuidados de saúde por data
custo_data(Data,R) :- solucoes(Custo,cuidado(Data,_,_,_,Custo),L),
					  somaL(L,R).
								
%-----------------------------------------------------------------------------------------------------------------------

teste([]).
teste([R|L]) :- R, teste(L).

insere(P) :- assert(P).
insere(P) :- retract(P),!,fail. 

evolucao(Termo) :- solucoes(Inv,+Termo::Inv,S),
				   insere(Termo),
				   teste(S).

remove(P) :- retract(P).
remove(P) :- assert(P),!,fail.

involucao( Termo ) :-  Termo,
					   solucoes(Inv,-Termo::Inv,S),
					   remove(Termo),
					   teste(S).

%---------------------------Extras-----------------------------------------------

% Extensao do predicado num_ut_fem:: R -> {V,F}
% Retorna o número de utentes do sexo feminino
num_ut_fem(R) :- solucoes(IDU,utente(IDU,N,I,M,'Feminino'),L),
				 comprimento(L,R).

% Extensao do predicado num_ut_mas:: R -> {V,F}
% Retorna o número de utentes do sexo masculino
num_ut_mas(R) :-  solucoes(IDU,utente(IDU,N,I,M,'Masculino'),L), 
				  comprimento(L,R).



% Extensao do predicado utentes_masc : R -> {V,F}
%cuidados de todos os utentes do sexo masculino
utentes_masc(R) :- solucoes(cuidados(D,IDU,IDP,Desc,C),(utente(IDU,_,_,_,'Masculino'),cuidado(D,IDU,IDP,Desc,C)),R).


% Extensao do predicado utenteInstAnos : N,Inst,R -> {V,F}
% Retorna os cuidados dos utentes de uma certa idade e com os prestadores de uma certa instituicao
utenteInstNAnos(N,Inst,L) :-
	solucoes(cuidado(D,IdU,IdP,Desc,C),(utente(IdU,_,N,_,_),prestador(IdP,_,_,Inst),cuidado(D,IdU,IdP,Desc,C)),L).


% Extensao do predicado custo_utentes: lista,R -> {V,F}
%retorna a lista de custos totais por utente
custo_utentes([],[]).
custo_utentes([IDU|T],R) :- solucoes(Custo,cuidado(_,IDU,_,_,Custo),L1),
					        somaL(L1,X),
					        custo_utentes(T,L2),
					        concat1([X],L2,R).

% Extensao do predicado top5Custo : R -> {V,F}
%top 5 dos utentes que gastam mais
top5Custo(R) :- solucoes(IDU,utente(IDU,_,_,_,_),L1),
				custo_utentes(L1,L2),
				concatPair(L2,L1,L3),
				sort(L3,L4),
				reverse(L4,L5),
				limite(L5,5,R).


%---------------------------Funções Auxiliares-----------------------------------

% Extensao do meta-predicado nao: Questao -> {V,F}
nao(Questao) :-
    Questao, !, fail.
nao(Questao).

comprimento(S,N) :- length(S,N).

% Extensão do predicado solucoes: X,Y,Z -> {V,F}
solucoes(X,Y,Z) :-
    findall(X,Y,Z).

%verifica se um elemento pertence a uma lista
pertence(X,[X|_]).
pertence(X,[H|T]) :- X \= H , pertence(X,T).

%verifica se um elemento não pertence a uma lista
n_pertence(X,L):- nao(pertence(X,L)).

%remove os valores duplicados de uma lista
removeDups([],[]).
removeDups([X|L],[H|R]) :- pertence(X,L), removeDups(L,[H|R]).
removeDups([X|L],[X|R]) :- n_pertence(X,L),removeDups(L,R).

% Extensao do predicado concat1(L1,L2,L3)->{V,F}
concat1([],L2,L2).
concat1([X|L1],L2,[X|L3]) :- concat1(L1,L2,L3).

% Extensao do predicado concatPair(L1,L2,L3)->{V,F}
concatPair([],[],[]).
concatPair([X|L1],[Y|L2],[(X,Y)|R]) :- concatPair(L1,L2,R).

%soma todos os valores de uma lista
somaL([],0).
somaL([H|T],R) :- somaL(T,R1), R is R1+H.


% Extensao do predicado ordenarPorIdade: L,Resultado -> {V,F}
ordenarPorIdade([X],[X]).
ordenarPorIdade([X|Y],T):- 
	ordenarPorIdade(Y,R),insertPorIdade(X,R,T).

% Extensao do predicado insertPorIdade: X,L,Resultado  -> {V,F}
insertPorIdade((X,Y,Z,W), [], [(X,Y,Z,W)]).
insertPorIdade((X1,Y1,Z1,W1), [(X2,Y2,Z2,W2)|T], [(X1,Y1,Z1,W1)|[(X2,Y2,Z2,W2)|T]]) :- Z1=<Z2.
insertPorIdade((X1,Y1,Z1,W1), [(X2,Y2,Z2,W2)|T], [(X2,Y2,Z2,W2)|R]) :- Z1>Z2,
	insertPorIdade((X1,Y1,Z1,W1),T,R).

% Extensao do predicado reverse(L1,L2)->{V,F}
reverse([],[]).
reverse([H|T],L) :- reverse(T,P), concat1(P,[H],L).

% Extensao do predicado limite L,N,L ->{V,F}
limite(X,0,[]).
limite([X|T],N,[X|R]) :- N1 is N - 1, limite(T,N1,R).