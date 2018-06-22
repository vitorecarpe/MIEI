package horarios.business;

/**
 * Facade para a camada de negócio
 * Contém os métodos identificados nos diagramas de sequencia de implementação
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
import horarios.data.*;
import horarios.presentation.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class Facade {
    //O docente com login actual
    private Utilizador sessao;
    private IntegridadeDados integridade;
    //DAO's
    private AlunoDAO alunoDAO;
    private DocenteDAO docenteDAO;
    private CursoDAO cursoDAO;
    private UCDAO ucDAO;
    private TurnoDAO turnoDAO;
    private RegistoDAO registoDAO;
    private TrocaDAO trocaDAO;
    
    //INSTANCE
    public Facade() { //TODO: inicializar todas as DAO's
        integridade = new IntegridadeDados();
        alunoDAO = new AlunoDAO();
        docenteDAO = new DocenteDAO();
        cursoDAO = new CursoDAO();
        ucDAO = new UCDAO();
        turnoDAO = new TurnoDAO();
        registoDAO = new RegistoDAO();
        trocaDAO = new TrocaDAO();
    }
    
    //SET
    public void setSessao(Utilizador u){
        this.sessao = u;
    }
    public void setAlunoDAO(AlunoDAO a){
        this.alunoDAO = a;
    }
    public void setDocenteDAO(DocenteDAO d){
        this.docenteDAO = d;
    }
    public void setCursoDAO(CursoDAO c){
        this.cursoDAO = c;
    }
    public void setUCDAO(UCDAO uc){
        this.ucDAO = uc;
    }
    public void setRegistoDAO(RegistoDAO r){
        this.registoDAO = r;
    }
    public void setTrocaDAO(TrocaDAO t){
        this.trocaDAO = t;
    }

    //GET
    public Utilizador getSessao(){
        return sessao;
    }
    public IntegridadeDados getIntegridade(){
        return this.integridade;
    }
    public AlunoDAO getAlunoDAO(){
        return this.alunoDAO;
    }
    public DocenteDAO getDocenteDAO(){
        return this.docenteDAO;
    }
    public CursoDAO getCursoDAO(){
        return this.cursoDAO;
    }
    public UCDAO getUCDAO(){
        return this.ucDAO;
    }
    public TurnoDAO getTurnoDAO(){
        return this.turnoDAO;
    }
    public RegistoDAO getRegistoDAO(){
        return this.registoDAO;
    }
    public TrocaDAO getTrocaDAO(){
        return this.trocaDAO;
    }
    
    //LOGIN & LOGOUT
    public boolean login(int id, String password, JFrame formLogin){
        for(Aluno a : alunoDAO.values()) {
            if( (id==a.getID()) && password.equals(a.getPassword()) && a.getEstatuto().equals("Normal") ){
                sessao = new Aluno(alunoDAO.getAluno(id));
                FormEstudante fEst = new FormEstudante(this,formLogin);
                fEst.setVisible(true);
                formLogin.setVisible(false); //Fecha o form login
                return true;
            }
            else if( (id==a.getID()) && password.equals(a.getPassword()) && a.getEstatuto().equals("Trabalhador") ){
                sessao = new Aluno(alunoDAO.getAluno(id));
                FormTrabEstudante fTrabEst = new FormTrabEstudante(this,formLogin);
                fTrabEst.setVisible(true);
                formLogin.setVisible(false); //Fecha o form login
                return true;
            }
        for(Docente d : docenteDAO.values())
            if( (id==d.getID()) && password.equals(d.getPassword()) ){
                sessao = new Docente(docenteDAO.getDocente(id));
                FormDocente fDoc = new FormDocente(this,formLogin);
                fDoc.setVisible(true);
                formLogin.setVisible(false); //Fecha o form login
                return true;
            }   
        }
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
        JOptionPane.showMessageDialog(null, "Dados incorretos!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        return false;
    }
    public void logout(JFrame formToClose, JFrame formLogin){
        sessao = null;
        formToClose.dispose();
        formLogin.setVisible(true);
        //JFrame1.dispose();  //Remove JFrame 1
        //JFrame2.setVisible(true); //Show other frame
    }
    
    //1ºFASE
    public void registarUC(UC uc) throws ClassNotFoundException{
        ucDAO.put(uc);
    }
    public void registarAluno(Aluno a) throws ClassNotFoundException{
        alunoDAO.put(a);
    }
    
    //2ºFASE
    public Collection<Troca> verTrocas(int idAluno){
        return trocaDAO.verTrocas(idAluno);
    }
    public void aceitarTroca(Troca t) throws SQLException, ClassNotFoundException{
        Collection<Registo> reg2 = registoDAO.getTurnos(t.getAID2());
        String dia1   = turnoDAO.getTurno(t.getTID1()).getDia();
        int hora1     = turnoDAO.getTurno(t.getTID1()).getHora();
        int ano1      = turnoDAO.getTurno(t.getTID1()).getUC().getAno();
        int semestre1 = turnoDAO.getTurno(t.getTID1()).getUC().getSemestre();
        int i=0;
        for(Registo r : reg2){
            if((dia1.equals(r.getTurno().getDia())) && (hora1==r.getTurno().getHora()) && (ano1==r.getTurno().getUC().getAno()) && (semestre1==r.getTurno().getUC().getSemestre())){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
                JOptionPane.showMessageDialog(null, "Não pode alterar para o novo turno \n"
                        + "visto que já tem aulas nessa hora.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
                i=1;
            }
            if((dia1.equals(r.getTurno().getDia())) && (hora1==r.getTurno().getHora()) && (ano1!=r.getTurno().getUC().getAno()) && (semestre1==r.getTurno().getUC().getSemestre())){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
                int dialogResult = JOptionPane.showConfirmDialog(null, "Já tem aulas doutro ano na hora do turno pretendido.\n"
                        + "Pretende aceitar o pedido de troca mesmo assim?.", "Aviso", JOptionPane.YES_NO_OPTION, 0, icon);
                if(dialogResult == 0) i=0;
                else i=1;
            }
        }
        if(t.getAID1()==t.getAID2()){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Não pode trocar turnos entre o mesmo aluno!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(t.getTID1()==t.getTID2()){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O turno é o mesmo!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if((turnoDAO.getTurno(t.getTID1()).getTipoAula().getID()==1) || (turnoDAO.getTurno(t.getTID2()).getTipoAula().getID()==1)) {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Não pode efetuar trocas de aulas teóricas!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(i==0){
            try {
                registoDAO.aloca(t.getAID1(), t.getUC(), t.getTID2());
                registoDAO.remove(t.getAID1(), t.getTID1());
                registoDAO.aloca(t.getAID2(), t.getUC(), t.getTID1());
                registoDAO.remove(t.getAID2(), t.getTID2());
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
                JOptionPane.showMessageDialog(null, "Troca efetuada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        trocaDAO.removerTroca(t.getID());
    }
    public void recusarTroca(Troca t) throws SQLException, ClassNotFoundException{
        trocaDAO.removerTroca(t.getID());
    }
    public void trocaTurno(Troca t) throws SQLException, ClassNotFoundException{
        Collection<Registo> reg1 = registoDAO.getTurnos(t.getAID1());
        Collection<Registo> reg2 = registoDAO.getTurnos(t.getAID2());
        String dia1   = turnoDAO.getTurno(t.getTID1()).getDia();
        String dia2   = turnoDAO.getTurno(t.getTID2()).getDia();
        int hora1     = turnoDAO.getTurno(t.getTID1()).getHora();
        int hora2     = turnoDAO.getTurno(t.getTID2()).getHora();
        int ano1      = turnoDAO.getTurno(t.getTID1()).getUC().getAno();
        int ano2      = turnoDAO.getTurno(t.getTID2()).getUC().getAno();
        int semestre1 = turnoDAO.getTurno(t.getTID1()).getUC().getSemestre();
        int semestre2 = turnoDAO.getTurno(t.getTID2()).getUC().getSemestre();
        int i=0;
        for(Registo r : reg1){
            if((dia2.equals(r.getTurno().getDia())) && (hora2==r.getTurno().getHora()) && (ano2==r.getTurno().getUC().getAno()) && (semestre2==r.getTurno().getUC().getSemestre())){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
                JOptionPane.showMessageDialog(null, "Não pode alterar para o novo turno \n"
                        + "visto que já tem aulas nessa hora.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
                i=1;
            }
            if((dia2.equals(r.getTurno().getDia())) && (hora2==r.getTurno().getHora()) && (ano2!=r.getTurno().getUC().getAno()) && (semestre2==r.getTurno().getUC().getSemestre())){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
                int dialogResult = JOptionPane.showConfirmDialog(null, "Já tem aulas doutro ano na hora do turno pretendido.\n"
                        + "Pretende efetuar o pedido de troca mesmo assim?.", "Aviso", JOptionPane.YES_NO_OPTION, 0, icon);
                if(dialogResult == 0) i=0;
                else i=1;
            }
        } for(Registo r : reg2){
            if((dia1.equals(r.getTurno().getDia())) && (hora1==r.getTurno().getHora()) && (ano1==r.getTurno().getUC().getAno()) && (semestre1==r.getTurno().getUC().getSemestre())){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
                JOptionPane.showMessageDialog(null, "Não pode alterar para o novo turno visto que o horário do\n"
                        + "outro aluno não lhe permite assistir ao seu turno.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
                i=1;
            }
        }
        if(t.getAID1()==t.getAID2()){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Não pode trocar turnos entre o mesmo aluno!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(t.getTID1()==t.getTID2()){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O turno é o mesmo!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if((turnoDAO.getTurno(t.getTID1()).getTipoAula().getID()==1) || (turnoDAO.getTurno(t.getTID2()).getTipoAula().getID()==1)) {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Não pode efetuar trocas de aulas teóricas!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(i==0){
            trocaDAO.adicionarTroca(t.getAID1(),t.getAID2(),t.getUC(),t.getTID1(),t.getTID2());
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
            JOptionPane.showMessageDialog(null, "Pedido de troca efetuado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    public void trocaTurnoEspecial(int idAluno, int codUC, int numTurno1, int numTurno2) throws SQLException{
        Collection<Registo> reg = registoDAO.getTurnos(idAluno);
        String dia   = turnoDAO.getTurno(numTurno2).getDia();
        int hora     = turnoDAO.getTurno(numTurno2).getHora();
        int ano      = turnoDAO.getTurno(numTurno2).getUC().getAno();
        int semestre = turnoDAO.getTurno(numTurno2).getUC().getSemestre();
        int i=0;
        for(Registo r : reg){
            if((dia.equals(r.getTurno().getDia())) && (hora==r.getTurno().getHora()) && (ano==r.getTurno().getUC().getAno()) && (semestre==r.getTurno().getUC().getSemestre())){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
                JOptionPane.showMessageDialog(null, "Não pode alterar para o novo turno \n"
                        + "visto que já tem aulas nessa hora.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
                i=1;
            }
            if((dia.equals(r.getTurno().getDia())) && (hora==r.getTurno().getHora()) && (ano!=r.getTurno().getUC().getAno()) && (semestre==r.getTurno().getUC().getSemestre())){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
                int dialogResult = JOptionPane.showConfirmDialog(null, "Já tem aulas doutro ano nessa hora.\nPretende inscrever-se no turno mesmo assim?.", "Aviso", JOptionPane.YES_NO_OPTION, 0, icon);
                if(dialogResult == 0) i=0;
                else i=1;
            }
        } if(numTurno1==numTurno2){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O turno é o mesmo!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if((turnoDAO.getTurno(numTurno1).getCapacidade()) <= (registoDAO.numeroAlunosInscritosTurno(numTurno2))){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O turno pretendido encontra-se preenchido!\n Troque com outro aluno desse turno.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if((turnoDAO.getTurno(numTurno1).getTipoAula().getID()==1) || (turnoDAO.getTurno(numTurno2).getTipoAula().getID()==1)) {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Não pode efetuar trocas de aulas teóricas!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(i==0){
            try {
                registoDAO.aloca(idAluno, codUC, numTurno2);
                registoDAO.remove(idAluno, numTurno1);
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
                JOptionPane.showMessageDialog(null, "Troca efetuada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //3ºFASE
    public void trocaTurnoDiretor(Troca t) throws SQLException, ClassNotFoundException{
        Collection<Registo> reg1 = registoDAO.getTurnos(t.getAID1());
        Collection<Registo> reg2 = registoDAO.getTurnos(t.getAID2());
        String dia1   = turnoDAO.getTurno(t.getTID1()).getDia();
        String dia2   = turnoDAO.getTurno(t.getTID2()).getDia();
        int hora1     = turnoDAO.getTurno(t.getTID1()).getHora();
        int hora2     = turnoDAO.getTurno(t.getTID2()).getHora();
        int ano1      = turnoDAO.getTurno(t.getTID1()).getUC().getAno();
        int ano2      = turnoDAO.getTurno(t.getTID2()).getUC().getAno();
        int semestre1 = turnoDAO.getTurno(t.getTID1()).getUC().getSemestre();
        int semestre2 = turnoDAO.getTurno(t.getTID2()).getUC().getSemestre();
        int i=0;
        for(Registo r : reg1){
            if((dia2.equals(r.getTurno().getDia())) && (hora2==r.getTurno().getHora()) && (ano2==r.getTurno().getUC().getAno()) && (semestre2==r.getTurno().getUC().getSemestre())){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
                JOptionPane.showMessageDialog(null, "O 1º aluno não pode alterar para o turno do 2º aluno \n"
                        + "visto que já tem aulas nessa hora.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
                i=1;
            }
        } for(Registo r : reg2){
            if((dia1.equals(r.getTurno().getDia())) && (hora1==r.getTurno().getHora()) && (ano1==r.getTurno().getUC().getAno()) && (semestre1==r.getTurno().getUC().getSemestre())){
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
                JOptionPane.showMessageDialog(null, "O 2º aluno não pode alterar para o turno do 1º aluno \n"
                        + "visto que já tem aulas nessa hora.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
                i=1;
            }
        }
        if(t.getAID1()==t.getAID2()){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Não pode trocar turnos entre o mesmo aluno!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(t.getTID1()==t.getTID2()){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O turno é o mesmo!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(i==0){
            registoDAO.aloca(t.getAID1(), t.getUC(), t.getTID2());
            registoDAO.remove(t.getAID1(), t.getTID1());
            registoDAO.aloca(t.getAID2(), t.getUC(), t.getTID1());
            registoDAO.remove(t.getAID2(), t.getTID2());
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
            JOptionPane.showMessageDialog(null, "Troca efetuada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    public void removeAluno(int idAluno, int numTurno) throws ClassNotFoundException, SQLException {
        registoDAO.remove(idAluno, numTurno);
    }
    public void marcarFalta(int codUC, int numTurno, int idAluno, int falta){
        registoDAO.marcarFalta(codUC, numTurno, idAluno, falta);
        
        int aulas = registoDAO.getNumAulas(numTurno);
        int faltas = registoDAO.getNumFaltas(numTurno, idAluno);
        
        if(faltas*4 >= aulas) {
            try {
                removeAluno(idAluno, numTurno);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FormDocente.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Aluno excedeu limite de faltas e foi removido do turno!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    
    //QUALQUER FASE
    public void mudarFase(int fase){
        docenteDAO.setFase(fase);
    }
    public void adicionarTurno(Turno turno) throws ClassNotFoundException{
        String dia = turno.getDia();
        int hora = turno.getHora();
        int codigoUC = turno.getUC().getUC();
        int capSala = turno.getCapacidade();
        int idTipo = turno.getTipoAula().getID();
        int idDocente = turno.getDocente().getID();
        
        UC cad = turnoDAO.getUC(codigoUC);
        Turno t = turnoDAO.getTeoricas(hora, dia);
        int anoAnt = t.getUC().getAno();
        int semAnt = t.getUC().getSemestre();
        int curAnt = t.getUC().getCurso().getID();
        int anoNov = cad.getAno();
        int semNov = cad.getSemestre();
        int curNov = cad.getCurso().getID();
        if((turnoDAO.getCountTeoricas(hora, dia)>0) && (anoAnt==anoNov && semAnt==semNov && curAnt==curNov)){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Já existe uma aula teórica desse ano para a hora definida!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else if(idTipo==1 && turnoDAO.horVazio(hora, dia, anoNov, semNov)==0){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Uma teórica não pode coincidir com outros turnos do mesmo ano!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else if(turnoDAO.dispDocente(idDocente, dia, hora)==0){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O docente do novo turno não está disponível para essa hora!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else{
            turnoDAO.adicionarTurno(dia, hora, codigoUC, capSala, idTipo, idDocente);
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
            JOptionPane.showMessageDialog(null, "Turno adicionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    public void editarTurno(Turno turno) throws ClassNotFoundException{
        int id = turno.getTurno();
        String dia = turno.getDia();
        int hora = turno.getHora();
        int codigoUC = turno.getUC().getUC();
        int capSala = turno.getCapacidade();
        int idTipo = turno.getTipoAula().getID();
        int idDocente = turno.getDocente().getID();
        
        UC cad = turnoDAO.getUC(codigoUC);
        Turno t = turnoDAO.getTeoricas(hora, dia);
        int anoAnt = t.getUC().getAno();
        int semAnt = t.getUC().getSemestre();
        int curAnt = t.getUC().getCurso().getID();
        int anoNov = cad.getAno();
        int semNov = cad.getSemestre();
        int curNov = cad.getCurso().getID();
        if((turnoDAO.getCountTeoricas(hora, dia)>0) && (anoAnt==anoNov && semAnt==semNov && curAnt==curNov)){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Já existe uma aula teórica desse ano nessa hora!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else if(idTipo==1 && turnoDAO.horVazio(hora, dia, anoNov, semNov)==0){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Uma teórica não pode coincidir com outros turnos do mesmo ano!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else if(turnoDAO.dispDocente(idDocente, dia, hora)==0){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O docente desse turno não está disponível para essa hora!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else{
            try {
                turnoDAO.editarTurno(id, dia, hora, codigoUC, capSala, idTipo, idDocente);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FormDirecaoCurso.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
            JOptionPane.showMessageDialog(null, "Turno alterado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        
    }
    public void removerTurno(int numTurno){
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
        int dialogResult = JOptionPane.showConfirmDialog(null, "Tem a certeza que quer apagar o turno? \n"
                + "Esta ação não pode ser revertida.", "Aviso", JOptionPane.YES_NO_OPTION, 0, icon);
        if(dialogResult == 0) {
            turnoDAO.apagarTurno(numTurno);
        }
    }
    public Collection<Registo> getTurnos(int idAluno){
        return registoDAO.getTurnos(idAluno);
    }
    public void alocarAluno(int idAluno, int codUC, int numTurno) throws ClassNotFoundException {
        Collection<Integer> registos = registoDAO.turnoUCAluno(idAluno, codUC);
        Turno teo = new Turno();
        
        for(Integer i : registos){
            if(registoDAO.getTurno(i).getTipoAula().getID()==1){
                teo = registoDAO.getTurno(i);
            }
        }
        
        if (registos.size()>1){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O aluno já está inscrito nos turnos da UC!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(registos.size()==1 && teo.getTurno()==0 && registoDAO.getTurno(numTurno).getTipoAula().getID()!=1){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O aluno já está inscrito num turno prático da UC!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(registos.size()==1 && teo.getTurno()!=0 && registoDAO.getTurno(numTurno).getTipoAula().getID()==1){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O aluno já está inscrito num turno teórico da UC!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else if(registoDAO.numeroAlunosInscritosTurno(numTurno)==turnoDAO.getCap(numTurno)){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "O turno atingiu o limite máximo de alunos!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else{
            registoDAO.aloca(idAluno, codUC, numTurno);
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
            JOptionPane.showMessageDialog(null, "O aluno foi alocado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    public void alteraCapacidade(int numTurno, int capacidade, int idTipo){
        if(idTipo != 1){
            turnoDAO.setCap(numTurno, capacidade);
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
            JOptionPane.showMessageDialog(null, "Capacidade alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else{
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Não pode alterar capacidade de turnos teóricos!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
}