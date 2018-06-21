set.seed(987854321)
mat<-matrix(sample(10,60,replace=T), nr=6)
S<-colSums(mat)
L<-outer(S,S,"+")>75
res<-mat[which(lower.tri(L))]

#não está certo, mas é uma alternativa ao ex de cima
#LL<-(lower.tri(L) & L)
#which(LL, arr.ind=T)

#Exercicio 4
#a
m<-matrix(0,nr=20,nc=5)
for(i in 1:20){
   for(j in 1:5){
       m[i,j] <- i^4 / (3+j) 
   }
}

sum(m)

#alternativa com vetores 
sum(outer((1:20)^4 , (3+(1:5)), "/"))
sum((1:20)^4) * sum(1/(3+(1:5)))

#b
#está mal
mb <- matrix (0,nr=20,nc=5)
for (i in 1:20){
    for (j in 1:5){
        mb[i,j] <- (i^4 / (3 + i * j))
    }
}
sum (mb)

sum((1:20)^4 / (3+outer(1:20,1:5)))


#c
f <- function (i,j){
    (i >= j) * i^4 / (3+ i*j)
}
sum(outer (1:10, 1:10, "f"))









