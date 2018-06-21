
#3
#c
set.seed(987654321)
mat<-matrix(sample(10,60,replace=T),nr=6)
S<-colSums(mat) # vetor em que cada entrada está a soma dos elementos de cada coluna
outer(S,S,"+")
L<-outer(S,S,"+")>75
L2<-lower.tri(L, diag = FALSE)&L
Lfinal<-which(L2,arr.ind=TRUE)


# coluna 6 e 2; coluna 9 e 2; 10 e 2; 10 e 6; 10 e 9;


#4
#a
m<-matrix(0,nr=20,nc=5)
for(i in 1:20){
	for ( j in 1:5){
		m[i,j]=i^4/(3+j)
	}
}
sum(m)


sum(outer((1:20)^4,(3+(1:5)),"/"))


#b
sum((1:20)^4/(3+outer(1:20,1:5)))

mb<-matrix(0,nr=20,nc=5)
for(i in 1:20){
	for ( j in 1:5){
		mb[i,j]=i^4/(3+i*j)
	}
}
sum(mb)

#c
mc<-matrix(0,nr=10,nc=10)
for(i in 1:10){
	for ( j in 1:i){
		mc[i,j]=i^4/(3+j*i)
	}
}
sum(mc)
f<-function(x,y){
	(i>=j) * i^4(3+i*j)
}
m<-outer(1:10,1:10,f)
sum((1:10)^4/(3+outer(1:10,   )))--- por aqui lower tree




#5
#a
set.seed(123456789)
x<-sample(1:10,5)
y<-sample(1:11,6)

L<-outer(y,x,"<")
za<-colSums(outer(y,x,"<"))

#b
g<-function(a){a<x}
sapply(y,g) # tranposta de L
zb<-rowSums(sapply(y,g))

#c

zc<-rowSums(vapply(y,g,FUN.VALUE=seq(along.with=x)))

#d

