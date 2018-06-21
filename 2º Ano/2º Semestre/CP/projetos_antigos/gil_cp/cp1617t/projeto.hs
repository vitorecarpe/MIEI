{-# OPTIONS_GHC -XNPlusKPatterns #-}

import Cp
import List 
import Nat  
import Exp
import BTree
import LTree
import St 
import Probability hiding (cond)
import Data.List
import Test.QuickCheck hiding ((><))
import System.Random  hiding (split)
import GHC.IO.Exception
import System.IO.Unsafe



{-TESTAR COM O QUICKCHECK-}

{-////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-}
{--

-- PROBLEMA 1

inv x = foldr ((+).(f x)) 0 [0..100]
    where
        f x = ((1-x)^)
 
inv'' x n = p1 (for aux (1,n) n)
    where aux(s,o) = (s+(1-x)^o,o-1)
 --}



-- Regra de Horner - EXPLICAR NA ORAL ESTA OUTRA ALTERNATIVA, apenas nao foi utilizada na justificação porque seria muito complexo
inv3 x = for (succ.((1-x)*)) 1 --cataNat ( either (const 1) (succ . ((1-x)*))) --  <=  1+A (1+A (1+A...)) onde A = (1-x)

-- UNIVERSAL-CATA
--(inv x).inNat = (either (const 1) (succ.((1-x)*))) . (id -|- (inv x))

--desenvolvendo fica:

--inv x 0 = 1
--inv x (n+1) = (succ.((1-x)*)) . (inv x n)

-- \sum_i=0^n (1-x)^i Substituem os dois inv x da segunda linha pelo somatório \sum_i=0^n (1-x)^i 
-- e mostram que a igualdade se verifica. 
-- SOMATÓRIO sum_i:
sum_i x 0 = 1
sum_i x (n+1) = (1-x)^(n+1) + (sum_i x n)

--QUICKCHECK
-- inv x (n+1) = (succ.((1-x)*)) . (inv x n) <=>
-- <=> sum_i x (n+1) = (succ.((1-x)*)) . (sum_i x n)
-- <=> sum_i x (n+1) == (succ((1-x)*(sum_i x n))) -- Def-comp (succ.((1-x)*)) . (sum_i x n)

--invteste4 x (n+1) =  round2(sum_i x (n+1)) == round2(succ((1-x)*(sum_i x n)))

invteste4 x  =  round2(sum_i x (1000+1)) == round2(succ((1-x)*(sum_i x 1000))) -- n = 1000


-------------------------------------------------------------------------------------------------------------

-- Aplicando a lei de fokkinga ao split das funções inv e f obtemos um catamorfismo equivalente a um ciclo for
{-

------ FAZENDO EM PONTO-WISE---------
inv x 0 = 1
inv x (n+1) = (f (1-x) (n+1)) + (inv x n)
        where
            f x 0 = 1
            f x (n+1) = x * (f x n)

------ FOKKINGA --------------

split f inv   == cataNat (split h k) <=>   inv ==  p2.cataNat (split h k)

----------------CALCULAR H e K--(fokkinga)-------------------------------
f.in = h.F<f,inv>
inv.in = k.F<f,inv>

---------Calcular H-------
f x 0 = 1
f x (n+1) = x * (f x n)

gene de f x, g x = [const 1 , ((1-x)*)]
--------------------EQ + ---------
f (1-x) . in = [const 1 , ((1-x)*)]
---------------- ABSORÇÃO + , UNIVERSAL-CATA-----------
f (1-x) . in = [const 1 , ((1-x)*)] . (id + f(1-x))
---------------- CANCELAMENTO  P1 --------------
f (1-x) . in = [const 1 , ((1-x)*)] . (id + p1.<f(1-x),inv x>)
---------------- Functor +, Fokkinga -------------
f (1-x) . in = [const 1 , ((1-x)*)] . (id + p1) . (id + <f(1-x),inv x>)
f (1-x) . in = h . F<f,inv>
Logo,
h = [const 1 , ((1-x)*)] . (id + p1)
----------------------------------------------------------------------------------------------------------------------

---------Calcular K-------

inv x 0 = 1
inv x (n+1) = (f (1-x) (n+1)) + (inv x n)

gene de inv x (n+1), g x (n+1) = [const 1, add.< f (1-x) (n+1), inv x n >]

---------- Def-succ, Def-split (78), Igualdade extensional (73)------------
inv x (const 0) = (const 1)
inv x succ      = add.< f (1-x) (succ), inv x >
--------------Def-f----------------------
inv x (const 0) = (const 1)
inv x succ      = add.< (1-x)*(f(1-x)), inv x >
---------------EQ +--------------------
(inv x).in = [const 1, add.< (1-x)*(f(1-x)), inv x >]
---------------ABSORÇAO x--------------------
(inv x).in = [const 1, add.(((1-x)*) >< id) . < f(1-x), inv x>]
---------------ABSORÇAO + -------------------
(inv x).in = [const 1, add.(((1-x)*) >< id) ] . (id + < f(1-x), inv x>)
--------------- FOKKINGA --------------------
(inv x).in = k. F<f,inv>
Logo,
k= [const 1, add.(((1-x)*) >< id) ]

-------------- CONCLUSAO ----------------
cataNat (split h k) <=>   inv ==  p2.cataNat (split h)
-}

-- FORMA DE CATAMORFISMO
inv2 x = wrap . cataNat (split h k)
        where
            h    = (either (const 1) ((1-x)*)).(id -|- p1)
            k    =  either (const 1) (uncurry(+).(((1-x)*) >< id ))
            wrap =  p2

-- FORMA DE CICLO FOR
inv x = p2 . (for (split (((1-x)*).p1) (uncurry(+).(((1-x)*) >< id))) (1,1))


-- QUICKCHECK:
invteste1 x = (x>1 && x<2) ==> round2(inv (inv x 10000) 10000) == round2(x)


invteste2 x = if ( (x<=1) || (x>=2)) then True else round2(inv (inv x 10000) 10000) == round2(x)

-- Sem arredondamentos, tem de ser menor que um certo grau de incerteza
invteste3 x = (x>1 && x<2) ==> let gInc = 0.000000001
                               in abs((inv (inv x 1000) 1000) - x) < gInc

-- Arredonda às décimas
round2 x = fromIntegral(round (x * 100))/100




{-}
--invProp:: Int -> Int -> Property
-- (x>1 && x<2) && (n>1000 && n<2000) && ((inv x n == 1/x)??) &&
-- && PROPRIEDADES DAS SERIES (exemplo, para n+1 é mais preciso que para n, ver propriedades das series na wiki)
invProp x n = (x>1 && x<2) && (n>1000 && n<2000) && (invteste1 x)
-- > quickCheck invProp

--forAll:: Gen a -> (a -> prop) -> Property

--invPropGen::Property
invPropGen= forAll genx $ \x -> forAll genn $ \n -> invProp x n 
    where
        genx :: Gen Int
        genx = Test.QuickCheck.choose(1,2) -- gera x de 1 a 2
        genn :: Gen Int
        genn = Test.QuickCheck.choose(1000,2000) -- gera n de 1000 a 2000

-- > quickCheck invPropGen
-}
            

{-////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-}


-- PROBLEMA 2

-- POINT-FREE

{-
------------ WORD_COUNT ----------
--GENE
f = either (const 0) (cond ((uncurry(&&)).(split (not.sep.p1) (lookahead.p2))) (succ.wc.p2) (wc.p2))

------------ LEI UNIVERSAL-CATA ----------
wc.inList = f

------------ ISOMORFISMO IN/OUT  ----------
wc = f.outList

-}


wc = (either  (const 0) (cond ((uncurry(&&)).(split (not.sep.p1) (lookahead.p2))) (succ.wc.p2) (wc.p2))) . outList


{-
------------ LOOKAHEAD ----------
--GENE
g = either (const True) (sep.p1)

------------ LEI UNIVERSAL-CATA ----------
lookahead.inList = g

------------ ISOMORFISMO IN/OUT  ----------
lookahead = g.outList

-}

lookahead =  (either (const True) (sep.p1)).outList

-- DO ENUNCIADO
wc_enun:: [Char] ->  Int
wc_enun [] = 0
wc_enun (c:l) =
    if (not(sep c) && lookahead_enun l)
    then (wc_enun l) + 1
    else (wc_enun l)
            
lookahead_enun [] = True
lookahead_enun (c : l) = sep c


sep:: Char -> Bool
sep c = (c == ' ' || c == '\n' || c == '\t')


wc_w_final :: [Char] -> Int
wc_w_final = wrapper . worker
wrapper = p1

worker = cataList ( split (either (const 0) h2) (either (const True) (sep.p1)))
            where h2 = cond (uncurry(&&).((not.sep) >< p2)) (succ.p1.p2) (p1.p2)

-- QUICKCHECK
wc_teste x = wc_w_final x == wc_enun x
-----------------------------------------------------------------------------------
-- RESOLUÇÃO

{-

-- LEI DE FOKKINGA (50)
split wc lookahead = cataList (split h k)

    wc.inList        = h.(id -|- id >< (split wc lookahead))
    lookahead.inList = k.(id -|- id >< (split wc lookahead))
--------  DEF + (21) ----------
    wc.inList = h.either(i1. id , i2. id >< (split wc lookahead))
    ...
-------- FUSAO + (20) & NATURAL ID----------
    wc.inList = either(h.i1, h.i2. (id >< (split wc lookahead)))
    ...
--------  inList = either (nil cons) & Eq + (27)-------
    wc.nil         = h.i1
    wc.cons        = h.i2.(id >< (split wc lookahead))

    lookahead.nil  = k.i1
    lookahead.cons = k.i2.(id >< (split wc lookahead))

--------  CANCELAMENTO + (18) -------53
--------  Seja h = [h1,h2] e k = [k1,k2] -------

    wc.nil         = h1
    wc.cons        = h2.(id >< (split wc lookahead))

    lookahead.nil  = k1
    lookahead.cons = k2.(id >< (split wc lookahead))


--------  CONCLUINDO DO CÓDIGO DO ENUNCIADO-------

    wc.nil         = h1     = const 0
    wc.cons        = h2     =   ?

    lookahead.nil  = k1     = const true
    lookahead.cons = sep.p1 = k2.(id >< (split wc lookahead))

--------  CALCULAR k2 -----------------------------------------

sep.p1 = k2.(id >< (split wc lookahead))

--------  LEI NATURAL-ID -----------------------------------------

sep.id.p1 = k2.(id >< (split wc lookahead))

-------- LEI NATURAL-P1 -----------------------------------------

sep.p1.(id >< (split wc lookahead)) = k2.(id >< (split wc lookahead))


-------- LEI LEIBNIZ -----------------------------------------

k2 = sep.p1

------------------------------------------------------------------------------------------------------------
--------  PARA JÁ ------------------------------------------------------------------------------------------

h = either ((const 0) h2) ;  k = either((const true) (sep.p1))
------------------------------------------------------------------------------------------------------------

--------  CALCULAR h2 --------------------------------------------------------------------------------------
 -- OBSERVANDO O CODIGO

wc.cons = h2.(id >< (split wc lookahead)) = cond (uncurry(&&).(split (not.(sep.p1)) lookahead.p2) (succ.wc.p2) (wc.p2)

--------  CONDICIONAL DE MCCARTHY (30) -------

h2.(id >< (split wc lookahead)) = cond (uncurry(&&).(split (not.(sep.p1)) lookahead.p2) (succ.wc.p2) (wc.p2)

--------  UNIVERSAL X (6) -------

h2.(id >< (split wc lookahead)) = cond (uncurry(&&).(split (not.(sep.p1)) (p2.(split wc lookahead).p2) (succ.p1.(split wc lookahead).p2) (p1.(split wc lookahead).p2)

--------  DEF X (19) -------

h2.(id >< (split wc lookahead)) = cond (uncurry(&&).( (not.sep) >< (p2.(split wc lookahead))) (succ.p1.(split wc lookahead).p2) (p1.(split wc lookahead).p2)

-------- FUNCTOR-ID-X (15) & NATURAL P2 (13) -------

h2.(id >< (split wc lookahead)) = cond (uncurry(&&).( (not.sep) >< (p2.(split wc lookahead))) (succ.p1.p2.(id >< (split wc lookahead))) (p1.p2.(id >< (split wc lookahead)))

-------- NATURAL ID (15) & FUNCTOR-X (14) -------

h2.(id >< (split wc lookahead)) = cond (uncurry(&&).( ((not.sep) >< p2).(id >< (split wc lookahead))) (succ.p1.p2.(id >< (split wc lookahead))) (p1.p2.(id >< (split wc lookahead)))

-------- 2º LEI DE FUSAO DO CONDICIONAL (32) -------

h2.(id >< (split wc lookahead)) =  (cond (uncurry(&&).((not.sep) >< p2)) (succ.p1.p2) (p1.p2)).(id >< (split wc lookahead)) 

-------- LEI DE LEIBNIZ (5) -------

h2 =  (cond (uncurry(&&).((not.sep) >< p2)) (succ.p1.p2) (p1.p2))

-}
{-////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-}

--  PROBLEMA 3

data B_tree a = Nil | Block {leftmost :: B_tree a, block :: [(a, B_tree a)]} deriving (Show,Eq)

t = Block {
    leftmost = Block {
        leftmost = Nil,
        block = [(1, Nil), (2, Nil), (5, Nil), (6, Nil)]},
block = [
    (7, Block {
        leftmost = Nil,
        block = [(9, Nil), (12, Nil)]}),
(16, Block {
    leftmost = Nil,
    block = [(18, Nil), (21, Nil)]})
]}


t2 = Block  (Block  Nil [(1, Nil), (2, Nil), (5, Nil), (6, Nil)]) [(7, (Block Nil [(9, Nil), (12, Nil)])), (16, (Block Nil [(18, Nil), (21, Nil)]))]

-- ARVORE TESTE : (Block Nil [(2,Nil)])  ||  (Block Nil [(3,(Block Nil [(2,Nil)]))])

inB_tree = either (const Nil) (uncurry Block)  -- 1 + ( leftmost , [(a, Block)])

outB_tree  Nil   = i1 ()
outB_tree (Block l b) = i2 (l,b)

recB_tree f = id -|- f >< (map (id >< f))    --  1 + leftmost >< [(a,Block)]
                                             --  1 + BTree a >< [(a,BTree a)]
baseB_tree g f = id -|- f >< (map (g >< f))

cataB_tree g = g . (recB_tree (cataB_tree g)) . outB_tree

anaB_tree g = inB_tree . (recB_tree (anaB_tree g) ) . g

hyloB_tree f g = (cataB_tree f) . (anaB_tree g)

instance Functor B_tree
    where fmap f = cataB_tree ( inB_tree . baseB_tree f id )


inordB_tree = cataB_tree (inordB_aux)
inordB_aux = either nil (conc.(id >< (concat .(map (conc. (singl >< id))))))


largestBlock = cataB_tree ( either (const 0) (uncurry(max).( id >< h)))
    where
        h = uncurry(max).((length) >< (maximum)).unzip


mirrorB_tree = anaB_tree (( id -|- (split (head.p2.f) (g))).outB_tree)
        where
            f = (reverse >< reverse).unzip.p2
            g = uncurry(zip).(split (p1.f) (conc.(split (tail.p2.f) (singl.p1))))

------------------------------------------------------------------------------------------------------------------------

--Teste no terminal, exemplo, "mirrorteste t" :
mirrorteste l = (mirrorB_tree.mirrorB_tree) l == id l
 
--QUICK CHECK 
--Teste no quickCheck:

mirrorteste2 l = f l == g l
            where
                f = reverse.qSortB_tree
                g = inordB_tree. mirrorB_tree. anaB_tree (lsplitB_tree)

------------------------------------------------------------------------------------------------------------------------

lsplitB_tree:: Ord a => [a] -> Either () ([a],[(a,[a])])
lsplitB_tree [] = i1 ()
lsplitB_tree [e] = i2 ([],[(e,[])])
lsplitB_tree (h:h2:t) =  i2 ( ((filter (<minpar) t)) , [ ((minpar),((filter (condition) t))) , ((maxpar),((filter (>maxpar) t))) ]) 
                            where
                                minpar = (min h h2)
                                maxpar = (max h h2)
                                condition = \a -> (a>minpar) && (a<maxpar)

-- lsplitB_tree (h:h2:t)  = ([menores que h], [(h,[maiores que h e menores que h2]), (h2,[maiores que h2])])

qSortB_tree:: Ord a => [a] -> [a]
qSortB_tree =hyloB_tree inordB_aux lsplitB_tree


dotB_Tree :: Show a => B_tree a -> IO ExitCode
dotB_Tree = dotpict . bmap nothing (Just . init . concat . (map (++"|")).(map show)) . cB_tree2Exp



cB_tree2Exp = cataB_tree ( either (const (Var "nil")) cB2E_aux)
                where cB2E_aux = uncurry(Term).(id >< cons).(split (p1.p2) (split p1 (p2.p2))).(id><unzip)


{-////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-}

--  PROBLEMA 4

type Algae = A
data A = NA | A A B deriving Show
data B = NB | B A deriving Show

inA = either (const NA) (uncurry A) 

outA NA = i1 ()      -- 1 + A x B
outA (A a b) = i2 (a,b)

inB = either (const NB) B


outB NB = i1 ()   -- 1 + A
outB (B a) = i2 a

cata_Algae_A ga gb = ga . (id -|- (cata_Algae_A ga gb) >< (cata_Algae_B ga gb) ). outA
cata_Algae_B ga gb = gb . (id -|- (cata_Algae_A ga gb)) . outB


ana_Algae_A ga gb = inA . (id -|- ((ana_Algae_A ga gb)><(ana_Algae_B ga gb)) ) . ga 
ana_Algae_B ga gb = inB . (id -|- (ana_Algae_A ga gb) ) . gb 


--POINT-WISE
generateAlgaeA :: Int -> Algae
generateAlgaeA 0 = NA
generateAlgaeA n = A (generateAlgaeA (n-1)) (generateAlgaeB (n-1))

generateAlgaeB 0 = NB
generateAlgaeB n = B (generateAlgaeA (n-1))

--POINT-FREE
generateAlgae :: Int -> Algae
generateAlgae = (ana_Algae_A ga1 gb1)
        where 
            ga1 = (id -|- (split id id)).outNat
            gb1 = outNat

--------------------------------------------------------------------------------------------------------------------
-- POINT WISE
showAlgaeA:: Algae -> String
showAlgaeA NA = "A"
showAlgaeA (A a b) = (showAlgaeA a) ++ showAlgaeB b

showAlgaeB NB = "B"
showAlgaeB (B a) = showAlgaeA a

--POINT FREE
showAlgae :: Algae -> String
showAlgae = cata_Algae_A ga2 gb2
    where
        ga2 = either (const "A") (conc)
        gb2 = either (const "B") (id)



-- QUICKCHECK: 
-- Tivemos de redefinir a função fib, pois só assi é possivel alterar o seu tipo,
-- necessário para que coincida com o tipo de length
-- :t length [a]->Int
-- :t fib Integer -> Integer
-- Integer != Int
-- Senão dá erro ao compilar

-- N SÓ VAI ATÉ 5, porque quanto maior for o numero mais tempo demora a gerar, foi só para servir para testar
teste_Algae n = (n>=0 && n<20) ==> (length . showAlgae . generateAlgae) n == (fib . succ) n
            where
                fib:: Int->Int
                fib =  hyloLTree (either (const 1) (uncurry(+))) fibd

{-////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-}

--  PROBLEMA 5


type Equipa = String

equipas :: [Equipa]
equipas = [
   "Arouca","Belenenses","Benfica","Braga","Chaves","Feirense",
   "Guimaraes","Maritimo","Moreirense","Nacional","P.Ferreira",
   "Porto","Rio Ave","Setubal","Sporting","Estoril"
   ]

------------ FUNÇÕES AUXILIARES-----------------
-- ordena listas com base num atributo (função que induz uma pré-ordem)
presort :: (Ord a, Ord b) => (b -> a) -> [b] -> [b]
presort f = map snd . sort . (map (fork f id)) 

-- LOOK-UP : pap [('A', 0.02),('B', 0.12),('C', 0.29),('D', 0.35),('E', 0.22)] 'C'  = 0.29
pap :: Eq a => [(a, t)] -> a -> t
pap m k = unJust (lookup k m) where unJust (Just a) = a

getR :: [a] -> IO (a, [a])
getR x = do {
               i <- getStdRandom (randomR (0,length x-1));
               return (x!!i,retira i x)
             } where retira i x = take i x ++ drop (i+1) x


jogo :: (Equipa, Equipa) -> Dist Equipa
jogo(e1,e2) = D [ (e1,1-r1/(r1+r2)),(e2,1-r2/(r1+r2)) ] where
              r1 = rank e1
              r2 = rank e2
              rank = pap ranks
              ranks = [
                  ("Arouca",5),
                  ("Belenenses",3),
                  ("Benfica",1),
                  ("Braga",2),
                  ("Chaves",5), 
                  ("Feirense",5),
                  ("Guimaraes",2),
                  ("Maritimo",3),
                  ("Moreirense",4),
                  ("Nacional",3),
                  ("P.Ferreira",3),
                  ("Porto",1),
                  ("Rio Ave",4),
                  ("Setubal",4),
                  ("Sporting",1),
                  ("Estoril",5)]



---------- POINT WISE ------------

-- Sem probabilidades: o getR2 tira sempre a cabeça da lista, não retira à sorte, porque isso envolveria probabilidades,
-- algo que só é possivel em monades

getR2 :: [a] ->  (a, [a])
getR2 = split head tail


permuta2::[a] ->  [a]
permuta2 [] = []
permuta2 list = (a:permuta2 b) 
         where   
            (a,b) = getR2 list
                


------ MONADIFICATION ------------
permuta::[a] -> IO [a]
permuta [] = return []
permuta list = do { (a,b) <- getR list; c <- permuta b ; return (a:c)} 


                

sorteio :: [Equipa] -> LTree Equipa
sorteio = anaLTree lsplit . envia . permuta


--- VERSAO DA FUNÇÃO jogo ONDE GANHA SEMPRE O PRIMEIRO
jogo2 :: (Equipa, Equipa) ->  Equipa
jogo2 = p1


------ POINT WISE ------------
eliminatoria2 :: LTree Equipa ->   Equipa
eliminatoria2 (Leaf a) =  a
eliminatoria2 (Fork (l,r)) = jogo2(e1,e2)
                            where
                             e1 = eliminatoria2 l 
                             e2 = eliminatoria2 r 



------ MONADIFICATION ------------
eliminatoria :: LTree Equipa ->  Dist Equipa
eliminatoria (Leaf a) = return a
eliminatoria (Fork (l,r)) = do { e1 <- eliminatoria l; e2 <- eliminatoria r; jogo(e1,e2) } 



quem_vence :: [Equipa] -> Dist Equipa
quem_vence = eliminatoria . sorteio


--------------------------------------------------------------------------------------------
type Null   = ()
type Prod a b = (a,b)
fork = Cp.split
envia = unsafePerformIO
