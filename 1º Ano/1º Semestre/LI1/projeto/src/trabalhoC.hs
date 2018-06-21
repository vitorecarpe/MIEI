{-|
Módulo: Main

Título: Problema C

Descrição: Módulo Haskell que dado um tabuleiro com o Sokoban e caixas seja dado um comando que faça movimentar o Sokoban.

Copyright: Francisco Oliveira /a78416@alunos.uminho.pt/   | | |   Vitor Peixoto /a79175@alunos.uminho.pt/

Resumo: Módulo contendo definições Haskell do Problema C da 1ª Fase do Projeto da disciplina de Laboratórios de Informática I. Este módulo contém funções que movimentam o Sokoban através da introdução de um tabuleiro com coordenadas do Sokoban e de caixas e de uma /String/ que indiquem a direção que pretendemos que o Sokoban tome (U,D,R ou L para Up (Cima), Down (Baixo), Right (Direita) ou Left (Esquerda) respetivamente). O Sokoban não se pode movimentar caso pretendemos que ele tome direção contra uma parede, uma caixa que tenha uma parede atrás, ou que mova mais do que uma caixa.

== FUNÇÕES DEFINIDAS NO ESQUELETO INICIAL
-}

module Main where

import qualified Data.Text as T

-- |Função que dada uma /String/, separa-a onde encontra /\\n/ e junta as diferentes /String/ criadas numa lista de /String/.
--
-- >>> "Ola\nMundo"
-- ["Ola","Mundo"]
inStr :: String -> [String]
inStr [] = []
inStr ['\n'] = [[],[]]
inStr (x:xs) = case x of
    '\n' -> []:inStr xs
    otherwise -> case inStr xs of
        y:ys -> (x:y):ys
        [] -> [[x]]

-- |Função que dada uma lista de /String/ devolve os elementos da lista, agrupados e apenas separados por /\\n/. Função inversa de /inStr/.
--
-- >>> ["Ola","Mundo"]
-- "Ola\nMundo\n"
outStr :: [String] -> String
outStr [] = "\n"
outStr t = unlines (map (T.unpack . T.stripEnd . T.pack) t)

-- |Função principal (/main/).
main = do inp <- getContents
          putStr (outStr (tarefa3 (inStr inp)))

-- |Função que dado o tabuleiro com coordenadas para Sokoban e caixas e uma /String/ dentro desta mesma lista (U,D,R,L), devolva a nova coordenada do Sokoban
--
-- >>> tarefa3 ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2","U"]
-- ["2 2"]
--
-- >>> tarefa3 ["#####","#. .#","#   #","#   #","#####","1 1","1 2","3 2","L"]
-- ["1 1"]
tarefa3 :: [String] -> [String]
tarefa3 tabuleiro = tarefa3Aux (reverse (funcaoPlantaMelhor tabuleiro)) (head (sepTabCOORDpares tabuleiro)) (last (sepCOORD1 (sepTabCOORD tabuleiro))) where 
    tarefa3Aux ((x:y):z) (a,b) direcao | direcao=="U" = movimentos ((x:y):z) (a,b) (0,1)
                                       | direcao=="D" = movimentos ((x:y):z) (a,b) (0,-1)
                                       | direcao=="L" = movimentos ((x:y):z) (a,b) (-1,0)
                                       | direcao=="R" = movimentos ((x:y):z) (a,b) (1,0)
                                       | otherwise = ["COMANDO INCORRETO"]

-- |Função que movimenta o Sokoban através da introdução das coordenadas do Sokoban e das coordenadas definidas para movimentar o Sokoban ((0,1) para cima ||| (0,-1) para baixo ||| (1,0) para a direita ||| (-1,0) para a esquerda).
--
-- >>> movimentos ["###","#.#","# #","# #","###"] (1,1) (0,1)
-- ["1 2"]
--
-- >>> movimentos ["###","#.#","# #","# #","###"] (1,1) (1,0)
-- ["1 1"]
movimentos :: [String] -> (Int,Int) -> (Int,Int) -> [String]
movimentos ((x:y):z) (a,b) (c,d) | (charNaPosicao ((x:y):z) (a+c,b+d))==' ' = ( show(a+c) ++ " " ++ show(b+d) ):[]
                                 | (charNaPosicao ((x:y):z) (a+c,b+d))=='.' = ( show(a+c) ++ " " ++ show(b+d) ):[]
                                 | (charNaPosicao ((x:y):z) (a+c,b+d))=='#' = ( show(a) ++ " " ++ show(b) ):[]
                                 | (charNaPosicao ((x:y):z) (a+c,b+d))=='H' = if ( ((charNaPosicao ((x:y):z) (a+2*c,b+2*d))=='H')||(charNaPosicao ((x:y):z) (a+2*c,b+2*d))=='I'||(charNaPosicao ((x:y):z) (a+2*c,b+2*d))=='#' ) then (show(a) ++ " " ++ show(b)):[]
                                                                              else ( show(a+c) ++ " " ++ show(b+d) ):[]
                                 | (charNaPosicao ((x:y):z) (a+c,b+d))=='I' = if ( ((charNaPosicao ((x:y):z) (a+2*c,b+2*d))=='H')||(charNaPosicao ((x:y):z) (a+2*c,b+2*d))=='I'||(charNaPosicao ((x:y):z) (a+2*c,b+2*d))=='#' ) then (show(a) ++ " " ++ show(b)):[]
                                                                              else ( show(a+c) ++ " " ++ show(b+d) ):[]
                                 | otherwise = ( show(a) ++ " " ++ show(b) ):[]


