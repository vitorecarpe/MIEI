
-- (c) MP-I (1998/9-2006/7) and CP (2005/6-2016/7)

-- NB: this is not a library, it is just a script of demos.
-- The useful material can be found in libraries BTree.hs, LTree.hs etc

import Data.List
import Test.QuickCheck
import System.CPUTime
import System.Process
import GHC.IO.Exception
import Cp
import List 
import Nat  
import Exp
import BTree
import LTree
import FTree
import St
import Probability

--- (a) BTree virtual data structure display ----------------------------------

--- Quicksort ------------------------------------------------------------------

qSort_vtree x = (pict. (anaBTree qsep)) x

--- Towers of Hanoi ------------------------------------------------------------

hanoi_vtree = pict . (anaBTree strategy)

--- (b) LTree virtual data structure display -----------------------------------

--- Fibonacci ------------------------------------------------------------------

fib_vtree n = (pict . (anaLTree fibd)) n

--- Mergesort ------------------------------------------------------------------

mSort_vtree [] = (expShow "_.html") (Var " ")
mSort_vtree l  = (pict.(anaLTree lsplit )) l

-- Double factorial ------------------------------------------------------------

dfac_vtree 0 = (expShow "_.html") (Var "1")
dfac_vtree n = (pict. (anaLTree dfacd)) (1,n)

--- (c) Decorating BTrees with the state monad ---------------------------------

f Empty = return Empty
f(Node(a,(x,y))) = do {
         n <- get ; put(n+1);
         x' <- f x ;
         y' <- f y ;
         return (Node((a,n),(x',y'))) }

test = st(f t) 0
       where t = anaBTree qsep "aecrecrsdadcedx"

--- (d) A study of fibonacci ---------------------------------------------------


-- pointwise version of fib =  hyloLTree (either (const 1) add) fibd

fibpw 0 = 1
fibpw 1 = 1
fibpw n = fibpw (n-1) + fibpw (n-2)

-- linear version O(n) after mutual recursion law

fiblpw n = let (a,b) = aux n in b
         where aux 0 = (1,1)
               aux n = let (a,b) = aux (n-1) in (a+b,a) 

-- IO-monadic version o fibpw showing algorithm evolution

{--
fibpwm 0 = return 1
fibpwm 1 = return 1
fibpwm n  = do putStr("\nfib("++show n ++") = ...\n");
                 a <- fibpwm(n-1) ;
                 b <- fibpw(n-2);
--               putStr("\nn-1="++show(n-1)++" n="++show(n-2)++" a+b="++show(a+b)++"\n");
                 return (a + b)

-- IO-monadic version o fibpw' showing algorithm evolution

fiblpwm n = do (a,b) <- auxm n ; putStr("\nresult="++(show b)++"\n")
         where auxm 0 = return (1,1)
               auxm n = do (a,b) <- auxm(n-1);
                               putStr("\nn="++show n ++" (a+b,a)="++show(a+b,a)++"\n");
                               return (a+b,a) 
--}
-- measuring time costs:
{--
trun f a =
        do
        start <- getClockTime
        print(f a)
        end <- getClockTime
        print (diffClockTimes end start)

tanalysis f f' a =
        do start <- getClockTime
           print(f a)
           mid <- getClockTime
           print(f' a)
           end <- getClockTime
           print (diffClockTimes mid start)
           print (diffClockTimes end mid)
           print $ div (tdPicosec(diffClockTimes mid start)) (tdPicosec(diffClockTimes end mid))

-- trun fib 19
-- trun fiblpw 19
--}
