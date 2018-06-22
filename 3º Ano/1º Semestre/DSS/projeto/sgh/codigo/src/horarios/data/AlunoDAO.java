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


public class AlunoDAO{
    private Connection conn;
    public static final int MYSQL_DUPLICATE_PK = 1062;

    /**
     * Obter um aluno, dado o seu número
     * @param id
     * @return 
     */
    public Aluno getAluno(int id) {
        Aluno a = null;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM aluno WHERE idAluno=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                a = new Aluno(rs.getInt("idAluno"),rs.getString("nome"),rs.getString("password"),rs.getString("email"),
                              rs.getString("estatuto"),rs.getInt("ano"),rs.getInt("semestre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return a;
    }
    
    /**
     * Insere um aluno na base de dados.
     * @param aluno
     * @throws java.lang.ClassNotFoundException 
     */
    public void put(Aluno aluno) throws ClassNotFoundException {
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO aluno\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)\n", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, aluno.getID());
            stm.setString(2, aluno.getEstatuto());  
            stm.setString(3, aluno.getNome());
            stm.setString(4, aluno.getPassword());
            stm.setString(5, aluno.getEmail());
            stm.setInt(6, aluno.getAno()); 
            stm.setInt(7, aluno.getSemestre()); 
            stm.executeUpdate();
            
            ResultSet rs = stm.getGeneratedKeys();
            
        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
                JOptionPane.showMessageDialog(null, "Já existe uma aluno com esse ID!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Obtém todos os alunos da base de dados
     * @return 
     */
    public Collection<Aluno> values() {
        Collection<Aluno> col = new HashSet<Aluno>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM aluno");
            while (rs.next()) {
                Aluno a = new Aluno(rs.getInt("idAluno"),rs.getString("nome"),rs.getString("password"),rs.getString("email"),
                                    rs.getString("estatuto"),rs.getInt("ano"),rs.getInt("semestre"));
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
     * Trocas recebidas por um determinado aluno.
     * @param idAluno
     * @return 
     */
    private Collection<Troca> trocasRecebidas(int idAluno) {
        Collection<Troca> col = new HashSet<Troca>();
        try {
            conn = Connect.connectAluno();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Troca WHERE idAlunoRequerido="+idAluno);
            while (rs.next()) {
                Troca t = new Troca(rs.getInt("idTroca"),rs.getInt("idAlunoRequerente"),
                                    rs.getInt("idAlunoRequerido"),rs.getInt("codigoUC"),
                                    rs.getInt("numeroTurnoRequerente"),rs.getInt("numeroTurnoRequerido"));
                col.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return col;
    }
    
}