library( neuralnet )
library( hydroGOF )
library( leaps )
library( arules )

# ler dataset de um ficheiro csv

dados <- read.csv("C:\\Users\\Utilizador\\Desktop\\SRCR\\tp3\\winequality\\red.csv", header=TRUE, sep=";",dec=".")

# dividir os dados iniciais em casos para treino
treino <- dados[1:1000, ]

#casos para teste:
teste <- dados[1001:1599, ]

# formulas para o dataset
formula01 <- quality ~ alcohol
formula02 <- quality ~ volatile.acidity + alcohol
formula03 <- quality ~ volatile.acidity+ sulphates + alcohol
formula04 <- quality ~ volatile.acidity + total.sulfur.dioxide + sulphates + alcohol
formula05 <- quality ~ volatile.acidity + chlorides + total.sulfur.dioxide + sulphates + alcohol
formula06 <- quality ~ volatile.acidity + chlorides + total.sulfur.dioxide + pH + sulphates + alcohol
formula07 <- quality ~ volatile.acidity + chlorides + free.sulfur.dioxide + total.sulfur.dioxide + pH + sulphates + alcohol
formula08 <- quality ~ volatile.acidity + citric.acid + chlorides + free.sulfur.dioxide + total.sulfur.dioxide + pH + sulphates + alcohol
formula09 <- quality ~ volatile.acidity + citric.acid + residual.sugar + chlorides + free.sulfur.dioxide + total.sulfur.dioxide + pH + sulphates + alcohol
formula10 <- quality ~ fixed.acidity + volatile.acidity + citric.acid + residual.sugar + chlorides + free.sulfur.dioxide + total.sulfur.dioxide + pH + sulphates + alcohol   
formula11 <- quality ~ fixed.acidity + volatile.acidity + citric.acid + residual.sugar + chlorides + free.sulfur.dioxide + total.sulfur.dioxide + density + pH + sulphates + alcohol   

# treinar a rede neuronal para usar as variaveis xxxx como input e quality como output

rna <- neuralnet( formula07, treino, hidden = c(4),lifesign = "full", threshold = 0.1,algorithm = "rprop+",rep=1)

# definir variaveis de input para teste
teste.01 <- subset(teste, select = c("alcohol"))
teste.02 <- subset(teste, select = c("volatile.acidity","alcohol"))
teste.03 <- subset(teste, select = c("volatile.acidity","sulphates","alcohol"))
teste.04 <- subset(teste, select = c("volatile.acidity", "total.sulfur.dioxide","sulphates","alcohol"))
teste.05 <- subset(teste, select = c("volatile.acidity","chlorides","total.sulfur.dioxide","sulphates","alcohol"))
teste.06 <- subset(teste, select = c("volatile.acidity","chlorides","total.sulfur.dioxide","pH","sulphates","alcohol"))
teste.07 <- subset(teste, select = c("volatile.acidity","chlorides","free.sulfur.dioxide","total.sulfur.dioxide","pH","sulphates","alcohol"))
teste.08 <- subset(teste, select = c("volatile.acidity","citric.acid","chlorides","free.sulfur.dioxide","total.sulfur.dioxide","pH","sulphates","alcohol"))
teste.09 <- subset(teste, select = c("volatile.acidity","citric.acid","residual.sugar","chlorides","free.sulfur.dioxide","total.sulfur.dioxide","pH","sulphates","alcohol"))
teste.10 <- subset(teste, select = c("fixed.acidity","volatile.acidity","citric.acid","residual.sugar","chlorides","free.sulfur.dioxide","total.sulfur.dioxide","pH","sulphates","alcohol"))
teste.11 <- subset(teste, select = c("fixed.acidity","volatile.acidity","citric.acid","residual.sugar","chlorides","free.sulfur.dioxide","total.sulfur.dioxide","density","pH","sulphates","alcohol"))


# testar a rede com os novos casos
rna.resultados <- compute(rna, teste.07)

# imprimir resultados
resultados <- data.frame(atual = teste$quality, previsao = rna.resultados$net.result)

# imprimir resultados arredondados
resultados$previsao <- round(resultados$previsao, digits=0)

# calcular o RMSE
rmse(c(teste$quality),c(resultados$previsao))

##################################################################################
# seleção de variáveis mais significativas
funcao <- quality ~ fixed.acidity + volatile.acidity + citric.acid + residual.sugar + chlorides + free.sulfur.dioxide + total.sulfur.dioxide + density + pH + sulphates + alcohol
selecao <- regsubsets(funcao1,dados,nvmax=11)
summary(selecao1)
