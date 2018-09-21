#gawk -f freqGeo.awk arq-son-txt |sort -n -r

BEGIN {FS"::"}

        {freq[$1]++}

END { for(i in freq) print freq[i], i }