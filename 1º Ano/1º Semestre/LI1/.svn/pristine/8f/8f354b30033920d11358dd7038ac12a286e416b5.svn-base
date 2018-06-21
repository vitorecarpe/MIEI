{-|
Módulo: Main

Título: Problema D

Descrição: Módulo Haskell que dado um tabuleiro com as coordenadas do Sokoban, caixas e um conjunto de movimentos devolva o estado do jogo.

Copyright: Francisco Oliveira /a78416@alunos.uminho.pt/   | | |   Vitor Peixoto /a79175@alunos.uminho.pt/

Resumo: Módulo contendo definições Haskell do Problema D da 2ª Fase do Projeto da disciplina de Laboratórios de Informática I. Este módulo contém funções que movimentam o Sokoban através da introdução de um tabuleiro com coordenadas do Sokoban e de caixas e de uma /String/ com um conjunto de carateres que dão os comandos das direções que o Sokoban deve tomar. Se o conjunto de comandos dado levar à vitória, devolve "FIM <tick_count>" onde <tick_count> representa o número de movimentos funcionais efetuados. Os movimentos são representados por U,D,R ou L para Up (Cima), Down (Baixo), Right (Direita) ou Left (Esquerda) respetivamente. O movimento do Sokoban tem as mesmas limitações que na Tarefa C.

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
          putStr (outStr (tarefa4 (inStr inp)))

-- |Função que dado um tabuleiro retribui o número de movimentos funcionais (exclui comandos em que o Sokoban não se moveu) e se ganhou o jogo ou não.
--
-- >>> tarefa4 ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2","","RUDLLU",""]
-- ["FIM 6"]
--
-- >>> tarefa4 ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2","RUDLL",""]
-- ["INCOMPLETO 5"]
--
-- == FUNÇÕES DE SEQUÊNCIA DE COMANDOS
--
tarefa4 :: [String] -> [String]
tarefa4 tabuleiro = rinceAndRepeat (tabuleiro) (last (init tabuleiro))




-- |Função que inserido um tabuleiro e uma sucessão de comandos executa os comandos até acabar a sequência de comandos ou até o jogo estar ganho, ou seja, até não haver nenhum "H" no tabuleiro. É devolvido o tabuleiro final, ganho ou não.
--
-- >>> rinceAndRepeat ["#####","#    #","#  .#","#####","1 1","2 1",""] "R"
-- ["FIM 1"]
--
-- >>> rinceAndRepeat ["#####","#    #","#  .#","#####","1 1","2 1",""] "UDUR"
-- ["INCOMPLETO 4"]
rinceAndRepeat :: [String] -> String -> [String]
rinceAndRepeat tabuleiro direcoes = rinceAndRepeatAux (funcaoPlantaMelhor tabuleiro) (head (sepTabCOORDpares tabuleiro)) (detetaPontos (sepTABCoord tabuleiro)) 0 direcoes where
    rinceAndRepeatAux tabuleiro coordsoko pontos ticks [] = [(vitoria tabuleiro)++" "++(show ticks)]
    rinceAndRepeatAux tabuleiro coordsoko pontos ticks direcoes = if ((vitoria tabuleiro)=="FIM") then [(vitoria tabuleiro)++" "++(show ticks)]
                                                              else ( rinceAndRepeatAux ( updateTABpontos (fst(fst(tarefa3' tabuleiro coordsoko ([head direcoes])))) (pontos) ) (snd(fst(tarefa3' tabuleiro coordsoko ([head direcoes])))) (pontos) (ticks+(snd(tarefa3' tabuleiro coordsoko ([head direcoes])))) (tail direcoes) )


-- |Função que procura pontos e guarda as suas coordenadas.
--
-- >>> detetaPontos ["#####","# H #","#oI.#","#####"]
-- [(2,1),(3,1)]
--
-- >>> detetaPontos ["#####","#   #","#oH.#","#####"]
-- [(3,1)]
detetaPontos :: [String] -> [(Int,Int)]
detetaPontos tabuleiro = detetaPontosAux (reverse tabuleiro) (0,0) where
  detetaPontosAux ((a:[]):[]) (x,y)= if ( (a=='.')||(a=='I') ) then (x,y):[]
                                      else []
  detetaPontosAux ((a:b):[]) (x,y) = if ( (a=='.')||(a=='I') ) then (x,y):detetaPontosAux [b] (x+1,y)
                                      else detetaPontosAux [b] (x+1,y)
  detetaPontosAux ((a:[]):c) (x,y) = if ( (a=='.')||(a=='I') ) then (x,y):detetaPontosAux (c) (0,y+1)
                                      else detetaPontosAux (c) (0,y+1)
  detetaPontosAux ((a:b):c) (x,y)  = if ( (a=='.')||(a=='I') ) then (x,y):detetaPontosAux (b:c) (x+1,y)
                                     else detetaPontosAux (b:c) (x+1,y)

-- |Função que recebe um tabuleiro e as coordenadas dos pontos e os coloca nas respetivas coordenadas. Se o espaço estiver ocupado por um carater qualquer, não coloca o ponto, no entanto ele está lá, debaixo desse carater. Se estiver vazio, coloca um ponto.
--
-- >>> updateTABpontos ["#####","#   #","# o #","#####"] [(1,1)]
-- ["#####","#   #","#.o #","#####"]
--
-- >>> updateTABpontos ["#####","#   #","#o  #","#####"] [(1,1)]
-- ["#####","#   #","#o  #","#####"]
updateTABpontos :: [String] -> [(Int,Int)] -> [String]
updateTABpontos tabuleiro [] = tabuleiro
updateTABpontos tabuleiro (x:y) = updateTABpontos (reverse(updateTABponto (reverse tabuleiro) x)) y


-- |Função que testa se o conjunto de comandos introduzidos levou à vitória, ou seja, se não há nenhum carater "H" no tabuleiro. Caso haja, ainda não está terminado o jogo, ainda há caixas por meter nos pontos.
--
-- >>> vitoria ["#####","#   #","#oH.#","#####"]
-- "INCOMPLETO"
--
-- >>> vitoria ["#####","#   #","# oI#","#####"]
-- "FIM"
vitoria :: [String] -> String
vitoria ((x:[]):[])= if (x=='H') then "INCOMPLETO"
                      else "FIM"
vitoria ((x:y):[]) = if (x=='H') then "INCOMPLETO"
                      else vitoria [y]
vitoria ((x:[]):z) = if (x=='H') then "INCOMPLETO"
                      else vitoria z
vitoria ((x:y):z)  = if (x=='H') then "INCOMPLETO"
                      else vitoria (y:z)


-- |Função importada da Tarefa C que dado um tabuleiro com as coordenadas do Sokoban, caixas e um comando de movimentos (U,D,R,L) devolve a nova coordenada do Sokoban resultante desse movimento.
--
tarefa3' :: [String] -> (Int,Int) -> String -> ( ( [String],(Int,Int) ),Int )
tarefa3' tabuleiro (a,b) direcao | direcao=="U" = movORnot tabuleiro (a,b) (0,1)
                                 | direcao=="D" = movORnot tabuleiro (a,b) (0,-1)
                                 | direcao=="L" = movORnot tabuleiro (a,b) (-1,0)
                                 | direcao=="R" = movORnot tabuleiro (a,b) (1,0)
                                 | otherwise = error "Comando nao aceite"

-- |Função que movimenta o Sokoban através da introdução de um tabuleiro com as coordenadas do Sokoban e das coordenadas definidas para movimentar o Sokoban ((0,1) para cima, (0,-1) para baixo, (1,0) para a direita, (-1,0) para a esquerda) e devolve o novo tabuleiro com as alterações efetuadas (ou não) e a coordenada do Sokoban. Devolve ainda 1 ou 0 que significa se o comando inserido efetuou alguma alteração no tabuleiro. Este dado será usado para o <tick_count>.
--
-- >>> movORnot ["#####","#   #","#oH.#","#####"] (1,1) (-1,0)
-- ((["#####","#   #","#oH.#","#####"],(1,1)),0)
--
-- >>> >>> movORnot ["#####","#   #","#oH.#","#####"] (1,1) (0,1)
-- ((["#####","#o  #","# H.#","#####"],(1,2)),1) 
movORnot :: [String] -> (Int,Int) -> (Int,Int) -> ( ( [String],(Int,Int) ),Int )
movORnot tabuleiro (a,b) (c,d) | (charNaPosicao (reverse tabuleiro) (a+c,b+d))==' ' = ((movTOempty tabuleiro (a,b) (c,d),(a+c,b+d)),1)
                               | (charNaPosicao (reverse tabuleiro) (a+c,b+d))=='.' = ((movTOempty tabuleiro (a,b) (c,d),(a+c,b+d)),1)
                               | (charNaPosicao (reverse tabuleiro) (a+c,b+d))=='#' = ((tabuleiro,(a,b)),0)
                               | ( ((charNaPosicao (reverse tabuleiro) (a+c,b+d))=='H') || ((charNaPosicao (reverse tabuleiro) (a+c,b+d))=='I') ) = if ( ((charNaPosicao (reverse tabuleiro) (a+2*c,b+2*d))=='H')||(charNaPosicao (reverse tabuleiro) (a+2*c,b+2*d))=='I'||(charNaPosicao (reverse tabuleiro) (a+2*c,b+2*d))=='#' ) then ((tabuleiro,(a,b)),0)
                                                                                      else ((movTObox tabuleiro (a,b) (c,d),(a+c,b+d)),1)
                               | otherwise = error "Error"

-- |Função que dado o tabuleiro, a coordenada do Sokoban e o comando do movimento, devolve o tabuleiro com o Sokoban na sua nova posição. Esta função é auxiliar de /movORnot/ pelo que só vai ser usada para mover o Sokoban quando o movimento o leva para uma coordenada vazia ou um ponto.
--
-- >>> movTOempty ["#####","#   #","#oH.#","#####"] (1,1) (0,1)
-- ["#####","#o  #","# H.#","#####"]
movTOempty :: [String] -> (Int,Int) -> (Int,Int) -> [String]
movTOempty tabuleiro (a,b) (c,d) = reverse ( updateTABempty ( updateTABsokoban (reverse tabuleiro) (a+c,b+d) ) (a,b) ) 

-- |Função que dado o tabuleiro, a coordenada do Sokoban e o comando do movimento, devolve o tabuleiro com o Sokoban e a caixa que empurrou na sua nova posição. Esta função é auxiliar de /movORnot/ pelo que só vai ser usada para mover o Sokoban quando o movimento o leva a empurrar uma caixa.
--
-- >>> movTObox ["#####","#   #","#oH.#","#####"] (1,1) (1,0)
-- ["#####","#   #","# oI#","#####"]
movTObox :: [String] -> (Int,Int) -> (Int,Int) -> [String]
movTObox tabuleiro (a,b) (c,d) = movTOempty ( reverse (updateTABcaixas (reverse tabuleiro) (a+2*c,b+2*d))  ) (a,b) (c,d)





-- |Função auxiliar de /movORnot/ que dado um tabuleiro e as coordenadas de uma caixa ou Sokoban diz o que está nessa coordenada. Se estiver vazio, está disponível para receber uma caixa ou o Sokoban.
--
-- >>> charNaPosicao ["#####","#. .#","#   #","#   #","#####"] (2,2)
-- ' '
--
-- >>> charNaPosicao ["#####","#. .#","#   #","#   #","#####"] (4,1)
-- '#'
--
-- == FUNÇÃO /PLANTAMELHOR/ E SUAS AUXILIARES (UPDATE)
--
charNaPosicao :: [String] -> (Int,Int) -> Char
charNaPosicao ((x:y):z) (0,0) = x
charNaPosicao ((x:[]):z) (a,0) = '!'
charNaPosicao ((x:y):z) (a,0) = charNaPosicao ((y):z) (a-1,0)
charNaPosicao ((x:y):[]) (a,b) = '!'
charNaPosicao ((x:y):z) (a,b) = charNaPosicao (z) (a,b-1)







-- | Dado o tabuleiro com as coordenadas (/String/) devolve o tabuleiro com o Sokoban nas respetivas coordenadas e representado pelo "o", as caixas também nas respetivas coordenadas representadas por ""H"" e as caixas quando estão em cima do ponto são representadas por ""I"".
--
-- >>> funcaoPlantaMelhor ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- ["#####","#. .#","#H H#","# o #","#####"]
--
-- >>> funcaoPlantaMelhor ["#####","#. .#","#   #","#   #","#####","2 2","1 2","3 3"," "] 
-- ["#####","#. I#","#Ho #","#   #","#####"]
--
-- == FUNÇÕES UPDATE
--
funcaoPlantaMelhor :: [String] -> [String]
funcaoPlantaMelhor tabuleiro = funcaoPlantaMelhorAux (reverse (sepTABCoord tabuleiro)) (1) (sepTabCOORDpares tabuleiro) where
    funcaoPlantaMelhorAux tabuleiro 1 ((x,y):[]) = ["O Sokoban nao pode jogar sem caixas!"]
    funcaoPlantaMelhorAux tabuleiro 1 ((x,y):z) = funcaoPlantaMelhorAux (updateTABsokoban tabuleiro (x,y)) 0 (z)
    funcaoPlantaMelhorAux tabuleiro 0 ((x,y):[]) = (reverse (updateTABcaixas tabuleiro (x,y)))
    funcaoPlantaMelhorAux tabuleiro 0 ((x,y):z) = funcaoPlantaMelhorAux (updateTABcaixas tabuleiro (x,y)) 0 (z)



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
--
updateTABsokoban :: [String] -> (Int,Int) -> [String]
updateTABsokoban ((x:y):z) (a,0) = (updateTABsokobanAux (x:y) a) : z
updateTABsokoban ((x:y):z) (a,b) = (x:y) : updateTABsokoban (z) (a,b-1)

-- |Função que após a movimentação do Sokoban, coloca um espaço vazio na sua posição original.
--
-- == SEPARAR TABULEIRO DAS COORDENADAS
--
updateTABempty :: [String] -> (Int,Int) -> [String]
updateTABempty ((x:y):z) (a,0) = (updateTABemptyAux (x:y) a) : z
updateTABempty ((x:y):z) (a,b) = (x:y) : updateTABempty (z) (a,b-1)






-- |Função auxiliar de /FuncaoPlantaMelhor/ que separa o tabuleiro das coordenadas. Dá apenas o tabuleiro.
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
sepTabCOORD ([]:[]) = []
sepTabCOORD ((x:y):[]) = []
sepTabCOORD ((x:y):z) = if (algarismos x) then (x:y):sepTabCOORD z
                            else sepTabCOORD z

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
sepCOORD2 (x:[]) = []
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





-- |Função auxiliar de /testeCOORDso2numeros/ que dada uma lista de /Char/ diz se essa lista tem apenas números. Qualquer outro tipo de carater devolve /Falso/.
--
-- >>> algarismos [1,3,5,a,6,b]
-- False
--
-- >>> algarismos [1,3,7,4,2]
-- True
--
algarismos :: Char -> Bool
algarismos a = if (a >= '0') && (a <= '9') then True
                else False

-- |Função auxiliar de /updateTABpontos/.
updateTABponto :: [String] -> (Int,Int) -> [String]
updateTABponto ((x:y):z) (a,0) = (updateTABpontoAux (x:y) a) : z
updateTABponto ((x:y):z) (a,b) = (x:y) : updateTABponto (z) (a,b-1)

-- |Função auxiliar de /updateTABponto/.
updateTABpontoAux :: String -> Int -> String
updateTABpontoAux (x:y) 0 = if (x==' ') then "." ++ y
                              else [x]++y
updateTABpontoAux (x:y) posicao = [x] ++ updateTABpontoAux y (posicao-1)

-- |Função auxiliar de /updateTABempty/.
updateTABemptyAux :: String -> Int -> String
updateTABemptyAux (x:y) 0 = " " ++ y
updateTABemptyAux (x:y) posicao = [x] ++ updateTABemptyAux y (posicao-1)

-- |Função auxiliar de /updateTABcaixas/ que define a letra a colocar na coordenada da caixa. Se no espaço estiver o carater " ", coloca ""H"". Se não for vazio, coloca o carater ""I"".
updateTABcaixasAux :: String -> Int -> String
updateTABcaixasAux (x:y) 0 = if (x==' ') then "H" ++ y
                                else "I" ++ y
updateTABcaixasAux (x:y) posicao = [x] ++ updateTABcaixasAux y (posicao-1)

-- |Função auxiliar de /updateTABsokoban/ que lê a coordenada do Sokoban e o coloca no tabuleiro representado pelo carater "o".
updateTABsokobanAux :: String -> Int -> String
updateTABsokobanAux (x:y) 0 = "o" ++ y
updateTABsokobanAux (x:y) posicao = [x] ++ updateTABsokobanAux y (posicao-1)