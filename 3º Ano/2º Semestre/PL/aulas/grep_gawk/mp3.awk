# gawk -f mp3.awk arq-son.txt |sort

BEGIN {FS="::"}

$0 ~/7.mp3/ { match($0, /(\w+\.mp3)/, res);
                print $3, res[1]; }