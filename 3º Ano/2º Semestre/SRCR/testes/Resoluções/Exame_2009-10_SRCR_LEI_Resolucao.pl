% relações
pertence(invertebrado,reinoAnimal).
pertence(vertebrado,reinoAnimal).
pertence(molusco,reinoAnimal).
pertence(artropode,reinoAnimal).
pertence(peixe,vertebrado).
pertence(anfibio,vertebrado).
pertence(ave,vertebrado).
pertence(mamifero,vertebrado).
pertence(insecto,artropode).
pertence(aracnideo,artropode).
pertence(crustaceo,artropode).
pertence(homemAranha,mamifero).
pertence(homemAranha,aracnideo).
pertence(sereia,peixe).
pertence(sereia,mamifero).

% Agentes
agente(reinoAnimal,descricao(NascemCrescemVivemMorrem)).
agente(invertebrado,[corpo(colunaDorsal)]).
agente(vertebrado,[corpo(colunaDorsal)]).
agente(molusco,[corpo(mole),corpo(couracado)]).
agente(artropode,[]).
agente(peixe,[membros(barbatanas),respiracao(guelras),cobertura(escamas),reproducao(oviparos)]).
agente(anfibio,[reproducao(oviparos)]).
agente(ave,[respiracao(pulmoes),cobertura(penas),reproducao(oviparos)]).
agente(mamifero,[membros(doisPares),respiracao(pulmoes),cobertura(pelos),reproducao(viviparos)]).
agente(insecto,[membros(tresPares),respiracao(traqueia)]).
agente(aracnideo,[membros(quatroPares),respiracao(traqueia)]).
agente(crustaceo,[]).
agente(homemAranha,[capacidade(planar)]).
agente(sereia,[respiracao(fora_e_dentro_de_agua)]).


prova( Q, [Q|T] ).
prova( Q, [ (Q:- C) |T] ):- C.
prova( Q, [X|T] ):- 
	Q \== X,
	prova( Q, T ).
	
	
demo( A, Q ):-
	agente( A, T ),
	prova( Q, T ).
demo( A, Q ):-
	agente( A, T ),
	nao( prova( Q, T) ),
	pertence( A, C ),
	demo( C, Q ).
	
nao(X):- X,!,fail.
nao(X).