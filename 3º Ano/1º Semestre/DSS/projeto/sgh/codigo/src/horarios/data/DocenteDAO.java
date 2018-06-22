package horarios.data;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
import horarios.business.Docente;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;

public class DocenteDAO {
    private Connection conn;
    
    /**
     * Obter o docente, dado o seu ID
     * @param id
     * @return Docente d
     */
    public Docente getDocente(int id) {
        Docente d = null;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM docente WHERE idDocente=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                d = new Docente(rs.getInt("idDocente"),rs.getString("nome"),rs.getString("password"),rs.getString("email"),
                              rs.getString("estatuto"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return d;
    }
    
    /**
     * Obter a fase atual.
     * @return fase
     */
    public int getFase(){
        int fase = 0;
        try {
            conn = Connect.connectAdmin();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM InfoExtra");
            while (rs.next()) {
                fase = rs.getInt("fase");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Connect.close(conn);
        }
        return fase;
    }
    
    /**
     * Alterar fase.
     * @param f
     */
    public void setFase(int f){
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("UPDATE InfoExtra SET fase=(?) WHERE id=1", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, f);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Obtém todos os docentes da base de dados
     * @return
     */
    public Collection<Docente> values() {
        Collection<Docente> col = new HashSet<Docente>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM docente");
            while (rs.next()) {
                Docente a = new Docente(rs.getInt("idDocente"),rs.getString("nome"),rs.getString("password"),rs.getString("email"),
                                    rs.getString("estatuto"));
                col.add(a);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Connect.close(conn);
        }
        return col;
    }
    
}