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
import java.util.Collection;
import java.util.HashSet;

public class CursoDAO {
    private Connection conn;
    
    /**
     * Obter o docente, dado o seu ID
     * @param id
     * @return
     */
    private Docente getDocente(int id) {
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
     * Obtém todos os cursos da base de dados
     * @return 
     */
    public Collection<Curso> values() {
        Collection<Curso> col = new HashSet<Curso>();
        try {
            conn = Connect.connectAdmin();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM curso");
            while (rs.next()) {
                Curso a = new Curso(rs.getInt("idCurso"),rs.getString("nome"),getDocente(rs.getInt("idDirCurso")));
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