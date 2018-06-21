#1
#a)
nul6<-matrix(0,6,6)
class(nul6)
dim(nul6)

#b)
m<-col(nul6)-row(nul6)
m<-abs(m)
m[m!=1]<-0

#c)
M<-m
diag(M) <- 1:6 #a diagonal passa a ser de 1:6
M

#d)
MT <- t(M)#aplica a transposta a M(colunas passam a linhas e vice-versa)
M+MT #soma algebrica(elemento a elemento)
M*MT #produto algebrico(elemento a elemento)
M%*%MT #Produto matricial(interno--vetor *vetor)

#e)
SM <- M[c(1,2,3),c(2,4,6)]#devolve a matriz formada pela linhas 1 2 3 e col 2 4 6
which(M%%2 == 0 & M != 0,arr.ind = TRUE) #indices da matriz pares , dif de 0

#2

#a)
outer(0:4,0:4,"+") #soma individualmente cada elemento do primeiro array ao segundo array completo

#b)
apply(M,2,mean) #aplica a funçao media a todas as colunas (dimensao 2) da matriz M
#é a mesma coisa que o ciclo for:
	#r <- c()
	#k <- ncol(M)
	#for(i in 1:k){ r[i]<-mean(M[ , i ])}
	#r

#c)
outer(seq(0,20,5),seq(0,20,5),"-")

#3

set.seed(987654321)
m1<-matrix(sample(10,60,replace=T),nr=6) 
#ou mat<-matrix(sample(1:10,60,replace=T),nr=6)
#dá 10 elementos, cada um está entre 1 e 60

#a)
m1 <- apply(mat,1,function(x){x > 4})
apply(m1,2,sum) #ou s<-colSums(m1) #vetor em que cada entrada está a soma dos elementos de cada coluna
#ou apply(mat > 4,1,sum)

#b)
which(apply(mat == 7,1,sum) == 2)

#c)
s<-colSums(m1)#faz o sumatorio de cada coluna
L<-outer(s,s,"+")#soma o sumatorio de cada coluna com cada o das outras colunas
L2<-lower.tri(L)#devolve true a baixo diagonal
which(L2 & L>75, arr.ind = T)faz a interseção de L com L > 75

#4)
#a
m<-matrix(0,nr = 20, nc = 5)
for(i in 1:20){
	for(j in 1:5){
	m[i,j]<-i^4/(3+j)
	}
}
sum(m)
#ou sum(outer((1:20)^4,3+(1:5),"/"))

#b
sum((1:20)^4/(3+outer((1:20),(1:5),"*")))
#ou
m<-matrix(0,nr = 20, nc = 5)
for(i in 1:20){
	for(j in 1:5){
	m[i,j]<-i^4/(3+j*i)
	}
}
sum(m)


#c
m<-matrix(0,nr = 10, nc =10)
for(i in 1:10){
	for(j in 1:i){
	m[i,j]<-i^4/(3+j*i)
	}
}
sum(m)

#ou
f<-function(i,j){
	(i>=j)*(i^4/(3+i*j))
}
sum(outer(1:10,1:10,"f"))