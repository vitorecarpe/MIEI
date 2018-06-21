a<-c(-1,5,3)
A <- c(a,"a")#coerção
class(A)
sqrt(a)
v<-seq (1,5,by=0.3)
seq(1,5,length.out=10)
u<-seq(1,5,along.with=a)
a+v
a+u
w<-c(a,u)
1:12+w
a*u
a%%u
1:30
30:1
c(1:30,29:1)
c(rep(FALSE,3),TRUE,rep(FALSE,5),rep(TRUE,4))
nomes <- c("Ana","Luísa","Juliana")
typeof(nomes)
length(nomes)
nchar(nomes)
vet <- c(4,6,3)
rep(vet,10)
q<-rep(vet, c(q,4)10)
c(rep(4,10),rep(6,20),rep(3,30))#i
rep(vet,c(10,20,30))#i.alt
rep(vet,length.out=31)#i.alt2
vetor <- c(-3,5,-6,7,8,-0.5,2)
v1 <- vetor[c(1,2,5)]
v2 <- vetor[c(1,3,6)]
v1+v2
v1*v2
vetor+v2
(rep(3,32))^(0:31)
(0.1^seq(3,36,3))*(0.2^seq(1,34,3))
(2^(1:25)) / (1:25)
sum ((10:100)^3 + 4*(10:100)^2)
sum (2^(1:25) / (1:25) + 3^(1:25) / (1:25)^2)
LETTERS[c(1,2,3,4,5)]
LETTERS[1:5]
paste(LETTERS[1:5],letters[5:1])
paste(LETTERS[1:5],letters[1:5],LETTERS[5:1],letters[26:22])
rep((paste(LETTERS[1:3],letters[1:3])),5)
paste("passo",(1:6))

set.seed(543265)
vetor_x <- sample(0:999, 250, replace=T)
vetor_y <- sample(0:999, 250, replace=T)

vetor_y[c(2,49,215)]
vetor_x[c(2:250)]
vetor_x[-1]
vetor_x[-(67:213)]
vetor_y[vetor_y > 600]
vetor_y[which(vetor_y>600)] # menos if.
vetor_x[which(vetor_y>600)] # menos if.
vetor_x[vetor_y > 600]
sum(vetor_y > 200 & vetor_y < max(vetor_y))
sum(vetor_x %% 2 == 0)
order(c(5,3,6))
vetor_x[order(vetor_y)]
vetor_y[order(vetor_y)]
sort(vetor_y)


#exerciciozinho eae deiz ailinia hã) 
x <- 28
y <- 2 : (x-1)
d <- y [x %% y == 0]
if (x == sum(d) + 1) print ("x é perfeito") else print ("x não é perfeito")

perfeito <- function(a){
         y <- 2:(a-1)
         d <- y[a%%y == 0]
         if (x == sum(d) + 1) print ("x é perfeito") 
         else print ("x não é perfeito")
print ("txi amo")
}



#exerciciozinho eae deiz ailinia bê)

primo <- function(a){
        y <- 1:(a)
        d <- a%%y == 0
        if (sum(d) == 2) print ("x é primo") 
        else print ("x não é primo")
print ("txi amo")
}



#exercicio 11

#a)

vetor_x <- 1:10
vetor_y <- 21:30
n <- length(vetor_x)
vetor_y[-1] - vetor_x[-n]

#c)
vetor_x <- 1:10
-x[-(1:2)] + 2*x[-c(1,n)] + x[-c(n-1,n)] # está mal



#exercicio 12

#b
fb <- function(x){ 
1/sqrt(2*pi) * exp(-x^2 / 2)
}
curve (f(x), from = -5 , to = 5)
lines (c(-2,2),c(f(-2),f(2)), type = "h", lty = 2, col = "brown")

rect.int (seq (0,1,0.005),fb)


#welele



int.ret <- function (x,f){

if (!is.numeric(x)){
 stop ("x is not numeric")
}
if (!is.function(f)){
 stop ("f is not a function")
}


#obter o comprimento
#da variavel de integraçao
#e da funçao integranda
 n.points <- length (x)
#pontos médios
 mid.points <- 1/2 (x[2 : n.points] + x[1:(n.points-1)])

#calcular os valores de f nos midpoints 
 fmid.points <- f (mid.points)

#calcular as larguras dos intervalos 
#entre pares de pontos ao longo de variáveis de integração
 interval.width <- x[2 : n.points] - x[1 : (n.points -1)]

#fazer a integraçao numerica
 ret.int <- sum (interval.width * fmid.points)

#print the result
 return (ret.int)

}



#exercicio12 

#c)
fc <- function (x){
4/(1+x^2)
}

x <- seq(0,1,0.005)

#rect.int <- (seq (0,1,0.005),fc)

#rect.int <- function (x,f)

rect.int <- function (x,fc){

if (!is.numeric(x)){
 stop ("x is not numeric")
}
if (!is.function(fc)){
 stop ("f is not a function")
}

#obter o comprimento da variavel de integraçao e da funçao integranda
 n.points <- length (x)
#pontos médios
 mid.points <- 1/2 (x[2 : n.points] + x[1:(n.points-1)])

#calcular os valores de f nos midpoints 
 fmid.points <- fc (mid.points)

#calcular as larguras dos intervalos entre pares de pontos ao longo de variáveis de integração
 interval.width <- x[2 : n.points] - x[1 : (n.points -1)]

#fazer a integraçao numerica
 rect.int <- sum (interval.width * fmid.points)

#print the result
 return (rect.int)
}








f <- function (x){

4/(1+x^2) 

}

plot (seq (-1,2,by=0.01),f(seq(-1,2,by = 0.01), type = "l", f(x), from = -1, to = 2, xlab = "xvalues", ylab = expression (frac (4,1+x^2)), main = "gráfico de f"))

curve ( f(x), from = -1, to = 2, xlab = "xvalues", ylab = expression (frac (4,1+x^2)), main = "gráfico de f")
#curve e plot - high level
lines (c(0,1), c(f(0),f(1),type = "h")
#line - low levels
legend (locator (1), legend = "graf f", lty = 1, col = "red")
#interative - legend