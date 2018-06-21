# EXERCICIO MOEDA

# Considere a Exp que consiste em lançar uma moeda equilibrada
# até sair cara pela 1ª vez. O objetivo é obter ESTIMATIVAS para
# a probabilidade dos acontecimentos associados ao "nº de coroas
# obtido na real da Exp"

moeda <- c("cara","coroa")
f <- function (x){
	res<-c()
	i<-"coroa"
	while(i == "coroa"){
		i<-sample(moeda,1)
		res = c(res,i)
		}
	sum(res == "coroa")
	#print(res)
}

# Como queremos estimativas, temos de repetir a experiência várias vezes

replicate(10, f())

#OU

sapply(1:10,f)


#----------------- GRÁFICO ------------

tab<-table(replicate(1000,f()))
prop.table(tab) # frequências relativas
plot(prop.table(tab))
# -------------------------------------


######### EX4
##########   data.frame  ###############

# É uma tabela de dados com colunas que podem ser objetos de TIPOS
# DIFERENTES e representam variáveis (qualitativas ou quantitativas) que 
# são medidas em n indices (linhas). Está associada uma dimensão.
# As colunhas têm obrigatoriamente um nome associado.
# ----------------------------------------------

# Mudar a diretoria para o sítio onde estão os dados:

trafico<- read.table("trafico.txt",header=T)
str(trafico)
attach(trafico)
names(trafico)
tab<- table(limite)
barplot(tab,border="red", main="Indicações limite velocidade")
ac<-table(acidentes)
ac # a variável toma um nº elevado de valores diferentes
hist(acidentes, prob=T, ylab="densidade",density = 31, main = "dist do nº de acidentes")
# prob=T : obriga a que a área total do hist seja 1
boxplot(acidentes, horizontal = T)
out <- boxplot (acidentes, plot = F) $ out # da-me só os outliers


trafico[which(acidentes == out[1] | acidentes == out[2]| acidentes == out[3] | acidentes == out[4]),]
ac<-split(trafico,limite)

class(ac)
length(ac)
acs <- ac[[1]]
acc <- ac[[2]]


###### EX 5
cereais <- read.csv("Cereais.csv", header = T)
str(cereais)
plot(cereais$area~cereais$ano) #~ -> em função de.. neste caso área em função de ano, isto é y = area e x = ano
abline(a = 523001.7, b = -258.8)