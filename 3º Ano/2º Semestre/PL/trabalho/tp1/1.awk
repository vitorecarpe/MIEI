# gawk -f 1.awk harrypotter1.txt
# for i in *.txt; gawk -f 1.awk $i; done > 1.result

BEGIN {FS=" "}

$0 ~ /^$/ { conta++ }

END { print FILENAME " : " conta }