{-|
Módulo: Main

Título: Problema A

Descrição: Módulo Haskell que dado um tabuleiro, efetua diversos testes ao tabuleiro de jogo.

Copyright: Francisco Oliveira /a78416@alunos.uminho.pt/   | | |   Vitor Peixoto /a79175@alunos.uminho.pt/

Resumo: Módulo contendo definições Haskell do Problema A da 1ª Fase do Projeto da disciplina de Laboratórios de Informática I. Este módulo contém funções que testam a funcionalidade do tabuleiro e os erros lógicos que impossibilitem a jogabilidade. Estes diversos testes estão juntos na função  "main", no entanto durante este período de testes é feito pela função "teste".

== FUNÇÕES DEFINIDAS NO ESQUELETO INICIAL
-}

module Main where

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
outStr t = unlines t

-- |Função principal (/main/).
main = do inp <- getContents
          putStr (outStr (tarefa1 (inStr inp)))

-- |Função que dado o tabuleiro diz se está funcional (/OK/) através de um conjunto de funções que o testam. Se não estiver funcional apresenta a linha do primeiro erro que aparecer.
--
-- == FUNÇÃO /TESTE/
--
tarefa1 :: [String] -> [String]
tarefa1 tabuleiro = if (removeZeros (juntarNumeros tabuleiro)==[]) then "OK":[]
                    else (show (minimum (removeZeros (juntarNumeros tabuleiro)))):[]

-- |Função usada para testar o tabuleiro (simulador da função /main/). Se não houver erros, devolve /OK/. Se tiver algum erro, devolve a linha onde se encontra o primeiro erro.
--
-- >>> teste "correto1.txt"
-- OK
--
-- >>> teste "errado1.txt"
-- 2
--
-- == SUBPROGRAMAS DA FUNÇÃO /TAREFA1/
--
teste :: String -> IO ()
teste file = do inp <- readFile file
                putStr (outStr (tarefa1 (inStr inp)))


-- |Junta os outputs dos diferentes testes feitos ao tabuleiro. Se der uma lista de carateres 0 então o tabuleiro está funcionar. Caso apareça qualquer outro carater que não o 0, então terá um erro na função correspondente à ordem do número e na linha do tabuleiro correspondente a esse número.
--
-- >>> juntarNumeros ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "] 
-- ["0","0","0","0","0","0"]
--
-- Não tem erro.
--
-- >>> juntarNumeros ["#####","#. .#","# k #","#   #","#####","2 1","1 2","3 2"," "]
-- ["3","0","0","0","0","0"]
--
-- Tem erro na função /testeInput/ na linha 3 do tabuleiro.
juntarNumeros :: [String] -> [Int]
juntarNumeros tabuleiro = if ((testeFechada tabuleiro)==0) then (testeInput tabuleiro):(testeFechada tabuleiro):(testeNumeroCaixas tabuleiro):(testeTamanhodasLinhas tabuleiro):(testeCOORDso2numeros tabuleiro):(testePosicaoDasCOORD tabuleiro):[]
                            else (testeInput tabuleiro):(testeFechada tabuleiro):(testeTamanhodasLinhas tabuleiro):[]

-- |Remove zeros da lista, porque zero representa ausência de erros.
--
-- >>> removeZeros ["0","0","0","0","0"]
-- []
--
-- >>> removeZeros ["0","2","0","0","0"]
-- [2]
--
-- == TESTES AO TABULEIRO
--
removeZeros :: [Int] -> [Int]
removeZeros x = if ((elem 0 x)==True) then removeZeros (delete1 0 x)
                else x



