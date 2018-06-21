#include "hash.h"
#include "interface.h"
#include "hash.c"


//Função auxiliar de 'unique_articles' e 'all_revisions'. Verifica se um valor está contido num array.
int isvalueinarray(int val, int *arr, int size){
    for (int i=0; i < size; i++)
        if (arr[i] == val) return 1;
    return 0;
}

//Função auxiliar de 'all_revisions'. Verifica se um valor está contido na coluna de uma matriz.
int isvalueinarray_allrev(int val, int arr[10][10], int size){
    for (int i=0; i < size; i++)
        if (arr[i][1] == val) return 1;
    return 0;
}

//Função auxiliar de 'titles_with_prefix'. Verifica se 'prefix' é prefixo de 'title'.
int myStrcmp(char prefix[], char title[]){
    int i, flag=1;
    for(i=0; prefix[i] && title[i] && flag==1; i++){
        if(prefix[i]!=title[i]) flag=0;
    }
    if(prefix[i]!='\0' && title[i]=='\0') flag=0;
    return flag; //retorna 1 se "prefix" é prefixo de "title". Caso contrário retorna 0.
}

//Função auxiliar de 'titles_with_prefix'. Verifica se a variável 'id' existe no array.
int myelem(int s[], int id, int size){
    int i;
    for(i=0;i<size;i++){
        if(id == s[i]) return 1; //retorna 1 se existe.
    }
    return 0; //caso contrário retorna 0.
}

//Função auxiliar de 'top_20_largest_articles' e de 'top_N_articles_with_more_words'. Verifica se a variável 'id' existe no array.
int my68elem(long s[], int id, int size){
    int i;
    for(i=0; i<size; i++){
        if(id == s[i]) return i+1; //retorna i+1 se existe (para sabermos em que posiçao do array encontrou).
    }
    return 0; //caso contrário retorna 0.
}

//Função auxiliar de 'getObject' no ficheiro load.c. Conta o número de palavras do texto do artigo.
long contaPalavras (char* s){
    long count,i;
    int x, flag=0;
    for (count=0, i=0; s[i]!='\0'; i++){
        x=(s[i]==' ' || s[i]=='\n' || s[i]=='\t');
        if (!x && !flag) {count++; flag=1;}
        else if (x) flag=0;
    }
    return count;
}

//Função auxiliar de 'top_10_contributors'
int isContribInT10C(int contrib, T10C t10c){
    while(t10c){
        if( t10c->contributorid == contrib ) return 1;
        else t10c=t10c->next;
    }
    return 0;
}

//Função auxiliar de 'top_10_contributors'
int isRevidInT10C(int revid, T10C t10c){
    for(int i=0;i<t10c->count;i++){
        if( t10c->revid[i] == revid) return 1;
    }
    return 0;
}

//Função auxiliar de 'top_10_contributors'
T10C newCaixa4 (int revid,int contributorid, T10C qs){
    //tenho de dapois dar free disto somewhere, probabelmente clean
    T10C new = (T10C) malloc (sizeof (struct top10contributor));
    //mais outro free pra dar tambem na clean i think
    int* revidArray = malloc (5*sizeof(int));
    revidArray[0] = revid;
    
    if (new!=NULL) {
        new->contributorid = contributorid;
        new->count = 1;
        new->MAX = 5;
        new->revid = revidArray;
        new->next = qs;
    }
    return new;
}


//======================================== INTERROGAÇÕES ========================================

//1. Retorna o número de artigos encontrados nos backups.
long all_articles(TAD_istruct qs){
    long count=0;
    int i;
    object caixa;
    for(i=0;i<SIZE;i++){
        caixa=qs->hashTableID[i];
        while(caixa!=NULL){
            count++;
            caixa=caixa->next;
        }
    }
    return count;
}

//2. Retorna o número de artigos únicos encontrados nos backups. Isto é, os artigos que tem um ID único.
long unique_articles(TAD_istruct qs){
    long count=0;
    int i,size;
    int aux[32];
    object caixa;
    for(i=0;i<SIZE;i++){
        caixa=qs->hashTableID[i];
        size=0;
        while(caixa!=NULL){
            if(!isvalueinarray(caixa->id,aux,size)){
                aux[size]=caixa->id;
                size++;
            }
            caixa=caixa->next;
        }
        count+=size;
    }
    return count;
}

