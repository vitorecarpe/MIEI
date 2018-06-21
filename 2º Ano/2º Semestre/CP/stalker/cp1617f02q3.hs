
-- (c) CP (2016/7)

import Cp

data Atl
data Jog
data Dat

f :: [(Dat, [Jog])] -> [(Jog, [Atl])] -> [(Atl, [Dat])]
f = undefined

discollect :: [(a, [b])] -> [(a, b)]
discollect = undefined

collect :: [(a, b)] -> [(a, [b])] 
collect = undefined

comp :: [(a, b)] -> [(b, c)] -> [(a, c)]
comp = undefined

converse :: [(a, b)] -> [(b, a)]
converse = undefined

sort :: [a] -> [a]
sort = undefined

db1 :: [(Dat, [Jog])]
db1 = undefined

db2 :: [(Jog, [Atl])]
db2 = undefined

