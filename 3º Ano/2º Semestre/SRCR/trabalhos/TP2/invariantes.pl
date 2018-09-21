:- ensure_loaded(main).

%-----------------------------------------------------------------------------------------------------------------------
% Manipular invariantes que designem restrições à inserção e à remoção de conhecimento do sistema


% Não permite a inserção de um utente que já exista
+utente(ID,No,I,M,G) :: (solucoes(ID,utente(ID,X,Y,Z,W),S ),
                      comprimento(S,N), 
				      N == 1).


% O utente a ser inserido não pode ter uma idade inválida
+utente(ID,N,I,M,G) :: naturais(I).


% O utente a ser inserido apenas pode ter dois géneros, masculino ou femininos
+utente(ID,N,I,M,G) :: genero(G).


% Não pode haver mais do que uma ocorrência de um prestador
+prestador(ID,No,E,I) :: (solucoes(ID,prestador(ID,X,Y,Z),S),
                		  comprimento(S,N), 
				 		  N == 1).


% Invariante que não permite a inserção de conhecimento de um cuidado quando o 
% id do utente/prestador não existem na base de conhecimento 
+cuidado(D,U,S,Desc,C) :: (solucoes(U, utente(U, _, _, _,_), Utentes),
				       comprimento(Utentes, N1),
				 	   N1 == 1).


+cuidado(D,U,S,Desc,C) :: (solucoes(S, prestador(S, _, _, _), Prest),
				    	comprimento(Prest, N2),
				 	    N2 == 1).


% O custo do cuidado inserido não pode ser negativo
+cuidado(D,U,S,Desc,C) :: C >= 0. 


% Invariante que não permite a remoção de utentes caso exista cuidados de saúde com eles
-utente(ID,No,I,M,G) :: (solucoes(ID,cuidado(_,ID,_,_,_),L),
						 comprimento(L,X),
						 X == 0).


% Invariante que não permite a remoção de prestadores caso exista cuidados de saúde com eles
-prestador(ID,No,E,I) :: (solucoes(ID,cuidado(_,_,ID,_,_),L),
						 comprimento(L,X),
						 X == 0).

% Não permite que haja excecoes iguais
+excecao(T) :: ( solucoes(T,excecao(T),S ),
                 comprimento( S,L ), 
                 L == 1 ).

% Não permite adicionar conhecimento negativo repetido
+(-Termo) :: (solucoes(Termo, -Termo, S),
              comprimento(S,N),
               N == 1).

% Permite adicionar conhecimento positivo que contradiz o conhecimento negativo
+Termo :: nao(-Termo).

% Não deixa adicionar conhecimento negativo que contradiz o conhecimento positivo
+(-Termo) :: nao(Termo).