-- |Verificar se apenas existem espaços, asteriscos ou pontos no tabuleiro principal. 0 representa ausência de erros no tabuleiro. Qualquer outro número representa erro nessa linha. Isto aplica-se aos outros testes ao tabuleiro. 
--
-- >>> testeInput ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- 0
-- >>> testeInput ["##&##","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- 1
testeInput :: [String] -> Int
testeInput tabuleiro = testeInputAux (sepTABCoord(tabuleiro)) (length (sepTABCoord(tabuleiro))) 1 where
    testeInputAux _ 0 _ = 0
    testeInputAux ([]:z) a linha = testeInputAux z (a-1) (linha+1)
    testeInputAux ((x:y):z) a linha = if (x=='#' || x==' ' || x=='.') then testeInputAux ((y):z) a linha
                                         else linha

-- |Verificar se o tabuleiro está completamente rodeado de asteriscos, ou seja, se está fechado e não há local por onde possa haver fuga.
--
-- >>> testeFechada ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- 0
-- >>> testeFechada ["#####","#. .#","    #","#   #","#####","2 1","1 2","3 2"," "]
-- 3
testeFechada :: [String] -> Int
testeFechada tabuleiro = testeFechadaAux (sepTABCoord tabuleiro) 1 1 ((length tabuleiro)-1) (length (sepTabCOORD tabuleiro)) where
    testeFechadaAux tabuleiro 1 linha linhastotal linhasCOORD = if ((nub1 (head tabuleiro)) == "#") then (testeFechadaAux (tail tabuleiro) 0 (linha+1) linhastotal linhasCOORD)
                                                                 else linha
    testeFechadaAux tabuleiro 0 linha linhastotal linhasCOORD | (linha<(linhastotal-linhasCOORD)) = if ( ((head (head tabuleiro)) == '#') && ((last (head tabuleiro)) == '#') ) then (testeFechadaAux (tail tabuleiro) 0 (linha+1) linhastotal linhasCOORD)
                                                                                                      else linha
                                                              | otherwise = if ((nub1 (head tabuleiro)) == "#") then 0
                                                                             else linha

-- |Verificar se o número de pontos é exatamente igual ao número de caixas (dadas pelas coordenadas) no tabuleiro.
--
-- >>> testeNumeroCaixas ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- 0
-- >>> testeNumeroCaixas ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2","2 2"," "]
-- 9
-- >>> testeNumeroCaixas ["#####","#. .#","#   #","#   #","#####","2 1","1 2"," "]
-- 8
testeNumeroCaixas :: [String] -> Int
testeNumeroCaixas tabuleiro = testeNumeroCaixasAux (sepTABCoord tabuleiro) (0) (length (sepTabCOORD tabuleiro)) ((length tabuleiro)-1) (length (sepTABCoord tabuleiro)) where
    testeNumeroCaixasAux tabuleiro 0 0 nlinhastotal nlinhasTAB = nlinhasTAB+1
    testeNumeroCaixasAux [] pontos ncoordenadas nlinhastotal nlinhasTAB | (pontos == ncoordenadas-1) = 0
                                                                        | (pontos < ncoordenadas-1) = nlinhasTAB+1+pontos+1
                                                                        | (pontos > ncoordenadas-1) = nlinhastotal+1
    testeNumeroCaixasAux ((x:[]):[]) pontos ncoordenadas nlinhastotal nlinhasTAB = if (x == '.') then testeNumeroCaixasAux ([]) (pontos + 1) ncoordenadas nlinhastotal nlinhasTAB
                                                                                   else testeNumeroCaixasAux ([]) pontos ncoordenadas nlinhastotal nlinhasTAB
    testeNumeroCaixasAux ((x:y):[]) pontos ncoordenadas nlinhastotal nlinhasTAB = if (x == '.') then testeNumeroCaixasAux ([y]) (pontos + 1) ncoordenadas nlinhastotal nlinhasTAB
                                                                                   else testeNumeroCaixasAux ([y]) pontos ncoordenadas nlinhastotal nlinhasTAB
    testeNumeroCaixasAux ((x:[]):z) pontos ncoordenadas nlinhastotal nlinhasTAB = if (x == '.') then testeNumeroCaixasAux (z) (pontos + 1) ncoordenadas nlinhastotal nlinhasTAB
                                                                                   else testeNumeroCaixasAux (z) pontos ncoordenadas nlinhastotal nlinhasTAB
    testeNumeroCaixasAux ((x:y):z) pontos ncoordenadas nlinhastotal nlinhasTAB = if (x == '.') then testeNumeroCaixasAux ([y] ++ z) (pontos + 1) ncoordenadas nlinhastotal nlinhasTAB
                                                                                  else testeNumeroCaixasAux ([y] ++ z) pontos ncoordenadas nlinhastotal nlinhasTAB

-- |Verificar se a caixa é retângular, ou seja, se todas as linhas do tabuleiro têm o mesmo tamanho.
--
-- >>> testeTamanhodasLinhas ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- 0
--
-- >>> testeTamanhodasLinhas ["###","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- 1
--
testeTamanhodasLinhas :: [String] -> Int
testeTamanhodasLinhas tabuleiro = testeTamanhodasLinhasAux (length (head tabuleiro)) (tail (sepTABCoord tabuleiro)) 2 where
  testeTamanhodasLinhasAux linha1 (x:[]) linha = if (linha1 == (length x)) then 0
                                                 else linha
  testeTamanhodasLinhasAux linha1 (x:y) linha = if (linha1 == (length x)) then testeTamanhodasLinhasAux linha1 y (linha+1)
                                                 else linha

-- |Verificar se as coordenadas são bidimensionais e apenas números.
--
-- >>> testeCOORDso2numeros ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- 0
--
-- >>> testeCOORDso2numeros ["#####","#. .#","#   #","#   #","#####","2 1","1 2 3","3 2"," "]
-- 7
--
-- >>> testeCOORDso2numeros ["#####","#. .#","#   #","#   #","#####","2 1","1 2","x 2"," "]
-- 8
testeCOORDso2numeros :: [String] -> Int
testeCOORDso2numeros tabuleiro = testeCOORDso2numerosAux (sepTabCOORD tabuleiro) (length (sepTABCoord tabuleiro)+1) where
  testeCOORDso2numerosAux [] nlinhas = nlinhas
  testeCOORDso2numerosAux (x:[]) nlinhas = if ( ((length (words x))==2) && algarismos (head (words x)) && algarismos (last (words x)) ) then 0
                                          else nlinhas
  testeCOORDso2numerosAux (x:y) nlinhas = if ( ((length (words x))==2) && algarismos (head (words x)) && algarismos (last (words x)) ) then testeCOORDso2numerosAux y (nlinhas+1)
                                          else nlinhas

-- |Verifica se o Sokoban e as caixas estão em espaços vazios e dentro do tabuleiro. Não pode estar em cima de caixas, do Sokoban nem nas paredes ou fora do tabuleiro.
--
-- >>> testePosicaoDasCOORD ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- 0
--
-- >>> testePosicaoDasCOORD ["#####","#. .#","#   #","#   #","#####","2 1","1 2","4 2"," "]
-- 8
--
--
-- == SEPARAR TABULEIRO DE COORDENADAS
--
testePosicaoDasCOORD :: [String] -> Int
testePosicaoDasCOORD tabuleiro = testePosicaoDasCOORDaux (reverse (sepTABCoord tabuleiro)) ((length (sepTABCoord tabuleiro))+1) (sepTabCOORDpares tabuleiro) where
  testePosicaoDasCOORDaux tabuleiro linha [] = linha
  testePosicaoDasCOORDaux tabuleiro linha ((x,y):[]) = if ( ((charNaPosicao tabuleiro (x,y))==' ')||((charNaPosicao tabuleiro (x,y))==('.')) ) then 0
                                                        else linha
  testePosicaoDasCOORDaux tabuleiro linha ((x,y):z) = if ( ((charNaPosicao tabuleiro (x,y))==' ')||((charNaPosicao tabuleiro (x,y))==('.')) ) then testePosicaoDasCOORDaux (updateTAB tabuleiro (x,y)) (linha+1) (z)
                                                        else linha



-- |Separação do tabuleiro das coordenadas. Dá apenas o tabuleiro.
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
sepTabCOORD [] = []
sepTabCOORD [""] = []
sepTabCOORD ((x:y):z) = if (algarismos1 x) then init ((x:y):z) 
                            else sepTabCOORD z

-- |Juntamos as três anteriores funções e executamos todas apenas nesta.
--
-- >>> sepTabCOORDpares ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2"," "]
-- [(2,1),(1,2),(3,2)]
--
sepTabCOORDpares :: [String] -> [(Int,Int)]
sepTabCOORDpares tabuleiro = sepCOORD2 (sepCOORD1 (sepTabCOORD tabuleiro))

-- |Separa as coordenadas uma a uma.
--
-- >>> sepCOORD1 ["2 1","1 2","3 2"]
-- ["2","1","1","2","3","2"]
--
sepCOORD1 :: [String] -> [String]
sepCOORD1 [] = []
sepCOORD1 (x:[]) = words x
sepCOORD1 (x:y)  = (words x) ++ (sepCOORD1 y)

-- |Após separar as coordenadas uma a uma, juntamo-las aos pares.
--
-- >>> sepCOORD2 ["2","1","1","2","3","2"]
-- [(2,1),(1,2),(3,2)]
--
--
-- == FUNÇÕES AUXILIARES
--
sepCOORD2 :: [String] -> [(Int,Int)]
sepCOORD2 [] = []
sepCOORD2 (x:y:[]) = if (algarismos (x++y)) then [((read x :: Int),(read y :: Int))]
                      else []
sepCOORD2 (x:y:z)  = if (algarismos (x++y)) then [((read x :: Int),(read y :: Int))] ++ sepCOORD2 z
                      else []


-- |Função auxiliar de /testeCOORDso2numeros/ que dada uma lista de /Char/ diz se essa lista tem apenas números. Qualquer outro tipo de carater devolve /Falso/.
--
-- >>> algarismos [1,3,5,a,6,b]
-- False
--
-- >>> algarismos [1,3,7,4,2]
-- True
algarismos :: [Char] -> Bool
algarismos [] = True
algarismos (a:b) = if (a >= '0') && (a <= '9') then algarismos b
                    else False

-- |Função auxiliar de /sepTabCOORD/ que dada um /Char/ diz de esse caratér é numero. Caso seja, devolve /True/. Se não for número devolve /False/.
--
-- >>> algarismos1 '9'
-- True
--
-- >>> algarismos1 'b'
-- False
algarismos1 :: Char -> Bool
algarismos1 a = if (a >= '0') && (a <= '9') then True
                else False

-- |Função auxiliar de /testePosicaoDasCOORD/ que dado um tabuleiro e as coordenadas de uma caixa ou Sokoban diz o que está nessa coordenada. Se estiver vazio, está disponível para receber uma caixa ou o Sokoban.
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

-- |Função auxiliar de /testePosicaoDasCOORD/ que dado um tabuleiro (em lista de /String/) e a coordenada de uma caixa (par de /Int/) devolve o tabuleiro com a caixa inserida na coordenada referida.
--
-- >>> updateTAB ["#####","#. .#","#   #","#   #","#####"] (2,2)
-- ["#####","#. .#","# # #","#   #","#####"]
--
-- >>> updateTAB ["#####","#. .#","#   #","#   #","#####"] (4,1)
-- ["#####","#. .#","#   #","#   #","#####"]
--
updateTAB :: [String] -> (Int,Int) -> [String]
updateTAB ((x:y):z) (a,0) = (updateTABAux (x:y) a) : z
updateTAB ((x:y):z) (a,b) = (x:y) : updateTAB (z) (a,b-1)

-- |Função auxiliar de /updateTAB/.
--
-- >>> updateTABAux "#   #" 1
-- "##  #"
--
-- == FUNÇÕES PREDEFINIDAS
--
updateTABAux :: String -> Int -> String
updateTABAux (x:y) 0 = "#" ++ y
updateTABAux (x:y) posicao = [x] ++ updateTABAux y (posicao-1)


-- |Função predefinida que dado um elemento elimina a primeira ocorrência desse elemento numa lista.
--
-- >>> delete1 5 [1,3,5,6,9,5]
-- [1,3,6,9,5]
delete1 :: Eq a => a -> [a] -> [a]
delete1 a [] = []
delete1 a (x:y) = if (a==x) then y
                    else x:(delete1 a y)

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