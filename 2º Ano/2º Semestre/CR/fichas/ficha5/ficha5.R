each <- split(cii, cii$Protein.Name)
nlinhas <- lapply(each, nrow)

nome <- unique(cii$Protein.Name)
length(nome) == length(nlinhas)

class(each)
# list

class(each[[1]])
# data.frame

ci <- data.frame(Prot.Name = nome, Prot.length = nlinhas)
head(ci)
dim(ci)

# 1. Considere a tabela de dados guardada num objeto da classe data frame, CO2 no R.
# Estes dados referem-se ao consumo de CO2 em plantas de duas regiões distintas
# (Mississippi e Quebec) quando expostas a diferentes concentrações de CO2 ou
# quando sujeitas, ou não, a arrefecimento.

# (a) Verifique a informação contida na tabela de dados em causa (recorra ao help),
# a dimensão, nomes das variáveis e classe de cada um dos objetos.

?CO2
dim(CO2) # Ddimensão
str(CO2)
colnames(CO2) # nome das variáveis.
class(CO2[["Plant"]])
# [1] "ordered" "factor" 
class(CO2[["Type"]])
# [1] "factor"
class(CO2[["Treatment"]])
# [1] "factor"
class(CO2[["conc"]])
# [1] "numeric"
class(CO2[["uptake"]])
# [1] "numeric"

# (b) Quantas plantas foram sujeitas a tratamento (Treatment) de arrefecimento
# (chilled)?

length(which(CO2[["Treatment"]] == "chilled"))
# or
table(CO2$Treatment)
table(CO2$Treatment)[["chilled"]]

# (c) Defina um subconjunto de dados apenas com as plantas da região do Quebec.
# Exporte-o para um documento txt.

CO2Q <- split(CO2, CO2$Type)[[1]]
CO2Q <- subset(CO2, CO2$Type == "Quebec")
# sem usar funções
CO2Q <- CO2[CO2$Type == "Quebec", ]
# == GUARDAR EM TXT ==
# ‘write.table’ prints its required argument ‘x’ (after converting
# it to a data frame if it is not one nor a matrix) to a file or
# connection.
write.table(CO2Q, "CO2Q.txt")

# ==========================
# Elimina a coluna relativa a type em CO2Q e acrescente uma coluna com
# informação qualtitaativa acerca da variável conc.
# A coluna deve ter 5 níveis de conc.
# very small, small, moderate, high, very high:

# Eliminar a coluna
# subset -> Return subsets of vectors, matrices or data frames which meet
# conditions.
CO2new <- subset(CO2, select = -c(Type))

# Efetuar uma partição de conc em 5 partes iguais (cut)
# ‘cut’ divides the range of ‘x’ into intervals and codes the values
# in ‘x’ according to which interval they fall.  The leftmost
# interval corresponds to level one, the next leftmost to level two
# and so on.
# cut(x, breaks, labels = NULL, include.lowest = FALSE, right = TRUE,
# dig.lab = 3, ordered_result = FALSE, ...)
conc5 <- cut(CO2$conc, 5, labels = c("very small", "small", "moderate",
 "high", "very high"))

# Adicionar a nova coluna
CO2$concSize <- conc5

# Acrescente ainda uma outra coluna relativa ao uptake: Se uptake for maior
# que a mediana, é "high", se for inferior ou igual à mediana "low".
x <- median(CO2$uptake) # mediana
CO2$uplevels <- ifelse(CO2$uptake > x, "great", "low")
# ifelse é a versão vetorizavel de if (...) ... else ...

# ==========================
# (d) Defina uma matriz com os dados da concentração de CO2 e de uptake. Atribua
# nomes às linhas (identificação da planta) e às colunas (strings concentra e
# consumo).

mat <- cbind(CO2$conc, CO2$uptake)
colnames(mat) <- c("concentra", "consumo")
rownames(mat) <- CO2$Plant

# alt
CO2Matrix <- data.matrix(CO2, rownames.force = NA)
CO2Res <- subset(CO2Matrix, select = -c(Plant,Type,Treatment))
colnames(CO2Res) <- c("concentra","consumo")
PlantNames <- CO2[,1]
rownames(CO2Res) <- PlantNames

CO2Matrix <- data.matrix(CO2, rownames.force = NA)
CO2Matrix[, (colnames(CO2Matrix) %in% c("Plant", "Type", "Treatment", "concSize", "uplevels") == FALSE)]
colnames(CO2Matrix) <- c("concentra", "consumo")
rownames(CO2Matrix) <- CO2$Plant

