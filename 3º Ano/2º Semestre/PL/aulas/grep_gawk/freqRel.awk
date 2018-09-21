# gawk -f freqRel.awk processos.txt

BEGIN {FS="::"}

$6 !~ /^$/ { while( match($6, /,(\w+( \w+)*)\./ ,rel) )
            freq[rel[1]]++; }

END {for(i in freq) print freq[i], i}