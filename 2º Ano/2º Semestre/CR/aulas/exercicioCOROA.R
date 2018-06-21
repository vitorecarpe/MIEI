f<- function(...){
	res<-c();
	i<-"coroa";
	moeda<-c("cara","coroa")
	while(i == "coroa"){
	    i = sample(moeda,1);
	    ##print(i);
	    res=c(res,i);
	}
	sum(res=="coroa");
}

tab<-table(replicate(1000,f()))
prop.table(tab)#frequências relativas
plot(prop.table(tab))




#CENAS DO TRAFEGO

traf<-read.table("trafico.txt",header=T)
str(traf)
attach(traf) #
names(traf)
tab<-table(limite)
barplot(tab,border="red", main="Indicaçâo limite velocidade")

ac<-table(acidentes)
ac #a variavel toma um nº elevado de valores dif
hist(acidentes, prob = T, ylab = "densidade", density = 31, main = "dist do nº de acidentes")
boxplot (acidentes,horizontal = T)
boxplot (acidentes,plot = F) $out # apenas os outlierz

ac <- split (traf, limite)
acs<-ac[[1]]
acc<-ac[[2]]
dim(acs)
dim(acc)
nrow(acs) #nº de estrada sem indicador de limite de velocidade

detach(traf)

#cenas de cereais


cereais <- read.csv ("Cereais.csv", header = T)
str(cereais)
plot(cereais$area ~ cereais$ano)

#sobrepor a reta y = 523001.7 - 258.8x
abline ( a = 523001.7, b = - 258.8)

