{-|
Módulo: Main

Título: Problema E

Descrição: Módulo /Haskell/ com auxílio da biblioteca /Gloss/ onde dado uma /Picture/ que pode ou não conter várias /Pictures/ devolve a dimensão do retângulo mais pequeno que contém todas as /Pictures/.

Copyright: Francisco Oliveira /a78416@alunos.uminho.pt/   | | |   Vitor Peixoto /a79175@alunos.uminho.pt/

Resumo: Módulo contendo definições Haskell do Problema E da 2ª Fase do Projeto da disciplina de Laboratórios de Informática I. Este módulo contém funções que após a introdução de uma /Picture/ que pode conter várias /Pictures/ (que podem ser sujeitas a modificações pelos construtores /Translate/, /Rotate/ ou /Scale/), as separa, determina os cantos do retângulo de menor dimensão que envolvem essa /Picture/ e a partir dessa informação determina o comprimento e a largura desse retângulo.

== FUNÇÕES DEFINIDAS NO ESQUELETO INICIAL
-}

module Main where

import qualified Data.Text as T
import GlossExtras
import Graphics.Gloss

-- |Função principal (/main/).
main = do inp <- getContents
          let (x,y) = tarefa5 (readPicture inp)
          putStrLn (show x ++ " " ++ show y)



-- |Função /tarefa5/ que junta todas as funções criadas para auxiliar esta. Esta função ao receber uma /Picture/ ou conjunto de /Pictures/ devolve o retângulo de menor dimensão que envolve essa /Picture/.
--
-- >>> tarefa5 (Line [(0,0),(20,0),(10,10),(0,0)])
-- (20,10)
-- 
-- >>> tarefa5 (Translate 30 30 Blank)
-- (0,0)
--
-- >>> tarefa5 (Pictures [(Translate 10 10 (Circle 15)),(Scale 10 10 Blank),(Polygon [(10,20),(10,40),(30,40)])])
-- (35,45)
--
-- == FUNÇÕES DE CÁLCULO DAS DIMENSÕES DO RETÂNGULO
--
tarefa5 :: Picture -> (Int, Int)
tarefa5 pic = elSize (elCanto (listadeCaixasdasPics (separadordePics pic)))

-- |Tipo /Caixa/ que é um par de /Point/ ou seja ((/Float/,/Float/),(/Float/,/Float/)).
type Caixa = (Point,Point)


-- |Função que dada uma /Picture/, a separa nas diversas /Pictures/ que a compõem (ou não, caso a /Picture/ dada ser apenas constituída por uma única /Picture/).
--
-- >>> separadordePics (Circle 0)
-- []
--
-- >>> separadordePics (Scale 10 10 Blank)
-- []
--
-- >>> separadordePics (Pictures [(Circle 10),(Circle 40)])
-- [Circle 10.0,Circle 40.0]
--
-- >>> separadordePics (Pictures [(Circle 20),(Line [(10,10),(30,30)]),(Polygon [(0,0),(0,20),(20,20),(20,0)])])
-- [Circle 20.0,Line [(10.0,10.0),(30.0,30.0)],Polygon [(0.0,0.0),(0.0,20.0),(20.0,20.0),(20.0,0.0)]]
--
separadordePics :: Picture -> [Picture]
separadordePics Blank = []
separadordePics (Polygon []) = []
separadordePics (Polygon [p]) = []
separadordePics (Polygon p) = (Polygon p):[]
separadordePics (Line []) = []
separadordePics (Line [p]) = []
separadordePics (Line p) = (Line p):[]
separadordePics (Circle 0) = []
separadordePics (Circle f) = (Circle f):[]
separadordePics (Bitmap 0 0 dta b) = []
separadordePics (Bitmap w h dta b) = (Bitmap w h dta b):[]
separadordePics (Color c p) = separadordePics p 
separadordePics (Translate f1 f2 Blank) = []
separadordePics (Translate f1 f2 p) = (Translate f1 f2 p):[]
separadordePics (Rotate _ Blank) = []
separadordePics (Rotate f p) = []
separadordePics (Scale 0 _ p) = []
separadordePics (Scale _ 0 p) = []
separadordePics (Scale _ _ Blank) = []
separadordePics (Scale f1 f2 p) = (Scale f1 f2 p):[]
separadordePics (Pictures (p:[])) = (separadordePics p)
separadordePics (Pictures (p:t)) = (separadordePics p)++(separadordePics (Pictures t))

