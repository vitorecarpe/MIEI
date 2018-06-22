/* Objective function */
min: tf ;

/* Variable bounds */

/* restrições */
arco_02: t2 >= t0 + 4 ;
arco_23: t3 >= t2 + 7 ;
arco_i0: t0 >= ti + 0 ;
arco_04: t4 >= t0 + 4 ;
arco_42: t2 >= t4 + 9 ;
arco_53: t3 >= t5 + 4 ;
arco_3f: tf >= t3 + 2 ;
arco_45: t5 >= t4 + 9 ;
arco_5f: tf >= t5 + 4 ;
arco_i6: t6 >= ti + 0 ;
arco_64: t4 >= t7 + 5 ;
arco_85: t5 >= t8 + 4 ;
arco_9f: tf >= t9 + 2 ;
arco_68: t8 >= t7 + 5 ;
arco_89: t9 >= t8 + 4 ;
arco_610: t10 >= t6 + 5 ;
arco_108: t8 >= t10 + 8 ;
arco_119: t9 >= t11 + 7 ;
arco_1011: t11 >= t10 + 8;
