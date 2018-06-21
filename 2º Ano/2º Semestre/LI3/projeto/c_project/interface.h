#include <stdio.h>
#include <stdlib.h>


typedef struct TCD_istruct * TAD_istruct;

TAD_istruct init();

TAD_istruct load(TAD_istruct qs, int nsnaps, char* snaps_paths[]);

long all_articles(TAD_istruct qs);

long unique_articles(TAD_istruct qs);

long all_revisions(TAD_istruct qs);

long* top_10_contributors(TAD_istruct qs);

char* contributor_name(long contributor_id, TAD_istruct qs);

long* top_20_largest_articles(TAD_istruct qs);

char* article_title(long article_id, TAD_istruct qs);

long* top_N_articles_with_more_words(int n, TAD_istruct qs);

char** titles_with_prefix(char* prefix, TAD_istruct qs);

char* article_timestamp(long article_id, long revision_id, TAD_istruct qs);

TAD_istruct clean(TAD_istruct qs);
