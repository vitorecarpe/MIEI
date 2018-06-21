{-|
Módulo: Main

Título: Problema F

Descrição: Módulo /Haskell/ que, com auxílio da biblioteca /Gloss/ cria o jogo /Sokoban/ mas desta vez em modo gráfico 2D.

Copyright: Francisco Oliveira /a78416@alunos.uminho.pt/   | | |   Vitor Peixoto /a79175@alunos.uminho.pt/

Resumo: Módulo contendo definições Haskell do Problema F da 2ª Fase do Projeto da disciplina de Laboratórios de Informática I. Este módulo contém funções que desenha o tabuleiro numa janela gráfica com o auxílio de /Pictures/ importadas dos bitmaps. O mapa é atualizado a cada comando que lhe é dado pela função /joga/. Os comandos são dados na função /reageEvento/ que executa alterações na janela de acordo com comandos dados no teclado ou rato do computador.

== FUNÇÕES DEFINIDAS NO ESQUELETO INICIAL
-}

module Main where

import Graphics.Gloss
import Graphics.Gloss.Data.Picture          -- importar o tipo Picture
import Graphics.Gloss.Interface.Pure.Game   -- importar o tipo Event
import Graphics.Gloss.Data.Bitmap           -- importar os BMP
import Graphics.Gloss.Data.Color            -- importar Data para criar novas cores

-----------------------------------------------------------

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

-----------------------------------------------------------

-- |Função principal (/main/).
main = do
      boxoff <- loadBMP "BOXOFF.bmp"
      boxon <- loadBMP "BOXON.bmp"
      ponto <- loadBMP "PONTO.bmp"
      chao <- loadBMP "BACKGROUND.bmp"
      parede <- loadBMP "BRICK.bmp"
      mario <- loadBMP "PLAYER.bmp"
      undo <- loadBMP "UNDO.bmp"
      restart <- loadBMP "RESTART.bmp"
      sokoban <- loadBMP "SOKOBAN.bmp"
      victory <- loadBMP "VICTORY.bmp"
      help <- loadBMP "HELP.bmp"
      l1 <- loadBMP "L1.bmp"
      l2 <- loadBMP "L2.bmp"
      l3 <- loadBMP "L3.bmp"
      l4 <- loadBMP "L4.bmp"
      l5 <- loadBMP "L5.bmp"
      l6 <- loadBMP "L6.bmp"
      l7 <- loadBMP "L7.bmp"
      l8 <- loadBMP "L8.bmp"
      l9 <- loadBMP "L9.bmp"
      l0 <- loadBMP "L0.bmp"
      joga (tabuleiros,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],"",0,mapa0) desenhaJogo reageEvento

-- |Tipo que representa o estado do jogo.
type Estado = ([String],[Picture],String,Int,String)

-- |Função responsável pela atualização contínua do jogo a cada comando que é introduzido na função /reageEvento/.
--
-- == MAPAS DO JOGO
--
joga :: Estado -> (Estado -> Picture) -> (Event -> Estado -> Estado) -> IO ()
joga tabuleiro desenha reage = play (FullScreen (1366,768))
                                    (makeColorI 153 217 234 1) 40
                                    tabuleiro desenha reage (\time estado -> estado)


-- |Função /tabuleiros/ que aglomera os diversos níveis do jogo numa lista de /String/.
tabuleiros :: [String]
tabuleiros = [mapa0,mapa1,mapa2,mapa3,mapa4,mapa5,mapa6,mapa7,mapa8,mapa9]


