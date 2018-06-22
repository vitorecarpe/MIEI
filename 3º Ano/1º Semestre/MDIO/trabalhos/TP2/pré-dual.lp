
/* Objective function */
min: 100 t0 - 100 s0 + 500 t2 - 500 s2 + 100 t3 - 100 s3 + 400 t4 - 400 s4 + 800 t5 - 800 s5 + 90 t6 - 90 s6 + 100 t8 - 100 s8 + 500 t10 - 500 s10 + 300 t11 - 300 s11;

/* Variable bounds */

tf - ti >= 20;
-tf + ti >= -20;

// ri = ti - si

t2 - s0 >= 4 ;
t3 - s2 >= 7 ;
t0 - ti >= 0 ;
t4 - s0 >= 4 ;
t2 - s4 >= 9 ;
t3 - s5 >= 4 ;
tf - s3 >= 2 ;
t5 - s4 >= 9 ;
tf - s5 >= 4 ;
t6 - ti >= 0 ;
t4 - s6 >= 5 ;
t5 - s8 >= 4 ;
tf - s9 >= 2 ;
t8 - s6 >= 5 ;
t9 - s8 >= 4 ;
t10 - s6 >= 5 ;
t8 - s10 >= 8 ;
t9 - s11 >= 7 ;
t11 - s10 >= 8;

//reduções
-t0 + s0 >= -1 ;
-t2 + s2 >= -4 ;
-t3 + s3 >= -1 ;
-t4 + s4 >= -3 ;
-t5 + s5 >= -1 ;
-t6 + s6 >= -2 ;
-t8 + s8 >= -1 ;
-t9 + s9 >= 0 ;
-t10 + s10 >= -1 ;
-t11 + s11 >= -2 ;

// reduções tem de ser maiores que zero
t0 - s0 >= 0 ;
t2 - s2 >= 0 ;
t3 - s3 >= 0 ;
t4 - s4 >= 0 ;
t5 - s5 >= 0 ;
t6 - s6 >= 0 ;
t8 - s8 >= 0 ;
t9 - s9 >= 0 ;
t10 - s10 >= 0 ;
t11 - s11 >= 0 ;

free s0,s2,s3,s4,s5,s6,s8,s9,s10,s11;

