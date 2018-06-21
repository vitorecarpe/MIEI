lst<-list(x=1:50,y=matrix(-9:5,nc=3,nr=5),z=c(TRUE,FALSE,TRUE,TRUE))

lst1 <- function(n){
	x <- rnorm(n)
	medx <- mean(x)
	y <-  sign(medx)*rexp(n,abs(1/medx))
	k <- sum(abs(y) > abs(x))
	list(vetx = x, media = medx, vety = y, conta = k)
}

x = lst1(500)$vetx
y = lst1(500)$vety

par(mfrow = c(1,2))
hist(x,prob = T, density = 31,xlab = "x", ylab = "densidade")
hist(y,prob = T, density = 31,xlab = "y", ylab = "densidade")
hist(x,plot = FALSE)