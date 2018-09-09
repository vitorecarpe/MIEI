:- prolog_flag(single_var_warnings,_,off).
:- prolog_flag(unknown,_,fail).

:-op(900,xfy,'::').
:-dynamic formacao/2.
:-dynamic profissao/3.

nao(P):-P,!,fail.
nao(P).

formacao(ana,licenciatura).
formacao(david,bacharel).
formacao(fernando,doutoramento).
formacao(helder,escolaridade).
-formacao(A,B):-nao(formacao(A,B)),nao(excepcao(formacao(A,B))).

- formacao(A,B) :: (findall(Prof,(demo([profissao(A,Prof,Val)],e,V),V\=falso),L),comp(L,0)).

excepcao(formacao(edgar,mestrado)).
excepcao(formacao(edgar,mestradointegrado)).
excepcao(formacao(jonas,X)):-X\=doutoramento.

profissao(baltazar,professor,1200).
profissao(celso,tesoureiro,850).
profissao(celso,contabilista,1050).
profissao(david,varredor,450).
profissao(fernando,director,indefinido).
profissao(guilherme,administrador,variavel).
-profissao(A,B,C):- nao(profissao(A,B,C)),nao(excepcao(profissao(A,B,C))).

nulo(variavel).

+ profissao(Nome,Prof,Val) ::(findall(V,(profissao(guilherme,administrador,V),nao(nulo(V))),L),comp(L,0)).

excepcao(profissao(A,B,C)):-profissao(A,B,indefinido).
excepcao(profissao(A,B,C)):- profissao(A,B,variavel).
excepcao(profissao(helder,calceteiro,990)).
excepcao(profissao(helder,pedreiro,990)).
excepcao(profissao(helder,alvanel,990)).
excepcao(profissao(ivo,gerente,V)):- V>=4000,V=<5000.
excepcao(profissao(ivo,gestor,V)):- V>=4000,V=<5000.


max([],falso).
max([H|T],verdadeiro):- H.
max([H|T],verdadeiro):- nao(H),max(T,verdadeiro).
max([H|T],desconhecido):- nao(H),max(T,desconhecido).
max([H|T],desconhecido):- nao(H),nao(-H),max(T,X),X\=verdadeiro.
max([H|T],falso):- (-H), max(T,falso).

min([],verdadeiro).
min([H|T],verdadeiro):- H,min(T,verdadeiro).
min([H|T],falso):- (-H).
min([H|T],falso):- nao(-H), min(T,falso).
min([H|T],desconhecido) :- nao(H),nao(-H),min(T,X),X\=falso.
min([H|T],desconhecido):- H,min(T,desconhecido).

comp([],0).
comp([H|T],N):-comp(T,G), N is G+1.

demo(L,ou,V):-max(L,V).
demo(L,e,V):-min(L,V).

teste([]).
teste([H|R]):-H,teste(R).

insercao(T):-assert(T).
insercao(T):-retract(T),!,fail.

evolucao(T):-findall(I,+T::I,LI),insercao(T),teste(LI).

remocao(T):-retract(T).
remocao(T):-assert(T),!,fail.

retrocesso(T):-T,findall(I,-T::I,LI),remocao(T),teste(LI).
