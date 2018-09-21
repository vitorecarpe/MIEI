# gawk -f 2.awk harrypotter1.txt
# gawk -f 2.awk harrypotter1.txt | sort -n -r > 2.result

BEGIN {FS=" "}

$5 ~ /NP/ { names[$2]++ }

END { for(i in names) print names[i], i | "sort -n -r" }
#END { for(i in names) print names[i], i | "sort -n -r > 2.result" }