# (e) Usando a matriz definida na alínea anterior, selecione os valores de
# concentração e consumo da planta identificada com Mc3.
mat[which(rownames(mat) == "Mc3"),]
# Crie um vetor com os valores de consumo a partir desta matriz.
as.vector(mat[,colnames(mat) == "consumo"])

# (f) Num campo com 1000 plantas do tipo QC3, sujeitas a uma concentração
# constante de 675 mL/L, qual o consumo de CO2?
mat <- mat[which(rownames(mat) == "Qc3"),]
concI <- mat[mat[,"concentra"] == 675, "consumo"]
consCO2 <- concI * 1000

# (g) Em que condições o consumo de CO2 é o mais elevado?
maxC <- max(uptake)
CO2[which(CO2$uptake == maxC), "Treatment"]
# Obtenha a mesma informação para cada uma das regiões.
tapply(CO2$uptake, CO2$Type, max)

# (h) Qual a média de consumo de CO2 realizado pelas plantas da região do
# Mississipi, sujeitas a tratamento?
CO2m <- split(CO2, Type)[["Mississippi"]]
CO2mT <- CO2m[CO2m$Treatment == "chilled",]
mean(CO2mT$uptake)

# Elaborar um gráfico de dispersão de uptake em função de conc.
plot(CO2$uptake ~ CO2$conc, xlab = "conc CO2 ar", ylab = "consumo CO2")
plot(CO2[c("conc","uptake")])

# Quantas medições para cada concentração do ar?
table(CO2$conc)

# Quantas medições para cada concentração do ar, em cada região?
tapply(CO2$conc,CO2$Type,table)


table(CO2$Plant,CO2$Type)
# Isto significa que há repetições de medições... O data.frame está no formato
# long. Queremos passar para o wide format...

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


# This function reshapes a data frame between ‘wide’ format with
# repeated measurements in separate columns of the same record and
# ‘long’ format with the repeated measurements in separate records.

reshape(data, varying = NULL, v.names = NULL, timevar = "time", idvar = "id",
ids = 1:NROW(data))

CO2.Wide <- reshape(CO2, v.names = "uptake", timevar = "conc", idvar = c("Plant", "Type", "Treatment"), direction = "wide")
CO2.Wide
head(CO2.Wide)
tail(CO2.Wide)
dim(CO2.Wide)

# ================== Exercício 2
# Considere o data frame airquality que contém dados sobre a qualidade do ar em
# Nova York entre maio e setembro de 1973.
# (a) Visualize a estrutura do data frame (use a função str).
str(airquality)
# (b) Verifique quantos valores dos campos Ozone e Solar.R não estão
# disponı́veis (missing values – Not Available; função de teste: is.na).
# Ozone
aqO <- airquality$Ozone
sum(is.na(aqO))

# Solar.R
aqS <- airquality$Solar.R
sum(is.na(aqS))

# (c) Quais os ı́ndices das linhas que têm missing values na variável Ozone?
which(is.na(aqO))

# (d) Qual o valor médio da radiação solar durante o mês de julho?
aQJ <- airquality[airquality$Month == 7, ]
mean(aQJ$Solar.R)

# (e) Defina um novo data frame a partir desta em que não existam observações NA.
toEO <- which(is.na(aqO))
toES <- which(is.na(aqS))
toE <- unique(c(toES, toEO))
novoDF <- airquality[-toE,]

# (f) Defina uma função que, para cada um dos meses de maio a setembro,
# calcule o máximo, mı́nimo, média, mediana e desvio padrão de radiação solar,
# usando o novo data frame definido na alı́nea anterior.

func <- function(x) {
	cat("MAX:", max(x), "\n")
	cat("MIN:", min(x), "\n")
	cat("MEDIA:", mean(x), "\n")
	cat("MEDIANA:", median(x), "\n")
	cat("DESVIO:", sd(x), "\n")
	cat("============\n")
}
tapply(novoDF$Solar.R, novoDF$Month, func)


# (g) Com a função boxplot construa diagramas de extremos e quartis para a
# concentração de Ozono por mês e para a temperatura por mês.

ozone <- novoDF$Ozone
month <- novoDF$Month
boxplot(ozone ~ month)

temp <- novoDF$Temp
boxplot(temp ~ month)