mapa0 :: String
mapa0 = outStr ["#######","#     #","#    .#","#    .#","#######","1 1","2 2","2 1",""]
mapa1 :: String
mapa1 = outStr ["###################","#####   ###########","#####   ###########","#####   ###########","###      ##########","### # ## ##########","#   # ## #####  ..#","#               ..#","##### ### # ##  ..#","#####     #########","###################","11 2","2 3","5 3","5 6","5 8","7 6","7 7",""]
mapa2 :: String
mapa2 = outStr ["########","####   #","#   .. #","#     ##","#  #####","########","4 4","3 3","4 3",""]
mapa3 :: String
mapa3 = outStr ["######","#  ###","#  ###","#  ###","#  ###","#.   #","#  . #","#  ###","######","4 3","2 3","3 3",""]
mapa4 :: String
mapa4 = outStr ["########","###.####","##  ####","##     #","#.   .##","#### ###","####.###","########","6 4","3 3","4 3","5 4","3 5",""]
mapa5 :: String
mapa5 = outStr ["########","###  ###","###    #","###  . #","#... ###","###  ###","###  ###","########","4 6","3 5","4 4","4 5","3 2",""]
mapa6 :: String
mapa6 = outStr ["##################","##############..##","#.              ##","#.              ##","##  ##########  ##","##  #........#  ##","##  #........#  ##","##  #           ##","##  #           ##","##  ##############","##               #","##               #","##..##############","##################","16 3","6 2","6 3","10 2","10 3","14 2","14 3","3 4","2 4","3 8","2 8","8 6","10 7","11 7","13 6","14 8","15 8","12 10","12 11","8 10","8 11","4 10","4 11",""]
mapa7 :: String
mapa7 = outStr ["########","#     ##","# . . ##","#  #  ##","# . . ##","#  #  ##","# . . ##","#  #   #","# . .  #","#     ##","########","1 9","2 3","3 2","3 4","3 6","3 8","2 7","4 3","4 7",""]
mapa8 :: String
mapa8 = outStr ["#################","#              ##","# # ######     ##","# #         #  ##","# #         ## ##","# # #     ###...#","# #        ##...#","# ###      ##...#","#     # ## ##...#","#####   ## ##...#","#########     ###","#########     ###","#################","7 8","8 8","6 8","5 7","7 7","9 7","6 6","8 6","5 5","6 5","7 5","9 5","5 9","7 9","9 9","11 9",""]
-- | 
--
-- == FUNÇÕES DE DESENHO DOS MAPAS
--
mapa9 :: String
mapa9 = outStr ["###################","#######..    #   ##","######...        ##","#####....# # #  ###","####....# # #    ##","####...#  #    # ##","#  ## #          ##","#      ###  # # ###","#         # #   ###","###     # # # # ###","###       # # #####","###  # #####      #","###    #####   #  #","###  ########     #","###  ########    ##","###################","2 8","2 7","3 6","4 6","5 8","6 7","3 8","6 5","4 4","3 3","6 11","7 12","8 13","10 13","15 13","14 12","14 11",""]




-- |Funcão que desenha todas as /Pictures/ que devem aparecer na janela. No caso de /Pictures/ estáticas a sua inserção é basica. No entanto, no caso das /Pictures/ que integram o mapa de jogo, é necessário recorrer a uma função auxiliar complexa, a função /desenhaJogoAux/.
desenhaJogo :: Estado -> Picture
desenhaJogo (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = Pictures [ ( Translate (fromIntegral (length (head (init (inStr mapaatm)))*(-20))) (fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(-20))) (Pictures (desenhaJogoAux (reverse ( funcaoRemoveCard(tarefa4 ( (init (init (inStr mapaatm)))++[moves]++[""] ) )),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (0,0) ) ) ) ,
                                                                                                                                                                 (Translate (100) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(-20)))-40) undo),
                                                                                                                                                                 (Translate (-40) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(-20)))-40) restart),
                                                                                                                                                                 (Scale 1.1 1.1 (Translate (0) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+111) sokoban)),
                                                                                                                                                                 (Translate (0) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(-20)))-110) help),
                                                                                                                                                                 (Translate (-225) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l0),
                                                                                                                                                                 (Translate (-175) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l1),
                                                                                                                                                                 (Translate (-125) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l2),
                                                                                                                                                                 (Translate (-75) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l3),
                                                                                                                                                                 (Translate (-25) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l4),
                                                                                                                                                                 (Translate (25) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l5),
                                                                                                                                                                 (Translate (75) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l6),
                                                                                                                                                                 (Translate (125) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l7),
                                                                                                                                                                 (Translate (175) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l8),
                                                                                                                                                                 (Translate (225) ((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+30) l9)
                                                                                                                                                               ]

