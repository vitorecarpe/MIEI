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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class RegistoDAO {
    private Connection conn;
    public static final int MYSQL_DUPLICATE_PK = 1062;

    /**
     * Aloca um aluno num turno de uma UC.
     * @param idAluno
     * @param codigoUC
     * @param numeroTurno
     * @throws java.lang.ClassNotFoundException
     */
    public void aloca(int idAluno, int codigoUC, int numeroTurno) throws ClassNotFoundException {
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Registo\n" +
                "VALUES (?, ?, ?, ?, ?)\n", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, idAluno);
            stm.setInt(2, numeroTurno);  
            stm.setInt(3, codigoUC);
            stm.setInt(4, 0);
            stm.setInt(5, 0);
            stm.executeUpdate();
            
            ResultSet rs = stm.getGeneratedKeys();

        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O aluno já está alocado nesse turno!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        } finally {
            Connect.close(conn);
        }
    }

    /**
     * Remove um aluno de um turno.
     * @param idAluno
     * @param numTurno
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void remove(int idAluno, int numTurno) throws SQLException, ClassNotFoundException {
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM Registo\n" +
                "WHERE idAluno=? AND numeroTurno=?", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, idAluno);
            stm.setInt(2, numTurno);
            stm.executeUpdate();
            
            ResultSet rs = stm.getGeneratedKeys();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
    }
   
    /**
     * Obtém o Turno dado o seu ID.
     * @param id
     * @return 
     */
    public Turno getTurno(int id) {
        Turno t = new Turno();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Turno WHERE numeroTurno="+id);
            while (rs.next()) {
                t = new Turno(rs.getInt("numeroTurno"),rs.getString("dia"),rs.getInt("hora"),
                                    getUC(rs.getInt("codigoUC")),rs.getInt("capSala"),getTipo(rs.getInt("idTipo")),
                                    getDocente(rs.getInt("idDocente")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return t;
    }
    
    /**
     * Lista de turnos onde o aluno está inscrito.
     * @param idAluno
     * @return 
     */
    public Collection<Registo> getTurnos(int idAluno) {
        Collection<Registo> col = new HashSet<Registo>();
        Registo r = new Registo();
        try {
            conn = Connect.connectAdmin();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Registo WHERE idAluno="+idAluno);
            while (rs.next()) {
                r = new Registo(getAluno(rs.getInt("idAluno")),getUC(rs.getInt("codigoUC")),getTurno(rs.getInt("numeroTurno")),
                                        rs.getInt("faltas"),rs.getInt("aulas"));
                col.add(r);
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
     * Diz em quantos turnos o aluno está alocado numa determinada UC.
     * @param idAluno
     * @param codigoUC
     * @return 
     */
    public Collection<Integer> turnoUCAluno(int idAluno, int codigoUC) {
        int i=0;
        Collection<Integer> col = new HashSet<Integer>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT numeroTurno FROM Registo WHERE idAluno="+idAluno+" AND codigoUC="+codigoUC);
            while (rs.next()) {
                i = rs.getInt("numeroTurno");
                col.add(i);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return col;
    }

    /**
     * Obtém o número de aulas lecionadas de um determinado turno.
     * @param numTurno
     * @return 
     */
    public int getNumAulas (int numTurno){
        int i=0;
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT aulas FROM Registo WHERE numeroTurno="+numTurno);
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
    
    /**
     * Obtém o número de faltas de um aluno num turno.
     * @param numTurno
     * @param idAluno
     * @return 
     */
    public int getNumFaltas (int numTurno, int idAluno){
        int i=0;
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT faltas FROM Registo WHERE numeroTurno="+numTurno+" AND idAluno="+idAluno);
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
    
    /**
     * Obtém o número de turnos de um aluno.
     * @param idAluno
     * @return 
     */
    public int getNumTurnos (int idAluno){
        int i=0;
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM Registo WHERE idAluno="+idAluno);
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
    
    /**
     * Função que marca faltas do aluno.
     * @param codUC
     * @param numTurno
     * @param idAluno
     * @param falta
     * @return 
     */
    public void marcarFalta(int codUC, int numTurno, int idAluno, int falta){
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("UPDATE Registo SET aulas=aulas+1, faltas=faltas+(?)"
                                                        + "WHERE numeroTurno=(?) AND idAluno=(?)"
                                                        + "AND codigoUC=(?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, falta);
            stm.setInt(2, numTurno);
            stm.setInt(3, idAluno);
            stm.setInt(4, codUC);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
        } catch (ClassNotFoundException | SQLException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Lista de alunos inscritos num turno.
     * @param idTurno
     * @return 
     */
    public Collection<Integer> alunosInscritosTurno(int idTurno){
        Collection<Integer> col = new HashSet<Integer>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idAluno FROM Registo WHERE numeroTurno="+idTurno);
            while (rs.next()) {
                int aluno = rs.getInt("idAluno");
                col.add(aluno);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return col;
    }
    
    /**
     * Lista de alunos inscritos numa UC
     * @param codUC
     * @return 
     */
    public Collection<Integer> alunosInscritosUC(int codUC){
        Collection<Integer> col = new HashSet<Integer>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idAluno FROM Registo WHERE codigoUC="+codUC);
            while (rs.next()) {
                int aluno = rs.getInt("idAluno");
                col.add(aluno);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return col;
    }
    
    /**
     * Lista de UCs em que o aluno está inscrito
     * @param idAluno
     * @return 
     */
    public Collection<Integer> listarUCAluno(int idAluno){
        Collection<Integer> col = new HashSet<Integer>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT codigoUC FROM Registo WHERE idAluno="+idAluno);
            while (rs.next()) {
                int codUC = rs.getInt("codigoUC");
                col.add(codUC);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return col;
    }
    
    /**
     * Turno da UC em que o aluno está inscrito.
     * @param idAluno
     * @param codUC
     * @return 
     */
    public int getTurno(int idAluno, int codUC){
        int numTurno = 0;
        Collection<Integer> col = new HashSet<Integer>();
        try {
            conn = Connect.connectAdmin();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT numeroTurno FROM Registo WHERE idAluno="+idAluno+" AND codigoUC="+codUC);
            while (rs.next()) {
                int n = rs.getInt("numeroTurno");
                col.add(n);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        for(Integer i : col){
            if(getTurno(i).getTipoAula().getID()!=1) return i;
        }
        return 0;
    }
    
    /**
     * Nº de alunos inscritos num turno
     * @param idTurno
     * @return 
     */
    public int numeroAlunosInscritosTurno(int idTurno){
        int i=0;
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM Registo WHERE numeroTurno="+idTurno);
            if (rs.next()) {
                i = rs.getInt(1);
            }
            
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return i;
    }
    
    /**
     * Obter um aluno, dado o seu número
     * @param id
     * @return 
     */
    private Aluno getAluno(int id) {
        Aluno a = null;
        try {
            conn = Connect.connectAdmin();
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
     * Obter o docente, dado o seu ID
     * @param id
     * @return Docente d
     */
    private Docente getDocente(int id) {
        Docente d = null;
        try {
            conn = Connect.connectAdmin();
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
     * @return 
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
    
    /**
     * Obter a UC, dado o seu código.
     * @param cod
     * @param cad
     * @return UC cad
     */
    private UC getUC(int cod) {
        UC cad = null;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM UC WHERE codigoUC=?");
            stm.setInt(1, cod);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {       
                cad = new UC(rs.getInt("codigoUC"),rs.getString("nome"),rs.getInt("ano"),rs.getInt("semestre"),
                              getDocente(rs.getInt("idDocente")),rs.getString("abreviatura"),getCurso(rs.getInt("idCurso")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return cad;
    }
    
    /**
     * Obter o tipo de aula, dado o seu ID.
     * @param id
     * @return TipoAula t
     */
    private TipoAula getTipo(int id) {
        TipoAula t = null;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM TipoAula WHERE idTipo=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {       
                t = new TipoAula(rs.getInt("idTipo"),rs.getString("tipo"),rs.getInt("limite"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return t;
    }
}