//3. Retorna o número de revisões que foram efeituadas.
long all_revisions(TAD_istruct qs){
    long count=0;
    int i,size,sizeaux,k;
    int aux[10][10];
    object caixa;

    for(i=0;i<SIZE;i++){
        caixa=qs->hashTableID[i];
        size=0;
        while(caixa!=NULL){
            if(!isvalueinarray_allrev(caixa->id,aux,size)){
                aux[size][0]=1;
                aux[size][1]=caixa->id;
                aux[size][2]=caixa->revid;
                size++;
            }
            else{
                for(sizeaux=0;aux[sizeaux][1]!=caixa->id;sizeaux++);
                //printf("linha %d | size %d \n",sizeaux,aux[sizeaux][0]);
                if(!isvalueinarray(caixa->revid,&(aux[sizeaux][2]),aux[sizeaux][0])){
                    aux[sizeaux][(aux[sizeaux][0])+2]=caixa->revid;
                    aux[sizeaux][0]+=1;
                }

            }
            caixa=caixa->next;
        }
        for(k=0;k<size;k++) count+=aux[k][0];
        //printf("count ---------------> %ld \n\n",count);
    }
    return count;
}

//4. Retorna os ID's dos maiores contribuidores.
long* top_10_contributors(TAD_istruct qs){
    T10C t10c,t10cAux;
    contributor caixa;
    long* ids = malloc (10*sizeof(long));
    int count[10]={0};
    int i, k;

    for(i=0;i<SIZE;i++){
        caixa=qs->hashTableRevID[i];
        t10c = NULL;
        t10cAux = NULL;
        while(caixa!=NULL){
            if(caixa->contributorid!=0){
                if(!isContribInT10C(caixa->contributorid,t10c)){
                    t10c = newCaixa4(caixa->revid,caixa->contributorid,t10c);
                }
                else{
                    t10cAux=t10c;
                    while(t10cAux->contributorid != caixa->contributorid) t10cAux=t10cAux->next;
                    if(!isRevidInT10C(caixa->revid,t10cAux)){
                        if(t10cAux->count > (t10cAux->MAX)-2){
                            t10cAux->MAX+=5;
                            t10cAux->revid = realloc(t10cAux->revid, t10cAux->MAX*sizeof(int) );
                        }
                        t10cAux->revid[t10cAux->count] = caixa->revid;
                        t10cAux->count++;
                    }
                }
            }
            caixa=caixa->next;
        }
        //depois de percorrer uma posiçao da hash, verificar os counts de cada contribuidor adicionando ao top10 se assim o merecer.
        for(;t10c;t10c=t10c->next){
            for (k=9; k>=0; k--){
                if( (t10c->count)>count[k] || ((t10c->count)==count[k] && (t10c->contributorid)<ids[k]) ){
                    if(k<9){
                        count[k+1]=count[k];
                        ids[k+1]=ids[k];
                    }
                    count[k]=t10c->count;
                    ids[k]=t10c->contributorid;
                }
            }
        }
    }
    return ids;
}

//5. Fornecendo o ID do contribuidor vai retornar o seu nome.
char* contributor_name(long contributor_id, TAD_istruct qs){
    contributor caixa = qs ->hashTableRevID[hashCode(contributor_id)];
    while(caixa!=NULL){
        if(caixa->contributorid == contributor_id){
            char* contributorname = mystrdup(caixa->contributorname);
            return contributorname;
        }
        caixa = caixa->next;
    }
    return NULL;
}

//6. Retorna os 20 artigos com maior tamanho.
long* top_20_largest_articles(TAD_istruct qs){
    object caixa;
    long tam[20]={0};
    long *id = malloc (20*sizeof(long));
    int i, j;

    for(j=0; j<SIZE; j++){
        caixa=qs->hashTableID[j];
        while(caixa){
            //inserir ordenadamente no array
            if(my68elem(id,caixa->id,20)==0){
                for (i=19; i>=0; i--){
                    if((caixa->tamanho)>tam[i] || ((caixa->tamanho)==tam[i] && (caixa->id)<id[i])){
                        if(i < 19){
                            tam[i+1]=tam[i];
                            id[i+1]=id[i];
                        }
                        tam[i]=caixa->tamanho;
                        id[i]=caixa->id;
                    }
                }
            }
            else{
                int x=my68elem(id,caixa->id,20)-1;
                if(tam[x]<caixa->tamanho){
                    tam[x]=caixa->tamanho;
                    for(i=x-1; i>=0; i--){
                        if((caixa->tamanho)>tam[i] || ((caixa->tamanho)==tam[i] && (caixa->id)<id[i])){
                            if(i < 19){
                                tam[i+1]=tam[i];
                                id[i+1]=id[i];
                            }
                            tam[i]=caixa->tamanho;
                            id[i]=caixa->id;
                        }
                    }
                }
            }
            caixa=caixa->next;
        }
    }
    return id;
}