-- | Dado o tabuleiro com as coordenadas (/String/) devolve o tabuleiro com o Sokoban nas respetivas coordenadas e representado pelo "o", as caixas também nas respetivas coordenadas representadas por ""H"" e as caixas quando estão em cima do ponto são representadas por ""I"".
--
-- >>> funcaoPlantaMelhor ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"]
-- ["#####","#. .#","#H H#","# o #","#####"]
--
-- >>> funcaoPlantaMelhor ["#####","#. .#","#   #","#   #","#####","2 2","1 2","3 3"] 
-- ["#####","#. I#","#Ho #","#   #","#####"]
funcaoPlantaMelhor :: [String] -> [String]
funcaoPlantaMelhor tabuleiro = funcaoPlantaMelhorAux (reverse (sepTABCoord tabuleiro)) (1) (sepTabCOORDpares tabuleiro) where
    funcaoPlantaMelhorAux tabuleiro 1 ((x,y):[]) = ["O Sokoban nao pode jogar sem caixas!"]
    funcaoPlantaMelhorAux tabuleiro 1 ((x,y):z) = funcaoPlantaMelhorAux (updateTABsokoban tabuleiro (x,y)) 0 (z)
    funcaoPlantaMelhorAux tabuleiro 0 ((x,y):[]) = reverse (updateTABcaixas tabuleiro (x,y))
    funcaoPlantaMelhorAux tabuleiro 0 ((x,y):z) = funcaoPlantaMelhorAux (updateTABcaixas tabuleiro (x,y)) 0 (z)



-- |Função que dado um tabuleiro e a coordenada de uma caixa, coloca essa caixa no tabuleiro, que fica representada pelo carater ""H"" caso a caixa se localize num espaço vazio ou pelo carater ""I"" caso fique em cima de qualquer outro carater, ou seja, o ponto.
--
-- >>> updateTABcaixas ["#####","#. .#","#   #","#   #","#####"] (1,2)
-- ["#####","#. .#","#H  #","#   #","#####"]
--
-- >>> updateTABcaixas ["#####","#. .#","#   #","#   #","#####"] (1,1)
-- ["#####","#I .#","#   #","#   #","#####"]
updateTABcaixas :: [String] -> (Int,Int) -> [String]
updateTABcaixas ((x:y):z) (a,0) = (updateTABcaixasAux (x:y) a) : z
updateTABcaixas ((x:y):z) (a,b) = (x:y) : updateTABcaixas (z) (a,b-1)

-- |Função que dado um tabuleiro e a coordenada do Sokoban, coloca-o no tabuleiro, na sua respetiva coordenada. O Sokoban está representado pelo carater "o".
--
-- >>> updateTABsokoban ["#####","#. .#","#   #","#   #","#####"] (1,1)
-- ["#####","#o .#","#   #","#   #","#####"]
--
-- >>> updateTABsokoban ["#####","#. .#","#   #","#   #","#####"] (3,2)
-- ["#####","#. .#","#  o#","#   #","#####"]
updateTABsokoban :: [String] -> (Int,Int) -> [String]
updateTABsokoban ((x:y):z) (a,0) = (updateTABsokobanAux (x:y) a) : z
updateTABsokoban ((x:y):z) (a,b) = (x:y) : updateTABsokoban (z) (a,b-1)




