# 

BEGIN {FS="::"}

$3 ~ /Jesus/ { print $3; conta++ }

END { print conta }