//7. Fornecendo um ID de um artigo vai retornar o titulo desse artigo.
char* article_title(long article_id, TAD_istruct qs){
    object caixa = qs->hashTableID[hashCode(article_id)];
    while(caixa!=NULL){
        if(caixa->id == article_id){
            char* title = mystrdup(caixa->title);
            return title;
        }
        caixa=caixa->next;
    }
    return NULL;
}

//8. Retorna os N artigos com mais palavras.
long* top_N_articles_with_more_words(int n, TAD_istruct qs){
    object caixa;
    long pal[n];
    long* id = malloc (n*sizeof(long));
    int i, j;

    for(i=0;i<n;i++){
        pal[i]=0;
        id[i]=0;
    }
    for(j=0; j<SIZE; j++){
        caixa=qs->hashTableID[j];
        while(caixa){
            //inserir ordenadamente no array
            if(my68elem(id,caixa->id,n)==0){
                for (i=n-1; i>=0; i--){
                    if((caixa->palavras)>pal[i] || ((caixa->palavras)==pal[i] && (caixa->id)<id[i])){
                        if(i < n){
                            pal[i+1]=pal[i];
                            id[i+1]=id[i];
                        }
                        pal[i]=caixa->palavras;
                        id[i]=caixa->id;
                    }
                }
            }
            else{
                int x=my68elem(id,caixa->id,n)-1;
                if(pal[x]<caixa->palavras){
                    pal[x]=caixa->palavras;
                    for(i=x-1; i>=0; i--){
                        if((caixa->palavras)>pal[i] || ((caixa->palavras)==pal[i] && (caixa->id)<id[i])){
                            if(i < n){
                                pal[i+1]=pal[i];
                                id[i+1]=id[i];
                            }
                            pal[i]=caixa->palavras;
                            id[i]=caixa->id;
                        }
                    }
                }
            }
            caixa=caixa->next;
        }
    }
    return id;
}

//9. Devolve uma lista ordenada alfabeticamente de títulos de artigos cujo título começa com um prefixo dado como argumento.
char** titles_with_prefix(char* prefix, TAD_istruct qs){
    object caixa;
    int sizeids = 2500, sizeart = 4;
    char** art = malloc (sizeart*sizeof(char*));
    int* ids = malloc (sizeids*sizeof(int));
    int i,j;

    //percorre a hash table e vai criar um array com os ID's dos artigos nao repetidos
    for(i=0,j=0;i<SIZE;i++){
        caixa=qs->hashTableID[i];
        while(caixa){
            if(j>sizeids*0.9) {
                sizeids+=2500;
                ids = realloc(ids, sizeids*sizeof(int) );
            }
            if(!myelem(ids,caixa->id,j)) {
                ids[j]=caixa->id;
                j++;
            }
            caixa=caixa->next;
        }
    }
    int tamanho = j;
    //procurar os ID's na tabela hash e ver se os prefix estão bem.
    for(i=0,j=0;i<tamanho;i++){
        caixa=qs->hashTableID[hashCode(ids[i])]; //vai para a posição da hash onde tem esse ID guardado no array
        while(caixa){
            if(j>sizeart-1) {
                sizeart+=10;
                art = realloc(art, sizeart*sizeof(char*) );
            }
            if(caixa->id==ids[i]){
                if(myStrcmp(prefix, caixa->title)){ //compara o prefixo com o título, se for igual vai guarda-lo.
                    art[j]=mystrdup(caixa->title);
                    j++;
                }
                caixa=NULL;
            }
            else caixa=caixa->next;
        }
    }
    art[j]=NULL;

    //Ordena o array alfabeticamente.
    for (i = 0; i < j; i++) {
       for (int k = i+1; k < j; k++)
          if (strcmp(art[i], art[k]) > 0) {
             char* temp = art[i];
             art[i] = art[k];
             art[k] = temp;
          }
    }

    free(ids);
    return art;
}

//10. Para um certa revisão de um artigo retorna o timestamp.
char* article_timestamp(long article_id, long revision_id, TAD_istruct qs){
	object caixa=qs->hashTableID[hashCode(article_id)];
	while(caixa!=NULL){
		if(caixa->id == article_id){
    		if(caixa->revid == revision_id){
                char* revtimestamp= mystrdup(caixa->revtimestamp);
                return revtimestamp;
            }
        }
    caixa=caixa->next;
	}
	return NULL;
}
