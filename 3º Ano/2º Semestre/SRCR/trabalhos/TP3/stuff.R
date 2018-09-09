library( neuralnet )
library( hydroGOF )
library( leaps )
library( arules )

# ler dataset de um ficheiro csv
dados <- read.csv("D:\\Users\\Cesar\\Documents\\Aulas\\1718\\miei3srcr\\fichas\\ficha12\\creditset.csv")

# mostrar a "cabeça" do dataset
head(dados)

# dividir os dados iniciais em casos para treino...
treino <- dados[1:800, ]

# ... e casos para teste:
teste <- dados[801:2000, ]

# definição das camadas de entrada e saída da RNA
formula01 <- default10yr ~ LTI + age

# treinar a rede neuronal para usar a variavel LTI e age como input e degault10y como output
rnacredito <- neuralnet( formula01, treino, hidden = c(4), lifesign = "full", linear.output = FALSE, threshold = 0.1)

# desenhar rede neuronal
plot(rnacredito, rep = "best")

# definir variaveis de input para teste
teste.01 <- subset(teste, select = c("LTI", "age"))

# testar a rede com os novos casos
rnacredito.resultados <- compute(rnacredito, teste.01)

# imprimir resultados
resultados <- data.frame(atual = teste$default10yr, previsao = rnacredito.resultados$net.result)

# imprimir resultados arredondados
resultados$previsao <- round(resultados$previsao, digits=0)

# calcular o RMSE
rmse(c(teste$default10yr),c(resultados$previsao))

#-----------------------------------------------------------------------------------------------------------------------
# seleção de variáveis mais significativas
funcao <- default10yr ~ income+age+loan+LTI
selecao <- regsubsets(funcao,dados,nvmax=3)
summary(selecao)

selecao <- regsubsets(funcao,dados,method="backward")
summary(selecao)

#------------------------------------------------------------------------------------------------------------------------
# discretizacao de atributos
nomes <- c(1,2,3,4,5)
income <- discretize(dados$income,method = "frequency",categories = 5,labels = nomes )
dados$income <- as.numeric(income)

#-------------------------------------------------------------------------------------------------------------------------
# ler dataset de um ficheiro csv
dados <- read.csv("C:\\Users\\Utilizador\\Desktop\\SRCR\\tp3\\winequality\\winequality-red-normal.csv",header=TRUE, sep=";",dec=".")
