/**
 * "Boxes" where the article informations will be stored.
 *
 * @author Vitor Peixoto
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @version May 12
 */

package engine;

public class ArticleStructure{

    private String title;
    private long id;
    private long revid;
    private String timestamp;
    private String contributor;
    private long contid;
    private long size;
    private long words;

    public ArticleStructure(){
        this.title = "n/a";
        this.id = 0;
        this.revid = 0;
        this.timestamp = "n/a";
        this.contributor = "n/a";
        this.contid = 0;
        this.size = 0;
        this.words = 0;
    }
    public ArticleStructure(ArticleStructure s){
        this.title = s.getTitle();
        this.id = s.getID();
        this.revid = s.getRevID();
        this.timestamp = s.getTimestamp();
        this.contributor = s.getContributor();
        this.contid = s.getContID();
        this.size = s.getSize();
        this.words = s.getWords();
    }
    public ArticleStructure(String title, long id, long revid, String timestamp, String contributor, long contid, long size, long words){
        this.title = title;
        this.id = id;
        this.revid = revid;
        this.timestamp = timestamp;
        this.contributor = contributor;
        this.contid = contid;
        this.size = size;
        this.words = words;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public void setID(long id){
        this.id=id;
    }
    public void setRevID(long revid){
        this.revid=revid;
    }
    public void setTimestamp(String timestamp){
        this.timestamp=timestamp;
    }
    public void setContributor(String contributor){
        this.contributor=contributor;
    }
    public void setContID(long contid){
        this.contid=contid;
    }
    public void setSize(long size){
        this.size=size;
    }
    public void setWords(long words){
        this.words=words;
    }

    public String getTitle() {
        return title;
    }
    public long getID(){
        return id;
    }
    public long getRevID(){
        return revid;
    }
    public String getTimestamp(){
        return timestamp;
    }
    public String getContributor(){
        return contributor;
    }
    public long getContID(){
        return contid;
    }
    public long getSize(){
        return size;
    }
    public long getWords(){
        return words;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Article \n");
        sb.append("Title: ").append(getTitle()).append(" \n");
        sb.append("ID: ").append(getID()).append(" \n");
        sb.append("  Revision ID: ").append(getRevID()).append(" \n");
        sb.append("  Timestamp: ").append(getTimestamp()).append(" \n");
        sb.append("    Contributor Name: ").append(getContributor()).append(" \n");
        sb.append("    Contributor ID: ").append(getContID()).append(" \n");
        sb.append("  Text Size: ").append(getSize()).append(" \n");
        sb.append("  Text Words: ").append(getWords()).append(" \n");
        return sb.toString();
    }
    public boolean equals(ArticleStructure a){
        if((a.getID() == this.id) && (a.getRevID() == this.revid)){
            return true;
        }
        return false;
    }
    public ArticleStructure clone(){
        return new ArticleStructure(this);
    }
}
