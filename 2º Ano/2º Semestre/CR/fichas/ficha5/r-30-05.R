CO2 long format -> wide format
table(CO2$Plant)
uptake -> consumo
table(CO2$conc)
CO2$conc <-as.factor(CO2$conc)
reshape
CO2.wide <- reshape(CO2, v.names = "uptake", timevar = "conc", idvar = c("Plant", "Type", "Treatment"), direction ="wide")

wide format -> long format
-> carregar package (crabs ... ??crabs):
library(MASS) <- carregar package
wide format -> 200 linhas, 200 caranguejos
reshape(crabs, varying = c("FL", "RW", "CW", "BD"), v.names="value", timevar = "group", direction="long")
tapply(crabs[,4:6], crabs[,1:2],summary)
aggregate(crabs[,4:8], crabs[,1:2], summary)
by(crabs[,4:8], crabs[,1:2], summary)