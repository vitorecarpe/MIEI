# awk -f freqAno.awk processos.txt |sort -n -r

BEGIN {FS="::"}

NF>0 { anos[substr($2,1,4)]++ }

END { for(i in anos) print anos[i], i }