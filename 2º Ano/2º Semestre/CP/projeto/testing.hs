inv :: Float -> Int -> Float
inv 0 _ = 1
inv _ 0 = 1
inv x n = (1-x)^n + inv x (n-1)

bla :: Float -> Int -> Float
bla 0 _ = 1
bla _ 0 = 1
bla x n = (1-x)*(bla x (n-1))

mrd :: Float -> Float 
mrd 0 = 1
mrd x = 1-(mrd (x-1))