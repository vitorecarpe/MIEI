%x formato

%%
[ \t\n]     ;
printf      printf("PRINTF ");
\(          printf("PABRE ");
\)          printf("PFECHA");
,           printf("VIRG ");
[_A-Za-z][_A-Za-z0-9]*  printf("ID ");
\&          printf("OP_END ");
;           printf("PVIRG");
\"          BEGIN formato;

<formato>\"         BEGIN 0;
<formato>\%[dxcsf]  printf("MOD ");
<formato>[^%]+      printf("TEXTO ");

.           printf("ERRO ");
%%