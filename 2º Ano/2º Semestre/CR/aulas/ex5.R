#Exercicio 5

#a)

set.seed(123456789)
x<-sample(1:10,5)
y<-sample(1:11,6)

f1<-function(a,b){
   return(b<a)
}

L<-outer(x,y,f1)
colSums(L)
sum(colSums(L))

sum(colSums(outer(y,x,"<")))

#b

g<-function (a){
      a<x
}

rowSums(sapply (y,g))
#g é o argumento FUN


#c
rowSums(vapply(y,g,FUN.VALUE = seq (along.with=x)))





