/**
 * "Boxes" where the article informations will be stored.
 *
 * @author Vitor Peixoto
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @version May 17
 */

package engine;

public class ContributorStructure{

    private long id;
    private long revid;
    private String contributor;
    private long contid;

    public ContributorStructure(){
        this.id = 0;
        this.revid = 0;
        this.contributor = "n/a";
        this.contid = 0;
    }
    public ContributorStructure(ContributorStructure s){
        this.id = s.getID();
        this.revid = s.getRevID();
        this.contributor = s.getContributor();
        this.contid = s.getContID();
    }
    public ContributorStructure(long id, long revid, String contributor, long contid){
        this.id = id;
        this.revid = revid;
        this.contributor = contributor;
        this.contid = contid;
    }

    public void setID(long id){
        this.id=id;
    }
    public void setRevID(long revid){
        this.revid=revid;
    }
    public void setContributor(String contributor){
        this.contributor=contributor;
    }
    public void setContID(long contid){
        this.contid=contid;
    }

    public long getID(){
        return id;
    }
    public long getRevID(){
        return revid;
    }
    public String getContributor(){
        return contributor;
    }
    public long getContID(){
        return contid;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Contributor Name: ").append(getContributor()).append(" \n");
        sb.append("Contributor ID: ").append(getContID()).append(" \n");
        sb.append("ID: ").append(getID()).append(" \n");
        sb.append("Revision ID: ").append(getRevID()).append(" \n\n");

        return sb.toString();
    }
    public boolean equals(ContributorStructure a){
        if((a.getContID() == this.contid) && (a.getRevID() == this.revid)){
            return true;
        }
        return false;
    }
    public ContributorStructure clone(){
        return new ContributorStructure(this);
    }
}
