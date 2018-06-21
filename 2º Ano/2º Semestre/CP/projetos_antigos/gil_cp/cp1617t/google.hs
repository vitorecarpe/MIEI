
-- (c) CP (2012/3-16/17)

-- NB: this is not a proper library, it is just an "ad hoc" illustration of
-- the map-reduce programming strategy. A more accurate account
-- is given in: R. Lammel's "Google's MapReduce programming model --- Revisited",
-- http://dx.doi.org/10.1016/j.scico.2007.07.001

import Data.List
import Cp
import Exp
import List 
import Data.List.Split
import BTree
import LTree

--- a tiny internet with 5 URLs only:

db = zip [1..] [doc1, doc2, doc3, doc4, doc5]
   where
     doc1 = "This is an example of document and a good example"
     doc2 = "This is another document"
     doc3 = "A piece of text is given here as example"
     doc4 = "Yet another example"
     doc5 = "This is the fifth document"

-- we want to build a searchable index from it:

idx = (finalize . reduce . mapstep . prepare) db

-- NB: use "pict it" to visualize each step in the pipeline

-- analysis of the process pipeline

--- document inversion is a costly operation

invert :: Eq a => [(a, String)] -> [(String, [a])]
invert = cataList (h . (id -|- (conv  . (id >< words)) >< id))
          where h=either nil ucol

-- prepare data for 2 docs per machine

prepare = anaLTree lsplit . (chunksOf 2)

-- map step (inversions run in parallel)

mapstep = fmap invert

-- reduce step (join everything together)

reduce = cataLTree (either id (uncurry col))

-- build a searchable index

finalize = anaBTree qsep

-- aux functions

conv   :: (Eq a, Eq b) => (a, [b]) -> [(b, [a])]
conv   = map (id><nub) . freq . map swap .lstr

freq l = nub [ (a, collect a l) | (a,b) <- l ]
         where collect a l = [ b | (x,b) <- l, x == a]

col :: Eq b => [(b, [a])] -> [(b, [a])] -> [(b, [a])]
col m n = m .\ n ++ n .\ m ++ [(a,x++concat[l' | (a',l') <- n, a'==a]) | (a,x) <- m .= n]
            
ucol = uncurry col

m .\ n = [(a,b) | (a,b) <- m, not(a `elem` (map fst n))]

m .= n = [(a,b) | (a,b) <- m, a `elem` (map fst n)]

-------------------------------------------------------