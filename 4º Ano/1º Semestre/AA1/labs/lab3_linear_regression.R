
# Chapter 3 Lab: Linear Regression

library(MASS)
library(ISLR)

##########################
# Simple Linear Regression

# Boston: Housing values and other information about Boston suburbs
fix(Boston)
names(Boston)
?Boston
lm.fit=lm(medv~lstat)
lm.fit=lm(medv~lstat,data=Boston)
attach(Boston)
lm.fit=lm(medv~lstat)
lm.fit
summary(lm.fit)
names(lm.fit)
coef(lm.fit)
confint(lm.fit)

summary(medv) # Y response variable
summary(lstat) # X predictor/regressor/independent/explanatory variable 
#predict(lm.fit,data.frame(lstat=(c(5,10,15))), interval="confidence")
# IMPORTANT: predict Y for a given X0
predict(lm.fit,data.frame(lstat=(c(5,10,15))), interval="prediction")
plot(lstat,medv)
abline(lm.fit)
abline(lm.fit,lwd=3)
abline(lm.fit,lwd=3,col="red")
plot(lstat,medv,col="red")
plot(lstat,medv,pch=20)
plot(lstat,medv,pch="+")
plot(1:20,1:20,pch=1:20)

par(mfrow=c(2,2))
plot(lm.fit)
plot(predict(lm.fit), residuals(lm.fit))
plot(predict(lm.fit), rstudent(lm.fit))
plot(hatvalues(lm.fit))
which.max(hatvalues(lm.fit))
plot(lstat,medv, cex=0.6)
points(lstat[375],medv[375], col="red", pch="+", cex=1.2)
abline(lm.fit)
# On the basis of the residual plots, there is some evidence of non-linearity 



############################
# Multiple Linear Regression

lm.fit=lm(medv~lstat+age,data=Boston)
summary(lm.fit)
lm.fit=lm(medv~.,data=Boston)
summary(lm.fit)
library(car)
vif(lm.fit) #values low to moderate (high when above 10)
lm.fit1=lm(medv~.-age,data=Boston)
summary(lm.fit1)
lm.fit1=update(lm.fit, ~.-age)

step(lm.fit, direction="backward")


# Interaction Terms

summary(lm(medv~lstat*age,data=Boston))


# Non-linear Transformations of the Predictors

lm.fit2=lm(medv~lstat+I(lstat^2))
summary(lm.fit2)
lm.fit=lm(medv~lstat)
#IMPORTANT: compares two models M1 and M2
# H0: M1 is equivalent to M2 versus H1: Full Model (M2) is superior 
anova(lm.fit,lm.fit2)  

par(mfrow=c(2,2))
plot(lm.fit2)
# let's fit fifth-order polynomial
lm.fit5=lm(medv~poly(lstat,5))
summary(lm.fit5)
#summary(lm(medv~log(rm),data=Boston))



########################
# Qualitative Predictors 

# Carseats: Information about car seat sales in 400 stores
?Carseats
fix(Carseats)
names(Carseats)
summary(Carseats)

lm.fit=lm(Sales~.+Income:Advertising+Price:Age,data=Carseats)
summary(lm.fit)
attach(Carseats)
# contrasts function returns the coding that R uses for dummy variables
contrasts(ShelveLoc)

lm.fit=lm(Sales~.-Population-Education-Urban-US+Income:Advertising,data=Carseats)
summary(lm.fit)



###################
# Writing Functions
LoadLibraries
LoadLibraries()
LoadLibraries=function(){
  library(ISLR)
  library(MASS)
  print("The libraries have been loaded.")
}
LoadLibraries
LoadLibraries()