-- |Função /desenhaJogoAux/ que é a responsável por inserir na janela as /Pictures/ que constituem o mapa de jogo e que estão em constante mudança pela introdução de /moves/.
desenhaJogoAux (((a:[]):[]),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x,y)| (a=='H') =  (Translate (40*x+20) (40*y+20) boxoff ):(Translate (40*x+20) (40*y+20) (Color (makeColorI 200 200 200 0) (Circle 20))):[]
                                                                                                           | (a=='I') =  (Translate (40*x+20) (40*y+20) boxon  ):(Translate (40*x+20) (40*y+20) (Color (makeColorI 200 200 200 0) (Circle 20))):[]
                                                                                                           | (a=='.') =  (Translate (40*x+20) (40*y+20) ponto  ):(Translate (40*x+20) (40*y+20) (Color (makeColorI 200 200 200 0) (Circle 20))):[]
                                                                                                           | (a==' ') =  (Translate (40*x+20) (40*y+20) chao   ):(Translate (40*x+20) (40*y+20) (Color (makeColorI 200 200 200 0) (Circle 20))):[]
                                                                                                           | (a=='#') =  (Translate (40*x+20) (40*y+20) parede ):(Translate (40*x+20) (40*y+20) (Color (makeColorI 200 200 200 0) (Circle 20))):[]
                                                                                                           | (a=='o') =  (Translate (40*x+20) (40*y+20) mario  ):(Translate (40*x+20) (40*y+20) (Color (makeColorI 200 200 200 0) (Circle 20))):[]
                                                                                                           | otherwise = (Translate (40*x+20) (40*y+20) victory):(Translate (40*x+20) (40*y+20) (Color (makeColorI 200 200 200 0) (Circle 20))):[]