-- |Função auxiliar de /movimentos/ que dado um tabuleiro e as coordenadas de uma caixa ou Sokoban diz o que está nessa coordenada. Se estiver vazio, está disponível para receber uma caixa ou o Sokoban.
--
-- >>> charNaPosicao ["#####","#. .#","#   #","#   #","#####"] (2,2)
-- ' '
--
-- >>> charNaPosicao ["#####","#. .#","#   #","#   #","#####"] (4,1)
-- '#'
--
-- == SEPARAR TABULEIRO DE COORDENADAS
--
charNaPosicao :: [String] -> (Int,Int) -> Char
charNaPosicao ((x:y):z) (0,0) = x
charNaPosicao ((x:[]):z) (a,0) = '!'
charNaPosicao ((x:y):z) (a,0) = charNaPosicao ((y):z) (a-1,0)
charNaPosicao ((x:y):[]) (a,b) = '!'
charNaPosicao ((x:y):z) (a,b) = charNaPosicao (z) (a,b-1)





-- |Separação do tabuleiro das coordenadas. Dá apenas o tabuleiro.
--
-- >>> sepTABCoord ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- ["#####","#. .#","#   #","#   #","#####"]
--
sepTABCoord :: [String] -> [String]
sepTABCoord ((x:y):z) = if (algarismos x) then [] 
                        else (x:y):sepTABCoord z

-- |Separação das coordenadas do tabuleiro. Dá apenas as coordenadas da localização do Sokoban e das caixas.
--
-- >>> sepTabCOORD ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- ["2 1","1 2","3 2"]
--
sepTabCOORD :: [String] -> [String]
sepTabCOORD ((x:y):z) = if (algarismos x) then ((x:y):z) 
                            else sepTabCOORD z

-- |Separa as coordenadas uma a uma.
--
-- >>> sepCOORD1 ["2 1","1 2","3 2"]
-- ["2","1","1","2","3","2"]
--
sepCOORD1 :: [String] -> [String]
sepCOORD1 (x:[]) = words x
sepCOORD1 (x:y)  = (words x) ++ (sepCOORD1 y)

-- |Após separar as coordenadas uma a uma, juntamo-las aos pares.
--
-- >>> sepCOORD2 ["2","1","1","2","3","2"]
-- [(2,1),(1,2),(3,2)]
--
sepCOORD2 :: [String] -> [(Int,Int)]
sepCOORD2 (x:[]) = []
sepCOORD2 (x:y:[]) = [((read x :: Int),(read y :: Int))]
sepCOORD2 (x:y:z)  = [((read x :: Int),(read y :: Int))] ++ sepCOORD2 z

-- |Juntamos as três anteriores funções e executamos todas apenas nesta.
--
-- >>> sepTabCOORDpares ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- [(2,1),(1,2),(3,2)]
--
-- == FUNÇÕES AUXILIARES
--
sepTabCOORDpares :: [String] -> [(Int,Int)]
sepTabCOORDpares tabuleiro = sepCOORD2 (sepCOORD1 (sepTabCOORD tabuleiro))




-- |Função auxiliar de /updateTABcaixas/ que define a letra a colocar na coordenada da caixa. Se no espaço estiver o carater " ", coloca ""H"". Se não for vazio, coloca o carater ""I"".
updateTABcaixasAux :: String -> Int -> String
updateTABcaixasAux (x:y) 0 = if (x==' ') then "H" ++ y
                                else "I" ++ y
updateTABcaixasAux (x:y) posicao = [x] ++ updateTABcaixasAux y (posicao-1)

-- |Função auxiliar de /updateTABsokoban/ que lê a coordenada do Sokoban e o coloca no tabuleiro representado pelo carater "o".
updateTABsokobanAux :: String -> Int -> String
updateTABsokobanAux (x:y) 0 = "o" ++ y
updateTABsokobanAux (x:y) posicao = [x] ++ updateTABsokobanAux y (posicao-1)

-- |Função auxiliar de /testeCOORDso2numeros/ que dada uma lista de /Char/ diz se essa lista tem apenas números. Qualquer outro tipo de carater devolve /Falso/.
--
-- >>> algarismos [1,3,5,a,6,b]
-- False
--
-- >>> algarismos [1,3,7,4,2]
-- True
--
-- == FUNÇÕES PREDEFINIDAS
--
algarismos :: Char -> Bool
algarismos a = if (a >= '0') && (a <= '9') then True
                else False


-- |Função predefinida que elimina as repetições dos elementos de uma lista.
--
-- >>> nub1 [1,3,1,5,3,4,2,3,1,3]
-- [1,3,5,4,2]
nub1 :: Eq a => [a] -> [a]
nub1 []=[]
nub1 a = nub1Aux [] a where
  nub1Aux a [] = a
  nub1Aux a (b:bs) = if ((elem b a)== False) then (nub1Aux (a ++ [b]) bs)
                   else (nub1Aux a bs)
