BEGIN { FS = "\t" }
NR==1 {for(i=1; i<=NF; i++)
        print i " :: " $i}