# (h)
tapply(novoDF$Temp, novoDF$Month, mean)
# (h) Use a função split para obter as temperaturas por mês.
tempPM <- split(novoDF, month)
# Tendo em conta # a classe do resultado obtido, 
class(tempPM)
# > [1] "list"
# obtenha a média das temperaturas por mês usando uma função adequada
# da famı́lia apply.
meanOfEach <- sapply(tempPM, '[[', "Temp")
sapply(meanOfEach, mean)
# (i) Obtenha a média das concentrações de Ozono e a média das temperaturas,
# por mês, usando agora a função aggregate.

# Splits the data into subsets, computes summary statistics for
# each, and returns the result in a convenient form.
aggregate(novoDF$Ozone ~ novoDF$Month, novoDF, mean)
# Também se pode usar a tapply
tapply(novoDF$Ozone, novoDF$Month, mean)

# 3. A tabela de dados em Excel bnames contém a percentagem de bebés com
# cada um de 6872 nomes próprios, por ano, do género masculino ou feminino,
# registados nos EUA desde 1880 até 2008.

# (a) Importe a tabela usando a função read.csv, guardando-o no objeto
# nomEUA, verifique a classe de nomEUA e a sua estrutura.
nomEUA <- read.csv("bnames.csv")
class(nomEUA)
# [1] "data.frame"
str(nomEUA)

# (b) Classifique as variáveis constantes no data.frame e passe a variável
# year para a classe factor.
class(nomEUA$year)
# [1] "integer"
class(nomEUA$name)
# [1] "factor"
class(nomEUA$percent)
# [1] "numeric"
class(nomEUA$sex)
# [1] "factor"
nomEUA$year <- as.factor(nomEUA$year)

# (c) Quantos bebés foram avaliados em cada ano?
table(nomEUA$year)
# Quantas meninas?
girls <- nomEUA[nomEUA$sex == "girl",]
table(girls$year)
# Qual o nome mais popular entre as meninas no ano de 1880?
girls1880 <- girls[girls$year == 1880,]
girls1880[girls1880$percent == max(girls1880$percent),]$name
# [1] Mary
# E no ano 2008?
girls2008 <- girls[girls$year == 2008,]
head(girls2008,1)$name
# [1] Emma
# E entre os meninos?
boys <- nomEUA[nomEUA$sex == "boy",]
boys2008 <- boys[boys$year == 2008,]
head(boys2008,1)$name
# [1] Jacob
# (d) Faça uma ordenação por ano e por género dos nomes (do mais popular ao
# menos popular).

# Encontre uma forma automática de ir buscar o nome mais popular de menina
# e de menino em cada ano para todos os anos.
boys <- nomEUA[nomEUA$sex == "boy",]
byYear <- split(boys, boys$year)
fun <- function(x) {
	return (head(x, 1))
}
list <- lapply(byYear, fun)
sapply(list, '[[', "name")

girls <- nomEUA[nomEUA$sex == "girl",]
byYear <- split(girls, girls$year)
list <- lapply(byYear, function(x) {head(x,1)})
sapply(list, '[[', "name")

nomOrd <- nomEUA[order(nomEUA$percent, decreasing = T),]
nomOrdSex <- split(nomOrd,nomOrd$sex)
nomOrdSexYear <- lapply(nomOrdSex,function (x) {split(x,x$year)})
unlist <- unlist(nomOrdSexYear, recursive = FALSE)
list <- lapply(unlist, function(x) {head(x,1)})
sapply(list, '[[', "name")

split <- split(nomEUA,year)
for(i in 1: length(split))
    split[[i]] <- split[[i]][rev(order(percent)),]