-- |Função que dada uma /[Pictures]/ devolve a lista com as coordenadas dos cantos dos retângulos de menor dimensão que envolvem as /Pictures/ da lista.
--
-- >>> listadeCaixasdasPics [Circle 10.0,Circle 40.0]
-- [((-10.0,-10.0),(10.0,10.0)),((-40.0,-40.0),(40.0,40.0))]
--
-- >>> listadeCaixasdasPics [Circle 20.0,Line [(10.0,10.0),(30.0,30.0)],Polygon [(0.0,0.0),(0.0,20.0),(20.0,20.0),(20.0,0.0)]]
-- [((-20.0,-20.0),(20.0,20.0)),((10.0,10.0),(30.0,30.0)),((0.0,0.0),(20.0,20.0))]
--
-- >>> listadeCaixasdasPics [Translate 20.0 20.0 (Circle 20.0)]
-- [((0.0,0.0),(40.0,40.0))]
--
listadeCaixasdasPics :: [Picture] -> [Caixa]
listadeCaixasdasPics [] = []
listadeCaixasdasPics (Blank:t) = (listadeCaixasdasPics t)
listadeCaixasdasPics ((Polygon p):t) = (quaisSaoCantos p):(listadeCaixasdasPics t)
listadeCaixasdasPics ((Line p):t) = (quaisSaoCantos p):(listadeCaixasdasPics t)
listadeCaixasdasPics ((Circle r):t) = ((-r,-r),(r,r)):(listadeCaixasdasPics t)
listadeCaixasdasPics ((Bitmap w h dta b):t) = ((fromIntegral (-w),fromIntegral (-h)),(fromIntegral w,fromIntegral h)):(listadeCaixasdasPics t)
listadeCaixasdasPics ((Translate f1 f2 p):t) = kindaComplicated (Translate f1 f2 p):(listadeCaixasdasPics t)
listadeCaixasdasPics ((Rotate f p):t) = kindaComplicated (Rotate f p):(listadeCaixasdasPics t)
listadeCaixasdasPics ((Scale f1 f2 p):t) = kindaComplicated (Scale f1 f2 p):(listadeCaixasdasPics t)

-- |Função que determina a caixa que contém uma /Picture/ depois de esta ser sujeita a alterações (/Translate/ e /Scale/ (a /Rotate/ não funciona corretamente)).
--
-- >>> kindaComplicated (Translate 20.0 20.0 (Circle 20.0))
-- ((0.0,0.0),(40.0,40.0))
--
-- >>> kindaComplicated (Scale 1.0 2.0 (Circle 20.0))
-- ((-20.0,-40.0),(20.0,40.0))
--
-- >>> kindaComplicated (Rotate (Circle 20.0))
-- ((-20.0,-20.0),(20.0,20.0))
--
kindaComplicated :: Picture -> Caixa
kindaComplicated (Translate f1 f2 p) = let ( (x3,y3),(x1,y1) )=(head (listadeCaixasdasPics (p:[])))
                                       in ( (x3+f1,y3+f2),(x1+f1,y1+f2) )
kindaComplicated (Rotate _ (Circle r)) = head (listadeCaixasdasPics [Circle r])
kindaComplicated (Rotate f (Polygon p)) = kindaComplicated (Rotate f (Line p))
kindaComplicated (Rotate f (Line ((x,y):[]))) = let ponto=(x*(cos f) + y*(sin f),y*(cos f) - x*(sin f))
                                                in quaisSaoCantos [ponto]
kindaComplicated (Rotate f (Line ((x,y):t))) = let ponto=(x*(cos f) + y*(sin f),y*(cos f) - x*(sin f))
                                               in quaisSaoCantos [ ponto,doublePointintoPoint (kindaComplicated (Rotate f (Line t))) ]
