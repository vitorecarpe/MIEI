#! /bin/bash

# Este script serve para testar o ficheiro boundedrun.c
# Para comecar, fazer chmod a+x boundedrun.sh numa janela do terminal
# Depois esta pronto a correr
# Podes testar com varios valores, alterando as variaveis aqui presentes

# Para testar criamos um ficheiro com muitos 'a' (default = 10Mb) e mandamos imprimir com o cat
# O primeiro caso serve para testar o limite de caracteres
# Damos um nr reduzido (default = 10) e limitamos com muito tempo (default = 30s)
# O segundo caso serve para testar o limite de segundos
# Damos um nr elevado de carateres a imprimir (default = 10240) e limitamos a um nr reduzido de segundos (default = 2)

file=a.txt                      # Nome do ficheiro a ser gerado
compile=`gcc -Wall -Wextra -Wno-unused-parameter -o boundedrun boundedrun.c`

# Imprimir um nr de a (primeiro argumento) para um ficheiro (segundo argumento)
function the_fonz {
    for i in $(seq 1 $1)
    do
    	echo -n "a" >> $2
    done
}

rm $file                        # Garantir que nao estamos a fazer append a um ficheiro existente
$compile                        # Compilar o .c

big_size=10240                  # Nr de 'a' a serem impressos no ficheiro

the_fonz $big_size $file        # Encher o ficheiro de 'a'

num_a=10                        # Nr de 'a' apos os quais o programa deve interromper
printf "DEVERA INTERROMPER APOS IMPRIMIR %d a\n" $num_a
./boundedrun 30 $num_a cat $file

seconds=2                       # Nr de segundos apos os quais o programa deve interromper
printf "\n\nDEVERA INTERROMPER AO FIM DE %ds\n" $seconds
./boundedrun $seconds $big_size cat $file
