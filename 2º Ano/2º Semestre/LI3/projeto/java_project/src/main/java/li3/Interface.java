package li3; 

import java.util.ArrayList;

public interface Interface {

    void init();

    void load(int nsnaps, ArrayList<String> snaps_paths);

    long all_articles();

    long unique_articles();

    long all_revisions();

    ArrayList<Long> top_10_contributors();

    String contributor_name(long contributor_id);

    ArrayList<Long> top_20_largest_articles();

    String article_title(long article_id);

    ArrayList<Long> top_N_articles_with_more_words(int n);

    ArrayList<String> titles_with_prefix(String prefix);

    String article_timestamp(long article_id, long revision_id);

    void clean();

}
