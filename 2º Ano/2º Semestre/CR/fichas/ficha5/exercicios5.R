# FICHA 5

# EXERCÍCIO 1

?CO2
str(CO2)
class(CO2[["conc"]])
class(CO2["conc"])

table(CO2$Treatment)
# sum(CO2$Treatment == "chilled")

# Condição é sobre linhas...
CO2Q <- CO2[CO2$Type == "Quebec",]
str(CO2Q)
dim(CO2Q)
write.table(CO2Q,"CO2Que.txt")

# Elimine a coluna relativa a Type em CO2Q e acrescente uma coluna com
# informação qualitativa acerca da variável conc.
# A coluna deve ter 5 níveis de conc: "very small","small","moderate","high",
# "very high". Efetuar uma partição de conc em 5 partes iguais.
# Chame à nova coluna concSize.
?cut
conc5 <- cut(CO2Q$conc,5,labels=c("very_small","small","moderate","high","very high"))
str(conc5)
CO2Q$concSize <- conc5
str(CO2)
table(CO2Q$concSize)

# Acrescente ainda uma outra coluna relativa a uptake: Se uptake > mediana, "great"
#										     <= mediana,"low"

m <- median(CO2Q$uptake)
CO2Q$uplevels <- ifelse(CO2Q$uptake > m,"great","low")
str(CO2Q)
# ifelse é a versão vetorizavel de if (...) ... else ...

# Voltar a CO2

str(CO2)
# Que plantas têm um consumo de CO2 mais elevado (max(CO2$uptake))?
CO2[which(CO2$uptake == max(CO2$uptake)),-5]
# Calcular o máximo de CO2$uptake partido por região (Type)
maxs <- tapply(CO2$uptake,CO2$Type,max)
CO2M <- CO2[CO2$Type=="Mississippi",]
CO2M[which(CO2$uptake[CO2$Type=="Mississippi"]==35.5),-5]
# CO2M[which(CO2$uptake[CO2$Type=="Mississippi"]==maxs["Mississippi"]),-5]

# As concentrações do ar deverão ser controladas, dado que existem 6 
# experiênciaspara o Quebec e 6 para o Mississippi em que a concentração
# do ar é 1000...
CO2[CO2$conc==1000,]

# Elaborar um gráfico de dispersão de uptake em função de conc
plot(CO2$uptake ~ CO2$conc, xlab="conc CO2 ar", ylab="consumo CO2")
PLOT(CO2[c("conc","uptake")])
# Mas não aparecem as concentrações exatas no eixo do xx
# TENTAR COLOCAR O EIXO DO X A DAR OS VALORES EXATOS? ......................

# Quantas medições para cada concentração do ar?
table(CO2$conc)

# Quantas medições para cada concentração do ar, em cada região?
tapply(CO2$conc,CO2$Type,table)

table(CO2$Plant,CO2$Type)

# Isto significa que há repetições de medições... O data.frame está no formato
# long. Queremos passar para o white format...

CO2S <- CO2[c("conc","uptake")]
dim(CO2S)
str(CO2S)
CO2S$conc <- as.factor(CO2S$conc)
class(CO2S$conc)
levels(CO2S$conc)

# Wide Format:
table(CO2$Plant)
head(CO2)
table(CO2$conc)
CO2$conc <- as.factor(CO2$conc)
str(CO2)
table(CO2$Plant)

?reshape
CO2.Wide <- reshape(CO2,v.names="uptake",timevar="conc",idvar=c("Plant","Type","Treatment"),direction="wide")
head(CO2.Wide)
tail(CO2.Wide)
dim(CO2.Wide)

# De wide para long - data.frame crabs
??crabs
library(MASS)
?crabs            # wideformat -> 200 rows -> 200 crabs?reshape
crabs.Long <- reshape(crabs,varying=c("FL","RW","CL","CW","BD"),v.names="Value",timevar="group",direction="long")
crabs.Long

# Extensão de tapply
x <- tapply(crabs$FL,crabs[,1:2],summary)
class(x)
x[1,]
x[2,]
y <- tapply(crabs[,4:6],crabs[,1:2],summary)
# Não funciona...

?aggregate
sum.agr <- aggregate(crabs[,4:8],crabs[,1:2],summary)
class(sum.agr)
dim(sum.agr)
names(sum.agr)

?by
sum.by <- by(crabs[,4:8],crabs[,1:2],summary)
class(sum.by)
length(sum.by)
str(sum.by)

class(sum.by[[1]])
t1 <- sum.by[[1]]; t1
dim(t1)
t1[5,]

# CARÁTER VETORIZÁVEL DAS FUNÇÕES
a <- 1
if (a==1) "sim" else "não"
"if" (a==1,"sim","não")

"+" (2,3) # Everything is a dunction call
# Se "if" fosse vetorizável, seria possível passar um vetor (booleano) no 1º argumento

a <- 1:10
"if" (a<5,"sim","nao") # não funciona
ifelse (a<5,"sim","nao")

# Duplo somatório
s <- 0
for (i in 1:100) {
	for (j in 1:(i+1)) {
		s <- sum(s,1/(i*j))
		print(s);
	}
}
s

# pode-se usar a função outer -> Saber explicar o interesse desta função