kindaComplicated (Rotate f (Bitmap w h dta b)) = let ( (x3,y3),(x1,y1) )=head (listadeCaixasdasPics ((Bitmap w h dta b):[]))
                                                 in quaisSaoCantos [ doublePointintoPoint (quaisSaoCantos [ doublePointintoPoint (kindaComplicated (Rotate f (Line [(x1,y1)]))),
                                                                                                            doublePointintoPoint (kindaComplicated (Rotate f (Line [((-x1),y1)]))),
                                                                                                            doublePointintoPoint (kindaComplicated (Rotate f (Line [((-x1),(-y1))]))),
                                                                                                            doublePointintoPoint (kindaComplicated (Rotate f (Line [(x1,(-y1))]))) 
                                                                                                          ] ) ]
kindaComplicated (Scale f1 f2 p) | (f1>0 && f2>0) = let ( (x3,y3),(x1,y1) )=(head (listadeCaixasdasPics (p:[])))
                                                    in ( (x3*f1,y3*f2),(x1*f1,y1*f2) )
                                 | (f1<0 && f2<0) = let ( (x3,y3),(x1,y1) )=(head (listadeCaixasdasPics (p:[])))
                                                    in ( (x1*f1,y1*f2),(x3*f1,y3*f2) )
                                 | (f1<0 && f2>0) = let ( (x3,y3),(x1,y1) )=(head (listadeCaixasdasPics (p:[])))
                                                    in ( (x1*f1,y3*f2),(x3*f1,y1*f2) )
                                 | (f1>0 && f2<0) = let ( (x3,y3),(x1,y1) )=(head (listadeCaixasdasPics (p:[])))
                                                    in ( (x3*f1,y1*f2),(x1*f1,y3*f2) )

-- |Função auxiliar de /kindaComplicated/ que dado um /type/ /Caixa/, remove o segundo par.
-- 
-- >>> doublePointintoPoint ((-20.0,-20.0),(20.0,20.0))
-- (-20.0,-20.0)
--
doublePointintoPoint :: Caixa -> Point
doublePointintoPoint ((x,y),_) = (x,y)

-- |Função que descobre os cantos do retângulo que contém uma /Line/ ou /Polygon/.
--
-- >>> quaisSaoCantos [(0.0,0.0),(0.0,20.0),(20.0,20.0),(20.0,0.0)]
-- ((0.0,0.0),(20.0,20.0))
--
quaisSaoCantos :: [Point] -> Caixa
quaisSaoCantos [] = ((0,0),(0,0))
quaisSaoCantos ((x,y):t) = quaisSaoCantosAux (t) ( (x,y),(x,y) ) where --melhorar metendo as coords do x logo em vez de 0,0
  quaisSaoCantosAux [] cantos = cantos
  quaisSaoCantosAux ((x,y):t) ((x3,y3),(x1,y1)) = quaisSaoCantosAux (t) ((min x x3,min y y3),(max x x1,max y y1))


-- |Função que define o retângulo que contém todos os outros retângulos, logo contendo todas as /Pictures/ no menor retângulo possível.
--
-- >>> elCanto [((0.0,0.0),(20.0,20.0)) , ((-10.0,-10.0),(15.0,15.0)) , ((0.0,0.0),(50.0,40.0))]
-- ((-10.0,-10.0),(50.0,40.0))
--
elCanto :: [Caixa] -> Caixa
elCanto [] = ((0,0),(0,0))
elCanto (((x3,y3),(x1,y1)):t) = elCantoAux (t) ( (x3,y3),(x1,y1) ) where
  elCantoAux [] ( (xmin,ymin),(xmax,ymax) ) = ( (xmin,ymin),(xmax,ymax) )
  elCantoAux (((x3,y3),(x1,y1)):t) ( (xmin,ymin),(xmax,ymax) ) = elCantoAux (t) ( (min x3 xmin,min y3 ymin),(max x1 xmax,max y1 ymax) )

-- |Função que dadas as coordenadas dos cantos do retângulo que envolve todas as /Pictures/, calcula o comprimento e largura.
--
-- >>> elSize ((-10.0,-10.0),(50.0,40.0))
-- (60,50)
--
-- >>> elSize ((0.0,-10.0),(21.0,44.0))
-- (21,54)
--
elSize :: Caixa -> (Int,Int)
elSize ((x3,y3),(x1,y1)) = (round (abs (x1-x3)),round (abs (y1-y3)))