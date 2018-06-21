{-|
Módulo: Main

Título: Problema B

Descrição: Módulo Haskell que dado um tabuleiro já funcional, efetua melhoramentos visuais ao mesmo.

Copyright: Francisco Oliveira /a78416@alunos.uminho.pt/   | | |   Vitor Peixoto /a79175@alunos.uminho.pt/

Resumo: Módulo contendo definições Haskell do Problema B da 1ª Fase do Projeto da disciplina de Laboratórios de Informática I. Este módulo contém funções que melhoram o aspeto visual, nomeadamente, introduzindo as caixas e o Sokoban nas respetivas coordenadas e eliminando os carateres /#/ que são redundantes, ou seja, não influenciem o funcionamento do jogo através do seu mapa.

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
          putStr (outStr (tarefa2 (inStr inp)))

-- |Função responsável pela funcionalidade da tarefa, que neste caso é colocar as caixas e o Sokoban nas coordenadas respetivas e eliminar os "#" redundantes.
--
-- == FUNÇÕES DE MELHORAMENTO VISUAL DO MAPA
--
tarefa2 :: [String] -> [String]
tarefa2 tabuleiro = funcaoRemoveCard (funcaoPlantaMelhor tabuleiro)

-- | Dado o tabuleiro com as coordenadas (/String/) devolve o tabuleiro com o Sokoban nas respetivas coordenadas e representado pelo "o", as caixas também nas respetivas coordenadas representadas por ""H"" e as caixas quando estão em cima do ponto são representadas por ""I"".
--
-- >>> funcaoPlantaMelhor ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- ["#####","#. .#","#H H#","# o #","#####"]
--
-- >>> funcaoPlantaMelhor ["#####","#. .#","#   #","#   #","#####","2 2","1 2","3 3"," "] 
-- ["#####","#. I#","#Ho #","#   #","#####"]
funcaoPlantaMelhor :: [String] -> [String]
funcaoPlantaMelhor tabuleiro = funcaoPlantaMelhorAux (reverse (sepTABCoord tabuleiro)) (1) (sepTabCOORDpares tabuleiro) where
    funcaoPlantaMelhorAux tabuleiro 1 ((x,y):[]) = ["O Sokoban nao pode jogar sem caixas!"]
    funcaoPlantaMelhorAux tabuleiro 1 ((x,y):z) = funcaoPlantaMelhorAux (updateTABsokoban tabuleiro (x,y)) 0 (z)
    funcaoPlantaMelhorAux tabuleiro 0 ((x,y):[]) = (reverse (updateTABcaixas tabuleiro (x,y)))
    funcaoPlantaMelhorAux tabuleiro 0 ((x,y):z) = funcaoPlantaMelhorAux (updateTABcaixas tabuleiro (x,y)) 0 (z)

-- |Função que remove os cardinais redundantes do tabuleiro, ou seja, aqueles que não são necessários, sem influenciar a jogabilidade do tabuleiro.
-- 
-- >>> funcaoRemoveCard ["########","#. ..###","#### ###","###    #","########"]
-- ["######  ","#. ..#  ","#### ###","  #    #","  ######"]
funcaoRemoveCard :: [String] -> [String]
funcaoRemoveCard tabuleiro = (funcaoTiraInterrogacao (funcaoRemoveCardCentro (funcaoRemoveCardLados (funcaoRemoveCardCantos tabuleiro 4))))


-- |Função que seleciona os cardinais que estão nos quatro cantos do tabuleiro e os substitui por pontos de interrogação "?" caso o carater esteja rodeado dentro dos limites do tabuleiro por cardinais "#".
--
-- >>> funcaoRemoveCardCantos ["#####","##.##","#   #","## ##","#####"] 4
-- ["?###?","##.##","#   #","## ##","?###?"]
--
-- >>> funcaoRemoveCardCantos ["#####","#. .#","#   #","#   #","#####"] 4
-- ["#####","#. .#","#   #","## ##","?###?"]
funcaoRemoveCardCantos :: [String] -> Int -> [String]
funcaoRemoveCardCantos tabuleiro 4 = if ((head (tail (head (tail tabuleiro))))=='#') then funcaoRemoveCardCantos (updateTAB tabuleiro (0,0)) 3
                                        else funcaoRemoveCardCantos tabuleiro 3
funcaoRemoveCardCantos tabuleiro 3 = if ((last (init (head (tail tabuleiro))))=='#') then funcaoRemoveCardCantos (updateTAB tabuleiro ((length (head tabuleiro)-1),0)) 2
                                        else funcaoRemoveCardCantos tabuleiro 2
funcaoRemoveCardCantos tabuleiro 2 = if ((head (tail (last (init tabuleiro))))=='#') then funcaoRemoveCardCantos (updateTAB tabuleiro (0,((length tabuleiro)-1))) 1
                                        else funcaoRemoveCardCantos tabuleiro 1
funcaoRemoveCardCantos tabuleiro 1 = if ((last (init (last (init tabuleiro))))=='#') then updateTAB tabuleiro ((length (head tabuleiro)-1),((length tabuleiro)-1))
                                        else (tabuleiro)


-- |Função que seleciona cada cardinal que está nas bordas do tabuleiro e, caso esteja rodeado de cardinais "#" /e///ou/ "?", substitui-o por um ponto de interrogação "?".
--
-- >>> funcaoRemoveCardLados ["?######","###.  #","#    ##","#    ##","######?"]
-- ["??#####","###.  #","#    ##","#    #?","######?"]
--
-- >>> funcaoRemoveCardLados ["?####?","##.###","#   .#","##  ##","?####?"]
-- ["?###??","##.###","#   .#","##  ##","?####?"]
funcaoRemoveCardLados :: [String] -> [String]
funcaoRemoveCardLados tabuleiro = funcaoRemoveCardLadosAux tabuleiro 4 ((length tabuleiro)-2) ((length (head tabuleiro))-2) where
    funcaoRemoveCardLadosAux tabuleiro 4 1 y = if (funcaoRemoveCardLadosL tabuleiro 1) then funcaoRemoveCardLadosAux (updateTAB tabuleiro (0,1)) 3 ((length tabuleiro)-2) y
                                                else funcaoRemoveCardLadosAux tabuleiro 3 ((length tabuleiro)-2) y
    funcaoRemoveCardLadosAux tabuleiro 4 x y = if (funcaoRemoveCardLadosL tabuleiro x) then funcaoRemoveCardLadosAux (updateTAB tabuleiro (0,x)) 4 (x-1) y
                                                else funcaoRemoveCardLadosAux tabuleiro 4 (x-1) y
    funcaoRemoveCardLadosAux tabuleiro 3 1 y = if (funcaoRemoveCardLadosR tabuleiro 1) then funcaoRemoveCardLadosAux (updateTAB tabuleiro (length (head tabuleiro)-1,1)) 2 ((length tabuleiro)-2) y
                                                else funcaoRemoveCardLadosAux tabuleiro 2 ((length tabuleiro)-2) y
    funcaoRemoveCardLadosAux tabuleiro 3 x y = if (funcaoRemoveCardLadosR tabuleiro x) then funcaoRemoveCardLadosAux (updateTAB tabuleiro (length (head tabuleiro)-1,x)) 3 (x-1) y
                                                else funcaoRemoveCardLadosAux tabuleiro 3 (x-1) y
    funcaoRemoveCardLadosAux tabuleiro 2 y 1 = if (funcaoRemoveCardLadosU tabuleiro 1) then funcaoRemoveCardLadosAux (updateTAB tabuleiro (1,0)) 1 y ((length (head tabuleiro))-2)
                                                else funcaoRemoveCardLadosAux tabuleiro 1 y ((length (head tabuleiro))-2)
    funcaoRemoveCardLadosAux tabuleiro 2 y x = if (funcaoRemoveCardLadosU tabuleiro x) then funcaoRemoveCardLadosAux (updateTAB tabuleiro (x,0)) 2 y (x-1)
                                                else funcaoRemoveCardLadosAux tabuleiro 2 y (x-1)
    funcaoRemoveCardLadosAux tabuleiro 1 y 1 = if (funcaoRemoveCardLadosD tabuleiro 1) then (updateTAB tabuleiro (1,(length tabuleiro)-1))
                                                else (tabuleiro)
    funcaoRemoveCardLadosAux tabuleiro 1 y x = if (funcaoRemoveCardLadosD tabuleiro x) then funcaoRemoveCardLadosAux (updateTAB tabuleiro (x,(length tabuleiro)-1)) 1 y (x-1)
                                                else funcaoRemoveCardLadosAux tabuleiro 1 y (x-1)

-- |Função que seleciona cada cardinal que está nas interior do tabuleiro (ou seja, não está nas bordas do tabuleiro)e, caso esteja rodeado de cardinais "#" /e///ou/ "?" na sua imediação, substitui-o por um ponto de interrogação "?".
--
-- >>> funcaoRemoveCardCentro ["######??","#. ..##?","#### ###","?##    #","??######"]
-- ["######??","#. ..#??","#### ###","??#    #","??######"]
funcaoRemoveCardCentro :: [String] -> [String]
funcaoRemoveCardCentro tabuleiro = funcaoRemoveCardCentroAux tabuleiro (1,1)

-- |Função que substitui os carateres pontos de interrogação "?" colocados anteriormente por espaços " ". Deste modo eliminamos os "#" redundantes e que eram desnecessários ao tabuleiro.
--
-- >>> funcaoTiraInterrogacao ["??#####","###.  #","#    ##","#    #?","######?"]
-- ["  #####","###.  #","#    ##","#    # ","###### "]
--
-- == FUNÇÕES AUXILIARES DE /funcaoRemoveCardCentro/
--
funcaoTiraInterrogacao :: [String] -> [String]
funcaoTiraInterrogacao ((x:y):[]) = funcaoTiraInterrogacaoAux (x:y):[]
funcaoTiraInterrogacao ((x:y):z) = funcaoTiraInterrogacaoAux (x:y) : funcaoTiraInterrogacao z




-- |Função auxiliar de /funcaoRemoveCardCentro/.
funcaoRemoveCardCentroAux :: [String] -> (Int,Int) -> [String]
funcaoRemoveCardCentroAux (a:b:c:[]) (x,y) = if (x<((length b)-2)) then funcaoRemoveCardCentroAux1 (a:b:c:[]) (x,y) 2
                                                else funcaoRemoveCardCentroAux1 (a:b:c:[]) (x,y) 0
funcaoRemoveCardCentroAux (a:b:c:d) (x,y)   = if (x<((length b)-2)) then funcaoRemoveCardCentroAux1 (a:b:c:d) (x,y) 2
                                                else funcaoRemoveCardCentroAux1 (a:b:c:d) (x,y) 1

-- |Função auxiliar de /funcaoRemoveCardCentro/.
funcaoRemoveCardCentroAux1 :: [String] -> (Int,Int) -> Int -> [String]
funcaoRemoveCardCentroAux1 (a:b:c:[]) (x,y) 0 = if ((charNaPosicao (a:b:c:[]) (x,y))=='#') then funcaoRemoveCardCentroAux2 (a:b:c:[]) (x,y) 0
                                                else a:b:c:[]
funcaoRemoveCardCentroAux1 (a:b:c:d) (x,y) 1 = if ((charNaPosicao (a:b:c:d) (x,y))=='#') then funcaoRemoveCardCentroAux2 (a:b:c:d) (x,y) 1
                                                else a:(funcaoRemoveCardCentroAux (b:c:d) (1,1))
funcaoRemoveCardCentroAux1 (a:b:c:d) (x,y) 2 = if ((charNaPosicao (a:b:c:d) (x,y))=='#') then funcaoRemoveCardCentroAux2 (a:b:c:d) (x,y) 2
                                                else funcaoRemoveCardCentroAux (a:b:c:d) (x+1,y)

-- |Função auxiliar de /funcaoRemoveCardCentro/.
funcaoRemoveCardCentroAux2 :: [String] -> (Int,Int) -> Int -> [String]
funcaoRemoveCardCentroAux2 (a:b:c:[]) (x,y) 0 = if (   (((charNaPosicao (a:b:c:[]) (x-1,0))=='#')||((charNaPosicao (a:b:c:[]) (x-1,0))=='?')) && (((charNaPosicao (a:b:c:[]) (x,0))=='#')||((charNaPosicao (a:b:c:[]) (x,0))=='?')) && (((charNaPosicao (a:b:c:[]) (x+1,0))=='#')||((charNaPosicao (a:b:c:[]) (x+1,0))=='?'))
                                                    && (((charNaPosicao (a:b:c:[]) (x-1,1))=='#')||((charNaPosicao (a:b:c:[]) (x-1,1))=='?')) && (((charNaPosicao (a:b:c:[]) (x+1,1))=='#')||((charNaPosicao (a:b:c:[]) (x+1,1))=='?')) &&
                                                       (((charNaPosicao (a:b:c:[]) (x-1,2))=='#')||((charNaPosicao (a:b:c:[]) (x-1,2))=='?')) && (((charNaPosicao (a:b:c:[]) (x,2))=='#')||((charNaPosicao (a:b:c:[]) (x,2))=='?')) && (((charNaPosicao (a:b:c:[]) (x+1,2))=='#')||((charNaPosicao (a:b:c:[]) (x+1,2))=='?')) )
                                                        then (updateTAB (a:b:c:[]) (x,1))
                                                else a:b:c:[]
funcaoRemoveCardCentroAux2 (a:b:c:d) (x,y) 1 = if (   (((charNaPosicao (a:b:c:d) (x-1,0))=='#')||((charNaPosicao (a:b:c:d) (x-1,0))=='?')) && (((charNaPosicao (a:b:c:d) (x,0))=='#')||((charNaPosicao (a:b:c:d) (x,0))=='?')) && (((charNaPosicao (a:b:c:d) (x+1,0))=='#')||((charNaPosicao (a:b:c:d) (x+1,0))=='?'))
                                                   && (((charNaPosicao (a:b:c:d) (x-1,1))=='#')||((charNaPosicao (a:b:c:d) (x-1,1))=='?')) && (((charNaPosicao (a:b:c:d) (x+1,1))=='#')||((charNaPosicao (a:b:c:d) (x+1,1))=='?')) &&
                                                      (((charNaPosicao (a:b:c:d) (x-1,2))=='#')||((charNaPosicao (a:b:c:d) (x-1,2))=='?')) && (((charNaPosicao (a:b:c:d) (x,2))=='#')||((charNaPosicao (a:b:c:d) (x,2))=='?')) && (((charNaPosicao (a:b:c:d) (x+1,2))=='#')||((charNaPosicao (a:b:c:d) (x+1,2))=='?')) )
                                                        then a:(funcaoRemoveCardCentroAux (updateTAB (b:c:d) (x,0)) (1,1)) 
                                                else a:(funcaoRemoveCardCentroAux (b:c:d) (1,1))
funcaoRemoveCardCentroAux2 (a:b:c:d) (x,y) 2 = if (   (((charNaPosicao (a:b:c:d) (x-1,0))=='#')||((charNaPosicao (a:b:c:d) (x-1,0))=='?')) && (((charNaPosicao (a:b:c:d) (x,0))=='#')||((charNaPosicao (a:b:c:d) (x,0))=='?')) && (((charNaPosicao (a:b:c:d) (x+1,0))=='#')||((charNaPosicao (a:b:c:d) (x+1,0))=='?'))
                                                   && (((charNaPosicao (a:b:c:d) (x-1,1))=='#')||((charNaPosicao (a:b:c:d) (x-1,1))=='?')) && (((charNaPosicao (a:b:c:d) (x+1,1))=='#')||((charNaPosicao (a:b:c:d) (x+1,1))=='?')) &&
                                                      (((charNaPosicao (a:b:c:d) (x-1,2))=='#')||((charNaPosicao (a:b:c:d) (x-1,2))=='?')) && (((charNaPosicao (a:b:c:d) (x,2))=='#')||((charNaPosicao (a:b:c:d) (x,2))=='?')) && (((charNaPosicao (a:b:c:d) (x+1,2))=='#')||((charNaPosicao (a:b:c:d) (x+1,2))=='?')) )
                                                        then funcaoRemoveCardCentroAux (updateTAB (a:b:c:d) (x,1)) (x+1,1) 
                                                else funcaoRemoveCardCentroAux (a:b:c:d) (x+1,1) 





--
-- == FUNÇÕES AUXILIARES DE /funcaoRemoveCardLados/
--
-- |Função auxiliar de /funcaoRemoveCardLados/ que seleciona cada cardinal que está no lado esquerdo (L - Left) do tabuleiro e, caso esteja rodeado de cardinais "#" /e///ou/ "?", substitui-o por um ponto de interrogação "?".
funcaoRemoveCardLadosL :: [String] -> Int -> Bool
funcaoRemoveCardLadosL tabuleiro x = if ( (((charNaPosicao tabuleiro (1,x-1))=='#')||((charNaPosicao tabuleiro (1,x-1))=='?')) && (((charNaPosicao tabuleiro (1,x))=='#')||((charNaPosicao tabuleiro (1,x))=='?')) && (((charNaPosicao tabuleiro (1,x+1))=='#')||((charNaPosicao tabuleiro (1,x+1))=='?')) ) then True
                                        else False

-- |Função auxiliar de /funcaoRemoveCardLados/ que seleciona cada cardinal que está no lado direito (R - Right) do tabuleiro e, caso esteja rodeado de cardinais "#" /e///ou/ "?", substitui-o por um ponto de interrogação "?".
funcaoRemoveCardLadosR :: [String] -> Int -> Bool
funcaoRemoveCardLadosR tabuleiro x = if ( (((charNaPosicao tabuleiro ((length (head tabuleiro))-2,x-1))=='#')||((charNaPosicao tabuleiro ((length (head tabuleiro))-2,x-1))=='?')) && (((charNaPosicao tabuleiro ((length (head tabuleiro))-2,x))=='#')||((charNaPosicao tabuleiro ((length (head tabuleiro))-2,x))=='?')) && (((charNaPosicao tabuleiro ((length (head tabuleiro))-2,x+1))=='#')||((charNaPosicao tabuleiro ((length (head tabuleiro))-2,x+1))=='?')) ) then True
                                        else False

-- |Função auxiliar de /funcaoRemoveCardLados/ que seleciona cada cardinal que está na primeira linha (U - Up) do tabuleiro e, caso esteja rodeado de cardinais "#" /e///ou/ "?", substitui-o por um ponto de interrogação "?".
funcaoRemoveCardLadosU :: [String] -> Int -> Bool
funcaoRemoveCardLadosU tabuleiro x = if ( (((charNaPosicao tabuleiro (x-1,1))=='#')||((charNaPosicao tabuleiro (x-1,1))=='?')) && (((charNaPosicao tabuleiro (x,1))=='#')||((charNaPosicao tabuleiro (x,1))=='?')) && (((charNaPosicao tabuleiro (x+1,1))=='#')||((charNaPosicao tabuleiro (x+1,1))=='?')) ) then True
                                        else False

-- |Função auxiliar de /funcaoRemoveCardLados/ que seleciona cada cardinal que está na última fila (D - Down) do tabuleiro e, caso esteja rodeado de cardinais "#" /e///ou/ "?", substitui-o por um ponto de interrogação "?".
funcaoRemoveCardLadosD :: [String] -> Int -> Bool
funcaoRemoveCardLadosD tabuleiro x = if ( (((charNaPosicao tabuleiro (x-1,(length tabuleiro)-2))=='#')||((charNaPosicao tabuleiro (x-1,(length tabuleiro)-2))=='?')) && ((((charNaPosicao tabuleiro (x,(length tabuleiro)-2)))=='#')||(((charNaPosicao tabuleiro (x,(length tabuleiro)-2)))=='?')) && (((charNaPosicao tabuleiro ((x+1,(length tabuleiro)-2)))=='#')||((charNaPosicao tabuleiro ((x+1,(length tabuleiro)-2)))=='?')) ) then True
                                        else False



-- |Função auxiliar de /funcaoRemoveCardLados/ e /funcaoRemoveCardCantos/ que dado um tabuleiro (em lista de /String/) e a coordenada de uma caixa (par de /Int/) devolve o tabuleiro com a caixa inserida na coordenada referida.
--
-- >>> updateTAB ["#####","#. .#","#   #","#   #","#####"] (2,2)
-- ["#####","#. .#","# # #","#   #","#####"]
--
-- >>> updateTAB ["#####","#. .#","#   #","#   #","#####"] (4,1)
-- ["#####","#. .#","#   #","#   #","#####"]
--
-- == SEPARAR TABULEIRO DAS COORDENADAS
--
updateTAB :: [String] -> (Int,Int) -> [String]
updateTAB ((x:y):z) (a,0) = (updateTABAux (x:y) a) : z
updateTAB ((x:y):z) (a,b) = (x:y) : updateTAB (z) (a,b-1)




-- |Função auxiliar de /FuncaoPlantaMelhor/ que separa o tabuleiro das coordenadas. Dá apenas o tabuleiro.
--
-- >>> sepTABCoord ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- ["#####","#. .#","#   #","#   #","#####"]
--
sepTABCoord :: [String] -> [String]
sepTABCoord tabuleiro = sepTABCoordAux (tabuleiro) 1 where
    sepTABCoordAux tabuleiro 1 = (head tabuleiro):sepTABCoordAux (tail tabuleiro) 0
    sepTABCoordAux [] 0 = []
    sepTABCoordAux tabuleiro 0 = if ((nub1 (head tabuleiro)) == "#") then (head tabuleiro):[]
                                    else (head tabuleiro):(sepTABCoordAux (tail tabuleiro) 0)

-- |Separação das coordenadas do tabuleiro. Dá apenas as coordenadas da localização do Sokoban e das caixas.
--
-- >>> sepTabCOORD ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- ["2 1","1 2","3 2"]
--
sepTabCOORD :: [String] -> [String]
sepTabCOORD tabuleiro = sepTabCOORDAux (tabuleiro) 1 where
    sepTabCOORDAux tabuleiro 1 = sepTabCOORDAux (tail tabuleiro) 0
    sepTabCOORDAux [] 0 = []
    sepTabCOORDAux tabuleiro 0 = if ((nub1 (head tabuleiro)) == "#") then (tail tabuleiro)
                                    else sepTabCOORDAux (tail tabuleiro) 0

-- |Separa as coordenadas uma a uma.
--
-- >>> sepCOORD1 ["2 1","1 2","3 2"]
-- ["2","1","1","2","3","2"]
--
sepCOORD1 :: [String] -> [String]
sepCOORD1 (x:[]) = words x
sepCOORD1 (x:y)  = (words x) ++ (sepCOORD1 y)

-- |Após separar as coordenadas uma a uma, juntamo-las em pares de inteiros.
--
-- >>> sepCOORD2 ["2","1","1","2","3","2"]
-- [(2,1),(1,2),(3,2)]
--
sepCOORD2 :: [String] -> [(Int,Int)]
sepCOORD2 (x:y:[]) = [((read x :: Int),(read y :: Int))]
sepCOORD2 (x:y:z)  = [((read x :: Int),(read y :: Int))] ++ sepCOORD2 z

-- |Função auxiliar de /funcaoPlantaMelhor/ que junta as três anteriores funções e executa todas apenas nesta.
--
-- >>> sepTabCOORDpares ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- [(2,1),(1,2),(3,2)]
--
-- == FUNÇÕES AUXILIARES
--
sepTabCOORDpares :: [String] -> [(Int,Int)]
sepTabCOORDpares tabuleiro = sepCOORD2 (sepCOORD1 (sepTabCOORD tabuleiro))



-- |Função auxiliar de /FuncaoPlantaMelhor/ que dado um tabuleiro e a coordenada de uma caixa, coloca essa caixa no tabuleiro, que fica representada pelo carater ""H"" caso a caixa se localize num espaço vazio ou pelo carater ""I"" caso fique em cima de qualquer outro carater, ou seja, o ponto.
--
-- >>> updateTABcaixas ["#####","#. .#","#   #","#   #","#####"] (1,2)
-- ["#####","#. .#","#H  #","#   #","#####"]
--
-- >>> updateTABcaixas ["#####","#. .#","#   #","#   #","#####"] (1,1)
-- ["#####","#I .#","#   #","#   #","#####"]
updateTABcaixas :: [String] -> (Int,Int) -> [String]
updateTABcaixas ((x:y):z) (a,0) = (updateTABcaixasAux (x:y) a) : z
updateTABcaixas ((x:y):z) (a,b) = (x:y) : updateTABcaixas (z) (a,b-1)

-- |Função auxiliar de /FuncaoPlantaMelhor/ que dado um tabuleiro e a coordenada do Sokoban, coloca-o no tabuleiro, na sua respetiva coordenada. O Sokoban está representado pelo carater "o".
--
-- >>> updateTABsokoban ["#####","#. .#","#   #","#   #","#####"] (1,1)
-- ["#####","#o .#","#   #","#   #","#####"]
--
-- >>> updateTABsokoban ["#####","#. .#","#   #","#   #","#####"] (3,2)
-- ["#####","#. .#","#  o#","#   #","#####"]

updateTABsokoban :: [String] -> (Int,Int) -> [String]
updateTABsokoban ((x:y):z) (a,0) = (updateTABsokobanAux (x:y) a) : z
updateTABsokoban ((x:y):z) (a,b) = (x:y) : updateTABsokoban (z) (a,b-1)

-- |Função auxiliar das funções auxiliares de /funcaoRemoveCardLados/ que dado um tabuleiro e as coordenadas de uma caixa ou Sokoban diz o que está nessa coordenada. Se estiver vazio, está disponível para receber uma caixa ou o Sokoban.
--
-- >>> charNaPosicao ["#####","#. .#","#   #","#   #","#####"] (2,2)
-- ' '
--
-- >>> charNaPosicao ["#####","#. .#","#   #","#   #","#####"] (4,1)
-- '#'
--
charNaPosicao :: [String] -> (Int,Int) -> Char
charNaPosicao ((x:y):z) (0,0) = x
charNaPosicao ((x:[]):z) (a,0) = '!'
charNaPosicao ((x:y):z) (a,0) = charNaPosicao ((y):z) (a-1,0)
charNaPosicao ((x:y):[]) (a,b) = '!'
charNaPosicao ((x:y):z) (a,b) = charNaPosicao (z) (a,b-1)

-- |Função auxiliar de /updateTAB/.
--
-- >>> updateTABAux "#   #" 1
-- "##  #"
--
updateTABAux :: String -> Int -> String
updateTABAux (x:y) 0 = "?" ++ y
updateTABAux (x:y) posicao = [x] ++ updateTABAux y (posicao-1)

-- |Função auxiliar de /updateTABcaixas/ que define a letra a colocar na coordenada da caixa. Se no espaço estiver o carater " ", coloca ""H"". Se não for vazio, coloca o carater ""I"".
updateTABcaixasAux :: String -> Int -> String
updateTABcaixasAux (x:y) 0 = if (x==' ') then "H" ++ y
                                else "I" ++ y
updateTABcaixasAux (x:y) posicao = [x] ++ updateTABcaixasAux y (posicao-1)


-- |Função auxiliar de /updateTABsokoban/ que lê a coordenada do Sokoban e o coloca no tabuleiro representado pelo carater "o".
updateTABsokobanAux :: String -> Int -> String
updateTABsokobanAux (x:y) 0 = "o" ++ y
updateTABsokobanAux (x:y) posicao = [x] ++ updateTABsokobanAux y (posicao-1)

-- |Função auxiliar de /funcaoTiraInterrogacao/.
--
-- == FUNÇÕES PREDEFINIDAS
--
funcaoTiraInterrogacaoAux :: String -> String
funcaoTiraInterrogacaoAux (x:[]) = if (x=='?') then ' ':[]
                                    else x:[]
funcaoTiraInterrogacaoAux (x:y) = if (x=='?') then ' ':(funcaoTiraInterrogacaoAux y)
                                    else x:(funcaoTiraInterrogacaoAux y)


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