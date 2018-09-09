BEGIN { FS=" " }

#ponto 1
$2 ~ /.*/ { conceitos[$2] }
$3 ~ /-/  { split($6,aux,",");
            for(i in aux) conceitos[aux[i]];
          }
$3 ~ /-/  { relacoes[$4] }

#ponto 2
$3 ~ /:/  { propriedades[$2][$4] }

#ponto 3
$0 ~ /.*/ { split($6,aux,",");
            for(i in aux) linha[$2][$4][aux[i]];
            # for(i in aux) print "<triple>"$2"; "$4"; "aux[i]"</triple>\n";
          }

END {   #ponto 1
        for(i in conceitos) {print i; nConceitos++;}
        print nConceitos;
        for(i in relacoes) {print i;nRelacoes++;}
        print nRelacoes;
        #ponto 2
        for(i in propriedades)
            for(j in propriedades[i]) print i, j;
        #ponto 3
        for(i in linha)
            for(j in linha[i])
                for(k in linha[i][j])
                printf("<triple>%s; %s; %s</triple>\n",i,j,k)
}