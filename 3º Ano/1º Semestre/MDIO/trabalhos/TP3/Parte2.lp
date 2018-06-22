/* Objective function */
min: 200 r0 + 600 r21 + 400 r22 + 220 r3 + 600 r41
+ 400 r42 + 1600 r5 +  120 r61 + 90 r62
+ 210 r8 + 900 r10 + 400 r111 + 200 r112;

/* Variable bounds */
tf = 19;

arco_02: t2 >= t0 - r0 + 4 ;
arco_23: t3 >= t2 - r21 - r22 + 7 ;
arco_i0: t0 >= ti + 0 ;
arco_04: t4 >= t0 - r01 - r02 + 4 ;
arco_42: t2 >= t4 - r41 - r42 + 9 ;
arco_53: t3 >= t5 - r5 + 4 ;
arco_3f: tf >= t3 - r3 + 2 ;
arco_45: t5 >= t4 - r41 - r42 + 9 ;
arco_5f: tf >= t5 - r5 + 4 ;
arco_i6: t6 >= ti + 0 ;
arco_64: t4 >= t6 - r61 - r62 + 6 ;
arco_85: t5 >= t8 - r8 + 4 ;
arco_9f: tf >= t9 + 2 ;
arco_68: t8 >= t6 - r61 - r62 + 6 ;
arco_89: t9 >= t8 - r8 + 4 ;
arco_610: t10 >= t6 - r61 - r62 + 5 ;
arco_108: t8 >= t10 - r10 + 8 ;
arco_119: t9 >= t11 - r111 - r112 + 7 ;
arco_1011: t11 >= t10 - r10 + 8;

//atividade 6 e 11
r61 >= r62;
r111 >= r112;

//atividade 2
r21 >= 3 y2;
r22 <= 1000000 y2;

//Atividade 4
r41 >= y4;
r42 <= 1000000 y4;

// reduções máximas permitidas
r0 <= 1;
r21 <= 3;
r22 <= 1;
r3 <= 1;
r41 <= 1;
r42 <= 2;
r5 <= 1;
r61 <= 1;
r62 <= 1;
r8 <= 1;
r10 <= 1;
r111 <= 1;
r112 <= 1;

int r0 r21 r22 r3 r41 r42 r5 r61 r62 r8 r10 r111 r112;
bin y2 y4;












