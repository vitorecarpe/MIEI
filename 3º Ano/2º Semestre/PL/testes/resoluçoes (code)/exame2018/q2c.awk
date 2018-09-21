# gawk -f q2c.awk q2cFile1.txt q2cFile2.txt

BEGIN { FS=":" }

$0 ~ /.*/ { if(ARGIND==1) aux[$1][$2];
            else
                for(i in aux)
                    if($1==i) 
                        for(j in aux[i]) 
                            print $1":"j":"$2;
          }
