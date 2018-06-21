# De wide para long - data.frame crabs

??crabs
library(MASS)
?crabs
crabs.Long <- reshape(crabs, varying = c("FL","RW","CL","CW","BD"), v.names = "Value", timevar = "group", direction = "long")
crabs.Long

sum.agg <- aggregate(crabs[,4:8], crabs[,1:2], summary)
sum.by <- by(crabs[,4:8], crabs[,1:2], summary)

x <- tapply(crabs$FL, crabs[,1:2], summary)
class(x)
[1] "matrix"
x[1,]
x[2,]

# Splits the data into subsets, computes summary statistics for
# each, and returns the result in a convenient form.
# x: an R object.
# by: a list of grouping elements, each as long as the variables in
#	  the data frame ‘x’.  The elements are coerced to factors
# 	  before use.
# FUN: a function to compute the summary statistics which can be
# 	   applied to all data subsets.

sum.agg <- aggregate(crabs[,4:8],crabs[,1:2],summary)
class(sum.agg)
# [1] "data.frame"
dim(sum.agg)
# [1] 4 7
names(sum.agg)
# [1] "sp"  "sex" "FL"  "RW"  "CL"  "CW"  "BD" 


# Function ‘by’ is an object-oriented wrapper for ‘tapply’ applied
# to data frames.
# data: an R object, normally a data frame, possibly a matrix.
# INDICES: a factor or a list of factors, each of length ‘nrow(data)’.
# FUN: a function to be applied to (usually data-frame) subsets of ‘data’.

sum.by <- by(crabs[,4:8],crabs[,1:2],summary)

class(sum.by)
# [1] "by"
class(sum.by[[1]])
# [1] "table"
t1 <- sum.by[[1]]
t1
dim(t1)
t1[5,]


# CARÁTER VETORIZÁVEL DAS FUNÇÕES
a <- 1
if (a == 1) "sim" else "não"
"if"(a == 1, "sim", "não")

"+" (2,3) # Everything is a function call
# Se "if" fosse vetorizável, seria possível passar um vetor (booleano)
# no 1º argumento

a <- 1:10
"if"(a < 5, "sim", "não") # não funciona
ifelse(a < 5, "sim", "não")

# Duplo somatório
s <- 0
for (i in 1:100) {
	for (j in 1:(i+1)) {
		s <- sum(s,1/(i*j))
		print(s);
	}
}
s

# Pode-se usar a função outer -> Saber explicar o interesse desta função