% Base do Conhecimento Imperfeito

:- ensure_loaded(main).


%-----------------------------------------------------------------------------------------------------------------------
% Representaçao de Conhecimento Imperfeito

%-------------------------------------------------------
% Valor incerto (Tipo 1)

% Utente
% Desconhece-se o nome do utente com id 15 com 71 anos que vive em Vermoim do sexo masculino.

utente(15,inc1,71,'Vermoim','Masculino').
excecao(utente(IDU,N,I,M,G)) :- utente(IDU,inc1,I,M,G).

% Desconhece-se a idade do utente com id 22
utente(22,'Diogo',inc2,'Campos','Masculino').
excecao(utente(IDU,N,I,M,G)) :- utente(IDU,N,inc2,M,G).

% Desconhece-se a morada do utente com id 23
utente(23,'Carlota',68,inc3,'Feminino').
excecao(utente(IDU,N,I,M,G)) :- utente(IDU,N,I,inc3,G).


% Prestador
% Desconhece-se o nome do prestador com id 20
prestador(20,inc4,'Pediatria', 'Hospital de Santa Maria').
excecao(prestador(IDP,N,E,I)) :- prestador(IDP,inc4,E,I).

% Desconhece-se a Especialidade do prestador Carlos com id 12 que trabalha no Hospital de Santa Maria

prestador(12,'Carlos',inc5,'Hospital de Santa Maria').
excecao(prestador(IDP,N,E,I)) :- prestador(IDP,N,inc5,I).

% Desconhece-se a Instituiçao do prestador com id 21
prestador(21,'Matias','Pediatria',inc6).
excecao(prestador(IDP,N,E,I)) :- prestador(IDP,N,E,inc6).

% Cuidado
% Desconhece-se a data do cuidado
cuidado(inc7,3,3,'Perna partida',5).
excecao(cuidado(D,IDU,IDP,Desc,C)) :- cuidado(inc7,IDU,IDP,Desc,C).

% Desconhece-se a descrição do cuidado prestado pelo utente com id 6 no utente com id 6 no dia 09/04/2018 que teve um custo de 22€

cuidado('09-04-2018',6,6,inc8,22).
excecao(cuidado(D,IDU,IDP,Desc,C)) :- cuidado(D,IDU,IDP,inc8,C).
 
 % Desconhece-se o custo do cuidado
cuidado('30-10-2018',1,2,'Reacao alergica',inc9).
excecao(cuidado(D,IDU,IDP,Desc,C)) :- cuidado(D,IDU,IDP,Desc,inc9).
%-------------------------------------------------------
% Valor Impreciso (Tipo 2)


% Utente
% Não se tem a certeza se a Maria, o utente com id 16, se mora em Cerveira ou em Famalicao
excecao(utente(16,'Maria',20,'Vila Nova de Cerveira','Feminino')).
excecao(utente(16,'Maria',20,'Vila Nova de Famalicao','Feminino')).

% Prestador
% Não se sabe ao certo qual é a especialidade da Catariana, a prestadora com o id 13, do Hospital de Braga
excecao(prestador(13,'Catarina','Psiquiatria','Hospital de Braga')).
excecao(prestador(13,'Catarina','Cirurgia','Hospital de Braga')).
excecao(prestador(13,'Catarina','Ortopedia','Hospital de Braga')).

%Cuidado
% Não se sabe ao certo em que dia um cuidado foi prestado
excecao(cuidado('01-03-2018',5,5,'Urgencia',6)).
excecao(cuidado('02-03-2018',5,5,'Urgencia',6)).

%-------------------------------------------------------
% Valor Interdito (Tipo 3)
% Utente
% Nunca se irá saber qual a idade do utente com identificador 17

utente(17,'Salvador',idadeY,'Barcelos','Masculino').
excecao(utente(IDU,N,I,M,G)) :- utente(IDU,N,idadeY,M,G).
nulo(idadeY).


% Invariante que não permite a evolução do conhecimento do utente com id 17
+utente(IDU,N,I,M,G) ::(
	solucoes(I,(utente(17,'Salvador',I,'Barcelos','Masculino'),nao(nulo(I))),L),
	comprimento(L,R),
	R==0).


% Prestador
% Nunca se irá saber o nome do prestador com o identificador 15
prestador(15,nomeY,'Urologia','Hospital de Santa Maria').
excecao(prestador(IDP,N,E,I)) :- prestador(IDP,nomeY,E,I).
numo(nomeY).

% Invariante que não permite a evolução do conhecimento do prestador com ID=15
+prestador(IDP,N,E,I) ::(
	solucoes(N, (prestador(15,N,'Urologia','Hospital de Santa Maria'), nao(nulo(N))),L),
	comprimento(L,R),
	R == 0).