desenhaJogoAux (((a:b):[]),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x,y) | (a=='H') =  (Translate (40*x+20) (40*y+20) boxoff ):(desenhaJogoAux ((b:[]),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a=='I') =  (Translate (40*x+20) (40*y+20) boxon  ):(desenhaJogoAux ((b:[]),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a=='.') =  (Translate (40*x+20) (40*y+20) ponto  ):(desenhaJogoAux ((b:[]),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a==' ') =  (Translate (40*x+20) (40*y+20) chao   ):(desenhaJogoAux ((b:[]),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a=='#') =  (Translate (40*x+20) (40*y+20) parede ):(desenhaJogoAux ((b:[]),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a=='o') =  (Translate (40*x+20) (40*y+20) mario  ):(desenhaJogoAux ((b:[]),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | otherwise = (Translate (40*x+20) (40*y+20) victory):(desenhaJogoAux ((b:[]),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
desenhaJogoAux (((a:[]):c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x,y) | (a=='H') =  (Translate (40*x+20) (40*y+20) boxoff ):(desenhaJogoAux ((c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (0,y+1))
                                                                                                           | (a=='I') =  (Translate (40*x+20) (40*y+20) boxon  ):(desenhaJogoAux ((c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (0,y+1))
                                                                                                           | (a=='.') =  (Translate (40*x+20) (40*y+20) ponto  ):(desenhaJogoAux ((c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (0,y+1))
                                                                                                           | (a==' ') =  (Translate (40*x+20) (40*y+20) chao   ):(desenhaJogoAux ((c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (0,y+1))
                                                                                                           | (a=='#') =  (Translate (40*x+20) (40*y+20) parede ):(desenhaJogoAux ((c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (0,y+1))
                                                                                                           | (a=='o') =  (Translate (40*x+20) (40*y+20) mario  ):(desenhaJogoAux ((c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (0,y+1))
                                                                                                           | otherwise = (Translate (40*x+20) (40*y+20) victory):(desenhaJogoAux ((c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (0,y+1))
desenhaJogoAux (((a:b):c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x,y)  | (a=='H') =  (Translate (40*x+20) (40*y+20) boxoff ):(desenhaJogoAux ((b:c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a=='I') =  (Translate (40*x+20) (40*y+20) boxon  ):(desenhaJogoAux ((b:c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a=='.') =  (Translate (40*x+20) (40*y+20) ponto  ):(desenhaJogoAux ((b:c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a==' ') =  (Translate (40*x+20) (40*y+20) chao   ):(desenhaJogoAux ((b:c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a=='#') =  (Translate (40*x+20) (40*y+20) parede ):(desenhaJogoAux ((b:c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | (a=='o') =  (Translate (40*x+20) (40*y+20) mario  ):(desenhaJogoAux ((b:c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
                                                                                                           | otherwise = (Translate (40*x+20) (40*y+20) victory):(desenhaJogoAux ((b:c),[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help]) (x+1,y))
desenhaJogoAux _ (x,y) = (Circle 50):[]


-- |Funcão que lê os comandos introduzidos pelo utilizador quer através do teclado quer através de cliques com o rato. Estes comandos podem ser desde a seleção de níveis e retroceder um movimento, até próprio movimento do /Sokoban/, fazendo desta função extremamente importante.
reageEvento :: Event -> Estado -> Estado
reageEvento (EventKey (MouseButton LeftButton ) (Down) (modifiers) (x,y)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) | ( (x>=(80))&&(x<=(120))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(-20)))-60)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(-20)))-20))  ) = if (moves/=[]) then (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],init moves,lvl,mapaatm) -- RETROCEDE UMA JOGADA ATRAVÉS DO BOTÃO NO ECRÃ
                                                                                                                                                                                                                                                                                                                                                                                                                      else (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm)                            --
                                                                                                                                                                                                               | ( (x>=(-120))&&(x<=(40))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(-20)))-60)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(-20)))-20)) ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],lvl,mapaatm)                             -- REINICIA O JOGO ATRAVÉS DO BOTÃO NO ECRÃ
                                                                                                                                                                                                               | ( (x>=(-245))&&(x<=(-205))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],0,mapa0)                                -- LVL 0
                                                                                                                                                                                                               | ( (x>=(-195))&&(x<=(-155))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],1,mapa1)                                -- LVL 1
                                                                                                                                                                                                               | ( (x>=(-145))&&(x<=(-105))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],2,mapa2)                                -- LVL 2
                                                                                                                                                                                                               | ( (x>=(-95))&&(x<=(-55))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],3,mapa3)                                  -- LVL 3
                                                                                                                                                                                                               | ( (x>=(-45))&&(x<=(-5))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],4,mapa4)                                   -- LVL 4
                                                                                                                                                                                                               | ( (x>=(5))&&(x<=(45))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],5,mapa5)                                     -- LVL 5
                                                                                                                                                                                                               | ( (x>=(55))&&(x<=(95))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],6,mapa6)                                    -- LVL 6
                                                                                                                                                                                                               | ( (x>=(105))&&(x<=(145))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],7,mapa7)                                  -- LVL 7
                                                                                                                                                                                                               | ( (x>=(155))&&(x<=(195))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],8,mapa8)                                  -- LVL 8
                                                                                                                                                                                                               | ( (x>=(205))&&(x<=(245))&&(y>=(((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+10)))&&(y<=((fromIntegral (length (funcaoPlantaMelhor (init (inStr mapaatm)))*(20)))+50))  ) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],9,mapa9)                                  -- LVL 9
                                                                                                                                                                                                               | otherwise = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm)
reageEvento (EventKey (Char 'x'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],lvl,mapaatm)                             -- REINICIA O JOGO
reageEvento (EventKey (Char 'z'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = if (moves/=[]) then (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],init moves,lvl,mapaatm) -- RETROCEDE UMA JOGADA
                                                                                                                                                                                                                                   else (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm)   
reageEvento (EventKey (SpecialKey KeyUp       ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves++"U",lvl,mapaatm)                     -- MOVE SOKOBAN PARA CIMA
reageEvento (EventKey (SpecialKey KeyLeft     ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves++"L",lvl,mapaatm)                     -- MOVE SOKOBAN PARA A ESQUERDA
reageEvento (EventKey (SpecialKey KeyDown     ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves++"D",lvl,mapaatm)                     -- MOVE SOKOBAN PARA BAIXO
reageEvento (EventKey (SpecialKey KeyRight    ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves++"R",lvl,mapaatm)                     -- MOVE SOKOBAN PARA A DIREITA
reageEvento (EventKey (Char '0'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],0,mapa0) 
reageEvento (EventKey (Char '1'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],1,mapa1) 
reageEvento (EventKey (Char '2'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],2,mapa2) 
reageEvento (EventKey (Char '3'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],3,mapa3) 
reageEvento (EventKey (Char '4'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],4,mapa4) 
reageEvento (EventKey (Char '5'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],5,mapa5) 
reageEvento (EventKey (Char '6'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],6,mapa6) 
reageEvento (EventKey (Char '7'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],7,mapa7) 
reageEvento (EventKey (Char '8'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],8,mapa8)                            
reageEvento (EventKey (Char '9'               ) (Down) (_)         (pos)) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],[],9,mapa9) 
reageEvento (event                                                      ) (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm) = (tabuleiro,[boxoff,boxon,ponto,chao,parede,mario,undo,restart,sokoban,victory,help,l1,l2,l3,l4,l5,l6,l7,l8,l9,l0],moves,lvl,mapaatm)                          -- NÃO MOVE O SOKOBAN



-- |Função que dado um tabuleiro retribui esse mesmo tabuleiro com o Sokoban e as caixas colocadas nas respetivas coordenadas e após os movimentos introduzidos.
--
-- >>> tarefa4 ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2","RUDLLU",""]
-- ["#####","#I I#","#o  #","#   #","#####"]
--
-- >>> tarefa4 ["#####","#. .#","#   #","#   #","#####","2 1","1 2","3 2","RUDLL",""]
-- ["#####","#. I#","#H  #","#o  #","#####"]
--
-- == FUNÇÕES DE SEQUÊNCIA DE COMANDOS
--
tarefa4 :: [String] -> [String]
tarefa4 tabuleiro = rinceAndRepeat (tabuleiro) (last (init tabuleiro))



-- |Função que inserido um tabuleiro e uma sucessão de comandos executa os comandos até acabar a sequência de comandos ou até o jogo estar ganho, ou seja, até não haver nenhum "H" no tabuleiro. É devolvido o tabuleiro com o Sokoban e as caixas nas coordenadas após os movimentos, quer o jogo esteja ganho ou não.
--
-- >>> rinceAndRepeat ["#####","#    #","#  .#","#####","1 1","2 1",""] "R"
-- ["#####","#   #","# oI#","#####"]
--
-- >>> rinceAndRepeat ["#####","#    #","#  .#","#####","1 1","2 1",""] "UDUR"
-- ["#####","# o #","# H.#","#####"]
--
-- == FUNÇÕES IMPORTADAS DE OUTRAS TAREFAS
--
rinceAndRepeat :: [String] -> String -> [String]
rinceAndRepeat tabuleiro direcoes = rinceAndRepeatAux (funcaoPlantaMelhor tabuleiro) (head (sepTabCOORDpares tabuleiro)) (detetaPontos (sepTABCoord tabuleiro)) 0 direcoes where
    rinceAndRepeatAux tabuleiro coordsoko pontos ticks [] = tabuleiro
    rinceAndRepeatAux tabuleiro coordsoko pontos ticks direcoes = if ((vitoria tabuleiro)=="FIM") then tabuleiro
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


-- |Função auxiliar de /updateTABpontos/.
updateTABponto :: [String] -> (Int,Int) -> [String]
updateTABponto ((x:y):z) (a,0) = (updateTABpontoAux (x:y) a) : z
updateTABponto ((x:y):z) (a,b) = (x:y) : updateTABponto (z) (a,b-1)

-- |Função auxiliar de /updateTABponto/.
updateTABpontoAux :: String -> Int -> String
updateTABpontoAux (x:y) 0 = if (x==' ') then "." ++ y
                              else [x]++y
updateTABpontoAux (x:y) posicao = [x] ++ updateTABpontoAux y (posicao-1)



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
                               | otherwise = error "ripmov"

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
-- == FUNÇÃO /PLANTAMELHOR/ E SUAS AUXILIARES
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



-- |Função auxiliar de /funcaoPlantaMelhor/ que dado um tabuleiro e a coordenada de uma caixa, coloca essa caixa no tabuleiro, que fica representada pelo carater ""H"" caso a caixa se localize num espaço vazio ou pelo carater ""I"" caso fique em cima de qualquer outro carater, ou seja, o ponto.
--
-- >>> updateTABcaixas ["#####","#. .#","#   #","#   #","#####"] (1,2)
-- ["#####","#. .#","#H  #","#   #","#####"]
--
-- >>> updateTABcaixas ["#####","#. .#","#   #","#   #","#####"] (1,1)
-- ["#####","#I .#","#   #","#   #","#####"]
updateTABcaixas :: [String] -> (Int,Int) -> [String]
updateTABcaixas ((x:y):z) (a,0) = (updateTABcaixasAux (x:y) a) : z
updateTABcaixas ((x:y):z) (a,b) = (x:y) : updateTABcaixas (z) (a,b-1)

-- |Função auxiliar de /funcaoPlantaMelhor/ que dado um tabuleiro e a coordenada do Sokoban, coloca-o no tabuleiro, na sua respetiva coordenada. O Sokoban está representado pelo carater "o".
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
updateTABempty :: [String] -> (Int,Int) -> [String]
updateTABempty ((x:y):z) (a,0) = (updateTABemptyAux (x:y) a) : z
updateTABempty ((x:y):z) (a,b) = (x:y) : updateTABempty (z) (a,b-1)


-- |Função auxiliar de /updateTABcaixas/ que define a letra a colocar na coordenada da caixa. Se no espaço estiver o carater " ", coloca ""H"". Se não for vazio, coloca o carater ""I"".
updateTABcaixasAux :: String -> Int -> String
updateTABcaixasAux (x:y) 0 = if (x==' ') then "H" ++ y
                                else "I" ++ y
updateTABcaixasAux (x:y) posicao = [x] ++ updateTABcaixasAux y (posicao-1)

-- |Função auxiliar de /updateTABsokoban/ que lê a coordenada do Sokoban e o coloca no tabuleiro representado pelo carater "o".
updateTABsokobanAux :: String -> Int -> String
updateTABsokobanAux (x:y) 0 = "o" ++ y
updateTABsokobanAux (x:y) posicao = [x] ++ updateTABsokobanAux y (posicao-1)

-- |Função auxiliar de /funcaoRemoveCardLados/ e /funcaoRemoveCardCantos/ que dado um tabuleiro (em lista de /String/) e a coordenada de uma caixa (par de /Int/) devolve o tabuleiro com a caixa inserida na coordenada referida.
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
updateTABAux :: String -> Int -> String
updateTABAux (x:y) 0 = "?" ++ y
updateTABAux (x:y) posicao = [x] ++ updateTABAux y (posicao-1)


-- |Função auxiliar de /updateTABempty/.
updateTABemptyAux :: String -> Int -> String
updateTABemptyAux (x:y) 0 = " " ++ y
updateTABemptyAux (x:y) posicao = [x] ++ updateTABemptyAux y (posicao-1)


-- |Função auxiliar de /testeCOORDso2numeros/ que dada uma lista de /Char/ diz se essa lista tem apenas números. Qualquer outro tipo de carater devolve /Falso/.
--
-- >>> algarismos [1,3,5,a,6,b]
-- False
--
-- >>> algarismos [1,3,7,4,2]
-- True
--
-- == SEPARAR TABULEIRO DAS COORDENADAS
--
algarismos :: Char -> Bool
algarismos a = if (a >= '0') && (a <= '9') then True
                else False





-- |Função auxiliar de /funcaoPlantaMelhor/ que separa o tabuleiro das coordenadas. Dá apenas o tabuleiro.
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
sepTabCOORD ("":z) = []
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
-- == FUNÇÕES DE REMOÇÃO DE CARDINAIS REDUNDANTES
--
sepTabCOORDpares :: [String] -> [(Int,Int)]
sepTabCOORDpares tabuleiro = sepCOORD2 (sepCOORD1 (sepTabCOORD tabuleiro))




------------------------------------------------------------------------------------

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

-- |Função auxiliar de /funcaoTiraInterrogacao/.
funcaoTiraInterrogacaoAux :: String -> String
funcaoTiraInterrogacaoAux (x:[]) = if (x=='?') then ' ':[]
                                    else x:[]
funcaoTiraInterrogacaoAux (x:y) = if (x=='?') then ' ':(funcaoTiraInterrogacaoAux y)
                                    else x:(funcaoTiraInterrogacaoAux y)


