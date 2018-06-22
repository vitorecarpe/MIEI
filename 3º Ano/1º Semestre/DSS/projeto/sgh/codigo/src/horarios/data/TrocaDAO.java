package horarios.data;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
import horarios.business.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

public class TrocaDAO {
    private Connection conn;
    public static final int MYSQL_DUPLICATE_PK = 1062;
   
    /**
     * Obtém uma Troca dado o seu ID.
     * @param idTroca
     * @return 
     */
    public Troca getTroca(int idTroca) {
        Troca t = new Troca();
        try {
            conn = Connect.connectAluno();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Troca WHERE idTroca="+idTroca);
            while (rs.next()) {
                t = new Troca(rs.getInt("idTroca"),rs.getInt("idAlunoRequerente"),rs.getInt("idAlunoRequerido"),
                              rs.getInt("codigoUC"),rs.getInt("numeroTurnoRequerente"),rs.getInt("numeroTurnoRequerido"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return t;
    }
    
    /**
     * Adiciona uma troca.
     * @param idAlunoRequerente
     * @param idAlunoRequerido
     * @param codigoUC
     * @param numeroTurnoRequerente
     * @param numeroTurnoRequerido
     * @return 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public int adicionarTroca(int idAlunoRequerente, int idAlunoRequerido, int codigoUC, int numeroTurnoRequerente, int numeroTurnoRequerido) throws ClassNotFoundException, SQLException{
        try {
            conn = Connect.connectAluno();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Troca\n" +
                "VALUES (?, ?, ?, ?, ?, ?)\n", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, getMaiorID()+1);
            stm.setInt(2, idAlunoRequerente);  
            stm.setInt(3, idAlunoRequerido);
            stm.setInt(4, codigoUC);
            stm.setInt(5, numeroTurnoRequerente);
            stm.setInt(6, numeroTurnoRequerido);
            stm.executeUpdate();
            
            ResultSet rs = stm.getGeneratedKeys();

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return getMaiorID()+1;
    }
    
    /**
     * Remove uma troca.
     * @param idTroca
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void removerTroca(int idTroca) throws SQLException, ClassNotFoundException{
        try {
            conn = Connect.connectAluno();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM Troca\n" +
                "WHERE idTroca=?", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, idTroca);
            stm.executeUpdate();
            
            ResultSet rs = stm.getGeneratedKeys();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Lista as trocas propostas a um aluno.
     * @param idAluno
     * @return 
     */
    public Collection<Troca> verTrocas(int idAluno){
        Collection<Troca> col = new HashSet<Troca>();
        Troca t = new Troca();
        try {
            conn = Connect.connectAluno();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Troca WHERE idAlunoRequerido="+idAluno);
            while (rs.next()) {
                t = new Troca(rs.getInt("idTroca"),rs.getInt("idAlunoRequerente"),rs.getInt("idAlunoRequerido"),
                                        rs.getInt("codigoUC"),rs.getInt("numeroTurnoRequerente"),rs.getInt("numeroTurnoRequerido"));
                col.add(t);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connect.close(conn);
        }
        return col;
    }
    
    /**
     * Obtém a troca com maior ID.
     * @return 
     */
    private int getMaiorID(){
        int i=0;
        try {
            conn = Connect.connectAdmin();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idTroca FROM Troca ORDER BY idTroca DESC LIMIT 1");

            if (rs.next()) {
                i = rs.getInt(1);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return i;
    }
    
}