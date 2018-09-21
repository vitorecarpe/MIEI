# gawk -f 3.awk harrypotter1.txt


BEGIN {FS=" "}

$5 ~ /V../ { verbs[$2]++ } #verbos
$5 ~ /A./  { adjectives[$2]++ } #adjetivos
$5 ~ /N./  { substantivs[$2]++ } #substantivos   aka NP&NC
$5 ~ /R./  { adverbs[$2]++ } #adverbios

END {   for(i in verbs)
            print verbs[i], i | "sort -n -r > 3verbs.result";
        for(i in adjectives)   
            print adjectives[i], i | "sort -n -r > 3adjectives.result"; 
        for(i in substantivs)  
            print substantivs[i], i | "sort -n -r > 3substantivs.result";
        for(i in adverbs)      
            print adverbs[i], i | "sort -n -r > 3adverbs.result";
}
