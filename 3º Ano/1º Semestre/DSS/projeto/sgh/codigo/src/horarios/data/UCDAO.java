package horarios.data;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
import horarios.business.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class UCDAO{
    private Connection conn;
    public static final int MYSQL_DUPLICATE_PK = 1062;

    /**
     * Insere uma UC na base de dados.
     * @param uc
     * @throws java.lang.ClassNotFoundException 
     */
    public void put(UC uc) throws ClassNotFoundException {
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO UC\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)\n", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, uc.getUC());
            stm.setString(2, uc.getNome());  
            stm.setInt(3, uc.getAno());
            stm.setInt(4, uc.getSemestre());
            stm.setInt(5, uc.getDocente().getID());
            stm.setString(6, uc.getAbrev()); 
            stm.setInt(7, uc.getCurso().getID()); 
            stm.executeUpdate();
            
            ResultSet rs = stm.getGeneratedKeys();
        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
                JOptionPane.showMessageDialog(null, "Já existe uma Unidade Curricular com esse ID!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        } finally {
            Connect.close(conn);
        }
    }
   
    /**
     * Obtém todas as UC da base de dados
     * @return 
     */
    public Collection<UC> values() {
        Collection<UC> col = new HashSet<UC>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM UC");
            while (rs.next()) {
                UC a = new UC(rs.getInt("codigoUC"),rs.getString("nome"),rs.getInt("ano"),rs.getInt("semestre"),
                              getDocente(rs.getInt("idDocente")),rs.getString("abreviatura"),getCurso(rs.getInt("idCurso")));
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
    
    /**
     * Obter o docente, dado o seu ID
     * @param id
     * @return Docente d
     */
    private Docente getDocente(int id) {
        Docente d = null;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Docente WHERE idDocente=?");
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
     * Obter o curso, dado o seu ID
     * @param id
     * @return Curso c
     */
    private Curso getCurso(int id) {
        Curso c = null;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Curso WHERE idCurso=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                c = new Curso(rs.getInt("idCurso"),rs.getString("nome"),getDocente(rs.getInt("idDirCurso")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return c;
    }

}