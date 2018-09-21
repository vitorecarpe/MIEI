# gawk -f 4.awk harrypotter1.txt


BEGIN { FS=" " }

NF>0 {  #results[$5][$3][$2]++; 
        # tolower permite uniformizar os resultados
        results[$5][tolower($3)][tolower($2)]++;
}

END {   for(i in results) 
            for(j in results[i]) 
                for(k in results[i][j]){
                    # printa "Lema, Palavra, POS, Repeticoes"
                    print j, k, i, results[i][j][k] | "sort";
                    # printa "Palavra, Lema, POS, Repeticoes"
                    #print k, j, i, results[i][j][k] | "sort";
                    # printa "Repeticoes, POS, Lema, Palavra" (ordenado numericamente)
                    #print results[i][j][k], i, j, k | "sort -n -r";
                }
}
