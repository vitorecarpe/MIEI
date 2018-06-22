package horarios.data;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {   
    
    private static final String URL = "localhost";
    private static final String DB = "Horarios";
    private static final String USERNAMEADMIN = "admin";
    private static final String PASSWORDADMIN = "admin";
    private static final String USERNAMEALUNO = "aluno";
    private static final String PASSWORDALUNO = "aluno";
    private static final String USERNAMEDOCENTE = "docente";
    private static final String PASSWORDDOCENTE = "docente";
    
    /**
     * Estabelece ligação à base de dados
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Connection connectAdmin() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        //cliente deve fechar conexão!
        return DriverManager.getConnection("jdbc:mysql://"+URL+"/"+DB+"?user="+USERNAMEADMIN+"&password="+PASSWORDADMIN);    
    }
    public static Connection connectAluno() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        //cliente deve fechar conexão!
        return DriverManager.getConnection("jdbc:mysql://"+URL+"/"+DB+"?user="+USERNAMEALUNO+"&password="+PASSWORDALUNO);    
    }
    public static Connection connectDocente() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        //cliente deve fechar conexão!
        return DriverManager.getConnection("jdbc:mysql://"+URL+"/"+DB+"?user="+USERNAMEDOCENTE+"&password="+PASSWORDDOCENTE);    
    }
    
    /**
     * Fecha a ligação à base de dados, se aberta.
     * @param c 
     */
    public static void close(Connection c) {
        try {
            if(c!=null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
