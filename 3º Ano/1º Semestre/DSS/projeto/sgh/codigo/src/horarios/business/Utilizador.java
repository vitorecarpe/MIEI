package horarios.business;

/**
 * 
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public abstract class Utilizador {
    private int id;
    private String nome;
    private String password;
    private String email;
    private String estatuto;

    //INSTANCES
    public Utilizador(){
        this.id = 0;
        this.nome = "----";
        this.password = "----";
        this.email = "----";
        this.estatuto = "----";
    }
    public Utilizador(int id, String nome, String password, String email, String estatuto){
        this.id = id;
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.estatuto = estatuto;
    }
    public Utilizador(Utilizador a){
        this.id = a.getID();
        this.nome = a.getNome();
        this.password = a.getPassword();
        this.email = a.getEmail();
        this.estatuto = a.getEstatuto();
    }
    
    // SET
    public void setID(int id){
        this.id = id;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setEstatuto(String e){
        this.estatuto = e;
    }
    
    // GET
    public int getID(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
    public String getEstatuto(){
        return this.estatuto;
    }
    
    
    //CLONE & TOSTRING & EQUALS
    @Override
    public abstract Utilizador clone();
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: "+this.id+"\n");
        sb.append("Nome: "+this.nome+"\n");
        sb.append("Email: "+this.email+"\n");
        sb.append("Password: "+this.password+"\n");
        sb.append("Estatuto: "+this.estatuto+"\n");
        return sb.toString();
    }
    
    public boolean equals(Utilizador u){
        return (u.getID() == this.id);
    }
}