# 4. Em 1880 Christian Zeller, matemático alemão, notou que se percorrermos
# um ano de março a fevereiro, o número acumulado de dias em cada mês forma
# quase uma linha reta que passa quase pela origem (por se ter passado fevereiro
# para ultimo lugar).
# A fórmula que aqui se apresenta pode ser usada para determinar o dia da semana
# em função do dia do mês, do mês - 2, e do ano. De facto, março é 1, abril 2,
# e assim sucessivamente até dezembro 10. O mês de janeiro é 11 e fevereiro 12
# (último mês) ambos do ano precedente. O ano (com 4 dı́gitos, por exemplo 1965),
# por sua vez, encontra-se dividido em século (os primeiros dois dı́gitos +1) e
# ano (os últimos 2 dı́gitos). Por exemplo 29 de mar de 1965, corresponde ao dia
# 29, do mês 1 do ano 65 do século 20, enquanto que 29 de janeiro de 1965,
# corresponde ao dia 29, do mês 11, do ano 64 (ano precedente!) do século 20.
# Considerando d o dia, m o mês menos 2, a os últimos dois dı́gitos do ano e s os
# primeiros dois dı́gitos de ano, o dia da semana pode ser calculado através d
# resto da divisão de [2.6m − 0.2] + d + a + [a/4] + [s/4] − 2s por 7 (sete dias
# da semana) onde 0 é sábado, 1 é domingo e assim sucessivamente até 6 sexta
# feira. A notação [ ] é usada para designar a parte inteira de um número.
# Por exemplo [6.3] é 6, assim comotambém é 6, [6.95]. No R usa-se a função
# floor com este objetivo. Implemente em R o algoritmo de Zeller através de uma
# função com argumentos dia, mês (1-janeiro a 12-dezembro), ano com 4 dı́gitos
# (exemplo 2002). Esta função deve ser vetorizavel, ou seja, deve ser possı́vel
# obter simultaneamente os dias da semana de 22 de nov de 1965, 19 de ago de
# 1992 e 8 de jan de 1997 (f(c(22,19,8),c(11,8,1), c(1965,1992,1997))).
# Caso necessite de usar, numa primeira abordgem, a instrução if else, (else
# não é obrigatório), a sintaxe é if(condicao verdadeira) ... else ...
# No entanto esta instrução não é uma função vetorizavel. Existe uma opção
# vetorizavel desta instrução – função ifelse (consulte a ajuda do programa).
# Repare que se o mês for janeiro ou fevereiro, há necessidade de somar 12 e,
# simultaneamente, subtrair 1 ao ano.


zeller <- function(da, ma, aa) {
	d <- da
	m <- ma
	a <- strtoi(substr(aa, start = 3, stop = 4))
	s <- strtoi(substr(aa, start = 1, stop = 2)) + 1;
	a <- ifelse((m == 1 || m == 2), a - 1, a)
	m <- ifelse((m == 1 || m == 2), m + 12, m)
	return ((floor(2.6 * m - 0.2) + d + a + floor(a/4) + floor(s/4) - 2*s) %% 7)
}

# 5. Considere a sucessão definida recursivamente por x 0 = 1, x 1 = 2;
# x j = x j−1 + 2 para j = 1, 2, .... x j−1
# Escreva uma função com um único argumento n que devolva os n-1 termos da
# sucessão {x j } j≥0 . A sintaxe para os comandos associados a instruções
# condicionais, loops (iterações), ou seja usados para control flow (fluxo
# de execução) pode ser vista na ajuda do R fazendo, por exemplo, ?"for"
# (tal como para operadores aritméticos são usadas aspas no acesso ao help).

suc <- function(n) {
	v <- c(1,2)
	for(i in 3:(n-1)) v[i] <- v[i-1] + (2/v[i-1])
	return (v)
}

# 6. Considere a experiência que consiste em contar o número de vezes que sai
# coroa antes que saia pela primeira vez cara no lançamento de uma moeda
# equilibrada. Simule esta experiência usando a função while. Repita a experiência 
# 4000 vezes e obtenha uma tabela onde figurem os resultados obtidos.

coroa <- function() {
	moeda <- c("coroa", "cara")
	k <- 0
	while (sample(moeda, 1) == "cara") k <- k+1
	return (k)
}

results <- replicate(4000, coroa())
tabR <- table(results)

# Elabore um gráfico adequado.
barplot(tabR)


# (7) Pretende-se estudar o comportamento do valor médio do número de pontos obtido
# em 10 lançamentos (nas mesmas condições) de um dado equilibrado. Comece por
# simular um resultado para 10 lançamentos de um dado equilibrado e calcule a
# sua média. Repita agora o procedimento anterior 400 vezes (simule 400 vezes 
# o lançamento de um dado equilibrado 10 vezes). Calcule as médias observadas
# das 400 repitações da experiência e elabore um gráfico adequado. Que intervalo
# de valores lhe parece razoável para o valor médio do número de pontos obtido
# em 10 lançamentos?

dado <- function() {
	dado <- 1:6
	sum <- 0
	for (i in 1:10) sum <- sum + sample(dado, 1)
	return (sum/10)
}

results <- replicate(400, dado())
plot(results, type = "l")
# 3... 4 : - )