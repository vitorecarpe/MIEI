#include "interface.h"


int main(int argc, char **argv) {

    //Construir a estrutura
    TAD_istruct qs;
    qs=init();
    qs=load(qs,argc-1,argv+1);


    //Buscar os resultados das questões
    int n=30;
    char** auxPrefix = titles_with_prefix("Anax",qs);
    long* auxTop20 = top_20_largest_articles(qs);
    long* auxTopN = top_N_articles_with_more_words(n,qs);
    long* auxTop10 = top_10_contributors(qs);
    char* cn1 = contributor_name(28903366,qs);
    char* cn2 = contributor_name(194203,qs);
    char* cn3 = contributor_name(1000,qs);
    char* at1 = article_title(15910, qs);
    char* at2 = article_title(25507, qs);
    char* at3 = article_title(1111, qs);
    char* t1 = article_timestamp(12,763082287,qs);
    char* t2 = article_timestamp(12,755779730,qs);
    char* t3 = article_timestamp(12,4479730,qs);

    //Imprimir os resultados das questões
    printf("1. All articles: %ld\n",all_articles(qs));                  //retorna 59593
    printf("2. Unique articles: %ld\n",unique_articles(qs));            //retorna 19867
    printf("3. All revisions: %ld\n",all_revisions(qs));                //retorna 40131
    printf("4. Top 10 contributors: \n");                               //retorna 28903366, 13286072, 27823944, 27015025, 194203,  212624,  7852030, 7328338, 7611264, 14508071
    for(int i=0; i<10; i++) printf("   %ld\n", auxTop10[i]);
    printf("5. Contributor name: %s\n",cn1);                            //retorna "Bender the Bot"
    printf("5. Contributor name: %s\n",cn2);                            //retorna "Graham87"
    printf("5. Contributor name: %s\n",cn3);                            //retorna (null)
    printf("6. Top 20 largest articles: \n");                           //retorna 15910, 23235, 11812, 28678, 14604, 23440, 26847, 25507, 26909, 18166, 4402, 14889, 23805, 25391, 7023, 13224, 12108, 13913, 23041, 18048
    for(int i=0; i<20; i++) printf("   %ld\n", auxTop20[i]);
    printf("7. Article title: %s\n",at1);                               //retorna "List of compositions by Johann Sebastian Bach"
    printf("7. Article title: %s\n",at2);                               //retorna "Roman Empire"
    printf("7. Article title: %s\n",at3);                               //retorna "Politics of American Samoa"
    printf("8. Top N articles with more words: \n");                    //retorna 15910, 25507, 23235, 11812, 13224, 26847, 14889, 7023, 14604, 13289, 18166, 4402, 12157, 13854, 23805, 25401, 10186, 23041, 18048, 16772, 22936, 28678, 27069, 9516, 12108, 13913, 13890, 21217, 23440, 25391
    for(int i=0; i<n; i++) printf("   %ld\n", auxTopN[i] );
    printf("9. Titles with prefix \"Anax\":\n");                        //retorna Anaxagoras, Anaxarchus, Anaximander, Anaximenes of Lampsacus, Anaximenes of Miletus
    for(int i=0 ; auxPrefix[i]; i++) printf("   %s\n", auxPrefix[i]);
    printf("10. Article timestamp: %s\n",t1);                           //retorna "2017-02-01T06:11:56Z"
    printf("10. Article timestamp: %s\n",t2);                           //retorna "2016-12-20T04:02:33Z"
    printf("10. Article timestamp: %s\n",t3);                           //retorna (null)

    //Libertar memória
    qs=clean(qs);
    for (int s = 0; auxPrefix[s]; s++) free(auxPrefix[s]);
    free(cn1);
    free(cn2);
    free(cn3);
    free(at1);
    free(at2);
    free(at3);
    free(t1);
    free(t2);
    free(t3);
    free(auxPrefix);
    free(auxTop20);
    free(auxTopN);
    free(auxTop10);

    return (1);
}
