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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



public class TurnoDAO {
    private Connection conn;
    public static final int MYSQL_DUPLICATE_PK = 1062;

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
     * Adiciona um novo turno.
     * @param dia
     * @param hora
     * @param codigoUC
     * @param capSala
     * @param idTipo
     * @param idDocente
     * @throws java.lang.ClassNotFoundException
     */
    public void adicionarTurno(String dia, int hora, int codigoUC, int capSala, int idTipo, int idDocente) throws ClassNotFoundException{
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Turno VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, getMaiorID()+1);
            stm.setString(2, dia);
            stm.setInt(3, hora);
            stm.setInt(4, codigoUC);
            stm.setInt(5, capSala);
            stm.setInt(6, idTipo);
            stm.setInt(7, idDocente);
            stm.executeUpdate();
            
            ResultSet rs = stm.getGeneratedKeys();
        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
                JOptionPane.showMessageDialog(null, "Turno com esse ID já existe!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        } finally {
            Connect.close(conn);
        }    
    }
    
    /**
     * Altera informações do turno.
     * @param id
     * @param dia
     * @param hora
     * @param codigoUC
     * @param capSala
     * @param idTipo
     * @param idDocente
     * @throws java.lang.ClassNotFoundException
     */
    public void editarTurno(int id, String dia, int hora, int codigoUC, int capSala, int idTipo, int idDocente) throws ClassNotFoundException{
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("UPDATE Turno SET dia=(?),"
                                                        + "hora=(?),codigoUC=(?),capSala=(?),idTipo=(?),"
                                                        + "idDocente=(?) WHERE numeroTurno=(?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, dia);
            stm.setInt(2, hora);
            stm.setInt(3, codigoUC);
            stm.setInt(4, capSala);
            stm.setInt(5, idTipo);
            stm.setInt(6, idDocente);
            stm.setInt(7, id);
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
     * Remove o turno dado o seu numero e os registos relativos a esse turno.
     * @param num
     */
    public void apagarTurno(int num) {
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm1 = conn.prepareStatement(("DELETE FROM Registo WHERE numeroTurno="+num), Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stm2 = conn.prepareStatement(("DELETE FROM Turno WHERE numeroTurno="+num), Statement.RETURN_GENERATED_KEYS);
            stm1.executeUpdate();
            stm2.executeUpdate();
            ResultSet rs1 = stm1.getGeneratedKeys();
            ResultSet rs2 = stm2.getGeneratedKeys();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Altera capacidade do turno.
     * @param numTurno
     * @param cap
     * @param capSala
     * @throws java.lang.ClassNotFoundException
     */
    public void setCap(int numTurno, int cap){
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("UPDATE Turno SET capSala=(?) WHERE numeroTurno=(?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, cap);
            stm.setInt(2, numTurno);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Obter a capacidade de um turno.
     * @param numTurno
     * @return numTurno
     */
    public int getCap(int numTurno) {
        int capSala = 0;
        int idTipo = 0;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Turno WHERE numeroTurno=(?)");
            stm.setInt(1, numTurno);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {       
                capSala = rs.getInt("capSala");
                idTipo = rs.getInt("idTipo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        int limSala = getTipo(idTipo).getLimite();
        if(limSala>capSala) return limSala;
        else return capSala;
    }

    /**
     * Verificar se ha teoricas num dado dia e hora.
     * @param hora
     * @param dia
     * @return i
     */
    public int getCountTeoricas(int hora, String dia) {
        int i=0;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT COUNT(*) FROM Turno WHERE hora=(?) AND dia=(?) AND idTipo=1");
            stm.setInt(1, hora);
            stm.setString(2, dia);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                i=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return i;
    }
    
    /**
     * Devolve as teóricas dado o dia e hora.
     * @param hora
     * @param dia
     * @return 
     */
    public Turno getTeoricas(int hora, String dia) {
        Turno t = new Turno();
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Turno WHERE hora=(?) AND dia=(?) AND idTipo=1");
            stm.setInt(1, hora);
            stm.setString(2, dia);
            ResultSet rs = stm.executeQuery();
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
     * Obter a UC de um turno.
     * @param numTurno
     * @return codUC
     */
    public int getUCTurno(int numTurno) {
        int codUC = 0;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Turno WHERE numeroTurno=(?)");
            stm.setInt(1, numTurno);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {       
                codUC = rs.getInt("codigoUC");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return codUC;
    }
  
    /**
     * Diz a disponibilidade de um docente para um determinado dia e hora.
     * @param idDocente
     * @param dia
     * @param hora
     * @return 
     */
    public int dispDocente(int idDocente, String dia, int hora){
        int i=0;
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT COUNT(*) FROM Turno WHERE hora=(?) AND dia=(?) AND idDocente="+idDocente);
            stm.setInt(1, hora);
            stm.setString(2, dia);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                i=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        if(i>0) return 0;
        else return 1;
    }
    
    /**
     * Indica se é possível mudar uma teórica para um determinado dia e hora.
     * @param hora
     * @param dia
     * @param anoNov
     * @param semNov
     * @return i
     */
    public int horVazio(int hora, String dia, int anoNov, int semNov){
        int i=1;
        ArrayList<Turno> col = new ArrayList<>();
        try {
            conn = Connect.connectDocente();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Turno WHERE hora=(?) AND dia=(?)");
            stm.setInt(1, hora);
            stm.setString(2, dia);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Turno t = new Turno(rs.getInt("numeroTurno"),rs.getString("dia"),rs.getInt("hora"),
                                    getUC(rs.getInt("codigoUC")),rs.getInt("capSala"),getTipo(rs.getInt("idTipo")),
                                    getDocente(rs.getInt("idDocente")));
                col.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        for(Turno t : col){
            if(t.getUC().getAno()==anoNov && t.getUC().getSemestre()==semNov) {return 0;} //se não é possivel mudar a teorica 
            else i=1;
        }
        return i; //se é possível.
    }

    /**
     * Obtém os turnos de uma UC.
     * @param uc
     * @return 
     */
    public Collection<Turno> turnosDaUC(int uc) {
        Collection<Turno> col = new HashSet<Turno>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Turno WHERE codigoUC="+uc);
            while (rs.next()) {
                Turno t = new Turno(rs.getInt("numeroTurno"),rs.getString("dia"),rs.getInt("hora"),
                                    getUC(rs.getInt("codigoUC")),rs.getInt("capSala"),getTipo(rs.getInt("idTipo")),
                                    getDocente(rs.getInt("idDocente")));
                col.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return col;
    }

    /**
     * Obtém todos os tipos de aula da base de dados
     * @return 
     */
    public Collection<TipoAula> tiposAula() {
        Collection<TipoAula> col = new HashSet<TipoAula>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM TipoAula");
            while (rs.next()) {
                TipoAula a = new TipoAula(rs.getInt("idTipo"),rs.getString("tipo"),rs.getInt("limite"));
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
     * Obtém todos os cursos da base de dados
     * @return 
     */
    public Collection<Turno> values() {
        Collection<Turno> col = new HashSet<Turno>();
        try {
            conn = Connect.connectDocente();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Turno");
            while (rs.next()) {
                Turno t = new Turno(rs.getInt("numeroTurno"),rs.getString("dia"),rs.getInt("hora"),
                                    getUC(rs.getInt("codigoUC")),rs.getInt("capSala"),
                                    getTipo(rs.getInt("idTipo")),getDocente(rs.getInt("idDocente")));
                col.add(t);
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
     * Obter o curso, dado o seu ID
     * @param id
     * @return Curso c
     */
    public Curso getCurso(int id) {
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
    public UC getUC(int cod) {
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
    public TipoAula getTipo(int id) {
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
    
    /**
     * Obtém o turno com maior ID.
     * @return 
     */
    private int getMaiorID(){
        int i=0;
        try {
            conn = Connect.connectAdmin();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT numeroTurno FROM Turno ORDER BY numeroTurno DESC LIMIT 1");

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
    
}