package horarios.presentation;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
import horarios.business.*;
import java.awt.Color;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FormDirecaoCurso extends javax.swing.JFrame {
    private Facade facade;
    private final JFrame formPai;

    //INSTANCE
    public FormDirecaoCurso(Facade facade, JFrame formPai) {
        initComponents();
        this.facade = facade;
        this.formPai = formPai;
        refreshComboDoc();
        refreshComboCurso();
        refreshComboAluno();
        refreshComboUC();
        refreshComboTurno();
        refreshComboTipoAula();
        displayTurnoInfo();
        fases();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void fases(){
        int fase = facade.getDocenteDAO().getFase();
        if(fase==1){
            button1Fase.setEnabled(false);
            button2Fase.setEnabled(true);
            button3Fase.setEnabled(false); 
            tabDirCurso.setEnabledAt(0, true); 
            tabDirCurso.setBackgroundAt(0, null); 
            tabDirCurso.setForegroundAt(0, null); 
            tabDirCurso.setEnabledAt(1, true); 
            tabDirCurso.setBackgroundAt(1, null); 
            tabDirCurso.setForegroundAt(1, null); 
            tabDirCurso.setEnabledAt(2, true); 
            tabDirCurso.setBackgroundAt(2, null); 
            tabDirCurso.setForegroundAt(2, null);
            tabDirCurso.setEnabledAt(3, true); 
            tabDirCurso.setBackgroundAt(3, null); 
            tabDirCurso.setForegroundAt(3, null);
            tabDirCurso.setSelectedIndex(0);
        }
        else if(fase==2){
            button1Fase.setEnabled(false);
            button2Fase.setEnabled(false);
            button3Fase.setEnabled(true);
            tabDirCurso.setEnabledAt(0, false);
            tabDirCurso.setBackgroundAt(0, Color.gray);
            tabDirCurso.setForegroundAt(0, Color.darkGray);
            tabDirCurso.setEnabledAt(1, false);
            tabDirCurso.setBackgroundAt(1, Color.gray);
            tabDirCurso.setForegroundAt(1, Color.darkGray);
            tabDirCurso.setSelectedIndex(2);
        }
        else if(fase==3){
            button1Fase.setEnabled(true);
            button2Fase.setEnabled(false);
            button3Fase.setEnabled(false);
            tabDirCurso.setEnabledAt(0, false);
            tabDirCurso.setBackgroundAt(0, Color.gray);
            tabDirCurso.setForegroundAt(0, Color.darkGray);
            tabDirCurso.setEnabledAt(1, false);
            tabDirCurso.setBackgroundAt(1, Color.gray);
            tabDirCurso.setForegroundAt(1, Color.darkGray);
            tabDirCurso.setEnabledAt(2, false);
            tabDirCurso.setBackgroundAt(2, Color.gray);
            tabDirCurso.setForegroundAt(2, Color.darkGray);
            tabDirCurso.setEnabledAt(3, false);
            tabDirCurso.setBackgroundAt(3, Color.gray);
            tabDirCurso.setForegroundAt(3, Color.darkGray);
            tabDirCurso.setSelectedIndex(4);

        }
    }
    private void refreshComboDoc() { 
        for(Docente d : facade.getDocenteDAO().values()) {
            comboDocenteRUC.addItem(Integer.toString(d.getID()));
            comboDocT.addItem(Integer.toString(d.getID()));
        }
    }
    private void refreshComboCurso() { 
        for(Curso c : facade.getCursoDAO().values()) {
            comboCursoRUC.addItem(Integer.toString(c.getID()));
        }
    }
    private void refreshComboAluno() { 
        for(Aluno a : facade.getAlunoDAO().values()) {
            comboIDAlunoAA.addItem(Integer.toString(a.getID()));
        }
    }
    private void refreshComboUC() { 
        for(UC c : facade.getUCDAO().values()) {
            comboCodAA.addItem(Integer.toString(c.getUC()));
            comboCodT2.addItem(Integer.toString(c.getUC()));
        }
    }
    private void refreshComboTurno() { 
        for(Turno t : facade.getTurnoDAO().values()) {
            comboTurnoAA.addItem(Integer.toString(t.getTurno()));
            comboTurnoT.addItem(Integer.toString(t.getTurno()));
        }
    }
    private void refreshComboTipoAula() { 
        for(TipoAula t : facade.getTurnoDAO().tiposAula()) {
            comboTipoT.addItem(Integer.toString(t.getID()));
        }
    }
    private void displayTurnoInfo(){
        int turno = Integer.parseInt(comboTurnoT.getSelectedItem().toString());
        Turno t = facade.getTurnoDAO().getTurno(turno);
        textDiaT1.setText(t.getDia());
        textHoraT1.setText(Integer.toString(t.getHora()));
        textDocT.setText(Integer.toString(t.getDocente().getID()));
        textCap1.setText(Integer.toString(t.getCapacidade()));
        textTipoT.setText(Integer.toString(t.getTipoAula().getID()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabDirCurso = new javax.swing.JTabbedPane();
        panelRUC = new javax.swing.JPanel();
        textCodRUC = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        textNomeRUC = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textAbrevRUC = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textAnoRUC = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textSemestreRUC = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        comboCursoRUC = new javax.swing.JComboBox<>();
        comboDocenteRUC = new javax.swing.JComboBox<>();
        buttonRUC = new javax.swing.JButton();
        panelRA = new javax.swing.JPanel();
        textNomeRA = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        textPasswordRA = new javax.swing.JTextField();
        textEmailRA = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        textAnoRA = new javax.swing.JTextField();
        textIDAlunoRA = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        textSemestreRA = new javax.swing.JTextField();
        comboEstatutoRA = new javax.swing.JComboBox<>();
        buttonRA = new javax.swing.JButton();
        panelAA = new javax.swing.JPanel();
        comboIDAlunoAA = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        comboCodAA = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        comboTurnoAA = new javax.swing.JComboBox<>();
        buttonAA = new javax.swing.JButton();
        PanelT = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        comboTurnoT = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        textDiaT1 = new javax.swing.JTextField();
        textHoraT1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        textCap1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        textTipoT = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        comboCodT2 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        textDiaT2 = new javax.swing.JTextField();
        textHoraT2 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        comboDocT = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        textDocT = new javax.swing.JTextField();
        comboTipoT = new javax.swing.JComboBox<>();
        buttonRegTurno = new javax.swing.JButton();
        buttonEliminarT = new javax.swing.JButton();
        buttonGuardarT = new javax.swing.JButton();
        textCap2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        button1Fase = new javax.swing.JButton();
        button2Fase = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        button3Fase = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel2.setText("Nome");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel3.setText("Abreviatura");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel4.setText("ID Docente");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel5.setText("ID Curso");

        textAnoRUC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textAnoRUCFocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel6.setText("Ano");

        textSemestreRUC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textSemestreRUCFocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel7.setText("Semestre");

        buttonRUC.setText("Registar");
        buttonRUC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRUCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRUCLayout = new javax.swing.GroupLayout(panelRUC);
        panelRUC.setLayout(panelRUCLayout);
        panelRUCLayout.setHorizontalGroup(
            panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRUCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRUCLayout.createSequentialGroup()
                        .addGroup(panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textCodRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(12, 12, 12)
                        .addGroup(panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRUCLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelRUCLayout.createSequentialGroup()
                                .addGroup(panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textNomeRUC, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelRUCLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(50, 50, 50)
                                        .addComponent(jLabel6)
                                        .addGap(50, 50, 50)
                                        .addComponent(jLabel7)
                                        .addGap(23, 23, 23)
                                        .addComponent(jLabel3)
                                        .addGap(0, 34, Short.MAX_VALUE)))
                                .addContainerGap())))
                    .addGroup(panelRUCLayout.createSequentialGroup()
                        .addGroup(panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRUCLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelRUCLayout.createSequentialGroup()
                                .addComponent(comboCursoRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboDocenteRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textAnoRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textSemestreRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textAbrevRUC)))
                        .addContainerGap())))
            .addGroup(panelRUCLayout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(buttonRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelRUCLayout.setVerticalGroup(
            panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRUCLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textCodRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textNomeRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRUCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textAbrevRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textAnoRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textSemestreRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCursoRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDocenteRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(buttonRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        tabDirCurso.addTab("Registar UC", panelRUC);

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel8.setText("Semestre");

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel9.setText("Nome");

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel10.setText("Email");

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel11.setText("Password");

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel12.setText("Estatuto");

        textAnoRA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textAnoRAFocusLost(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel13.setText("Ano");

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel14.setText("ID Aluno");

        textSemestreRA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textSemestreRAFocusLost(evt);
            }
        });

        comboEstatutoRA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Trabalhador" }));

        buttonRA.setText("Registar");
        buttonRA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRALayout = new javax.swing.GroupLayout(panelRA);
        panelRA.setLayout(panelRALayout);
        panelRALayout.setHorizontalGroup(
            panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRALayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRALayout.createSequentialGroup()
                        .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboEstatutoRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRALayout.createSequentialGroup()
                                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(textAnoRA, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(textSemestreRA, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(buttonRA, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRALayout.createSequentialGroup()
                        .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelRALayout.createSequentialGroup()
                                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textIDAlunoRA, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addGap(12, 12, 12)
                                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(textNomeRA)))
                            .addGroup(panelRALayout.createSequentialGroup()
                                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(textEmailRA, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(textPasswordRA, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(32, 32, 32))))
        );
        panelRALayout.setVerticalGroup(
            panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRALayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textIDAlunoRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textNomeRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textPasswordRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textEmailRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textSemestreRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textAnoRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboEstatutoRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(buttonRA, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        tabDirCurso.addTab("Registar aluno", panelRA);

        jLabel15.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel15.setText("ID Aluno");

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel16.setText("Código UC");

        comboCodAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCodAAActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel17.setText("Turno ");

        buttonAA.setText("Alocar");
        buttonAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAALayout = new javax.swing.GroupLayout(panelAA);
        panelAA.setLayout(panelAALayout);
        panelAALayout.setHorizontalGroup(
            panelAALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAALayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelAALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonAA, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAALayout.createSequentialGroup()
                        .addGroup(panelAALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(comboIDAlunoAA, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelAALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16)
                            .addComponent(comboCodAA, 0, 128, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(comboTurnoAA, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        panelAALayout.setVerticalGroup(
            panelAALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAALayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelAALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelAALayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTurnoAA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAALayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboCodAA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAALayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboIDAlunoAA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addComponent(buttonAA, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        tabDirCurso.addTab("Alocar aluno", panelAA);

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel18.setText("Dia");

        comboTurnoT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTurnoTActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel19.setText("Turno");

        textDiaT1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textDiaT1FocusLost(evt);
            }
        });

        textHoraT1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textHoraT1FocusLost(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel21.setText("Hora");

        jLabel22.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel22.setText("Capacidade");

        textTipoT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textTipoTFocusLost(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel23.setText("Tipo");

        jLabel24.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel24.setText("Código UC");

        textDiaT2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textDiaT2FocusLost(evt);
            }
        });

        textHoraT2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textHoraT2FocusLost(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel25.setText("Hora");

        jLabel26.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel26.setText("Capacidade");

        jLabel27.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel27.setText("Tipo");

        jLabel28.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel28.setText("Dia");

        jLabel30.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel30.setText("Docente");

        jLabel31.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel31.setText("Docente");

        buttonRegTurno.setText("Registar novo");
        buttonRegTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRegTurnoActionPerformed(evt);
            }
        });

        buttonEliminarT.setText("Eliminar");
        buttonEliminarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEliminarTActionPerformed(evt);
            }
        });

        buttonGuardarT.setText("Guardar");
        buttonGuardarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGuardarTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelTLayout = new javax.swing.GroupLayout(PanelT);
        PanelT.setLayout(PanelTLayout);
        PanelTLayout.setHorizontalGroup(
            PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(PanelTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(PanelTLayout.createSequentialGroup()
                        .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(comboCodT2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textDiaT2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(12, 12, 12)
                        .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textHoraT2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(comboTipoT, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelTLayout.createSequentialGroup()
                            .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(textDocT)
                                .addComponent(comboTurnoT, 0, 116, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(textCap1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(PanelTLayout.createSequentialGroup()
                                    .addComponent(textTipoT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(buttonGuardarT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jLabel23)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelTLayout.createSequentialGroup()
                            .addComponent(jLabel31)
                            .addGap(88, 88, 88)
                            .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel18)
                                .addComponent(textDiaT1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(PanelTLayout.createSequentialGroup()
                                    .addComponent(textHoraT1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(buttonEliminarT, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel21))))
                    .addGroup(PanelTLayout.createSequentialGroup()
                        .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelTLayout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(88, 88, 88))
                            .addGroup(PanelTLayout.createSequentialGroup()
                                .addComponent(comboDocT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(6, 6, 6)))
                        .addGap(6, 6, 6)
                        .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addGroup(PanelTLayout.createSequentialGroup()
                                .addComponent(textCap2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(buttonRegTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        PanelTLayout.setVerticalGroup(
            PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTurnoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textDiaT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textHoraT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonEliminarT))
                .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelTLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonGuardarT, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textDocT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textCap1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textTipoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelTLayout.createSequentialGroup()
                        .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addGap(32, 32, 32)))
                .addGap(4, 4, 4)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel28)
                    .addComponent(jLabel25)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCodT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textDiaT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textHoraT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTipoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboDocT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRegTurno)
                    .addComponent(textCap2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tabDirCurso.addTab("Turnos", PanelT);

        button1Fase.setText("Iniciar 1ª Fase");
        button1Fase.setEnabled(false);
        button1Fase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1FaseActionPerformed(evt);
            }
        });

        button2Fase.setText("Iniciar 2ª Fase");
        button2Fase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2FaseActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N

        button3Fase.setText("Iniciar 3ª Fase");
        button3Fase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3FaseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(button1Fase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button2Fase, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button3Fase, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(99, 99, 99)
                    .addComponent(jLabel35)
                    .addContainerGap(361, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button2Fase, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button3Fase, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button1Fase, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(182, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(85, 85, 85)
                    .addComponent(jLabel35)
                    .addContainerGap(164, Short.MAX_VALUE)))
        );

        tabDirCurso.addTab("Fases", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabDirCurso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabDirCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRAActionPerformed
        try{    
            int id = Integer.parseInt(textIDAlunoRA.getText());
            String nome = textNomeRA.getText();
            String password = textPasswordRA.getText();
            String email = textEmailRA.getText();                                   
            String estatuto = comboEstatutoRA.getSelectedItem().toString();                                   
            int ano = Integer.parseInt(textAnoRA.getText());                                   
            int semestre = Integer.parseInt(textSemestreRA.getText());
        
            Aluno aluno = new Aluno(id, nome, password, email, estatuto, ano, semestre);
        
            facade.registarAluno(aluno);
        } catch(NumberFormatException e){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormDirecaoCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonRAActionPerformed

    private void buttonRUCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRUCActionPerformed
        try{
            int codigoUC = Integer.parseInt(textCodRUC.getText());
            String nome = textNomeRUC.getText();
            int ano = Integer.parseInt(textAnoRUC.getText());
            int semestre = Integer.parseInt(textSemestreRUC.getText());
            int idDocente = Integer.parseInt(comboDocenteRUC.getSelectedItem().toString());
            String abreviatura = textAbrevRUC.getText();
            int idCurso = Integer.parseInt(comboCursoRUC.getSelectedItem().toString());

            UC cadeira = new UC(codigoUC, nome, ano, semestre, facade.getDocenteDAO().getDocente(idDocente), abreviatura, facade.getTurnoDAO().getCurso(idCurso));
        
            facade.registarUC(cadeira);
        }catch(NumberFormatException e){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormDirecaoCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonRUCActionPerformed
        
    private void button1FaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1FaseActionPerformed
        facade.mudarFase(1);
        fases();
    }//GEN-LAST:event_button1FaseActionPerformed

    private void comboTurnoTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTurnoTActionPerformed
        displayTurnoInfo();
    }//GEN-LAST:event_comboTurnoTActionPerformed

    private void textAnoRUCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textAnoRUCFocusLost
        String text = textAnoRUC.getText();
        facade.getIntegridade().anoList(text);
    }//GEN-LAST:event_textAnoRUCFocusLost

    private void textSemestreRUCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textSemestreRUCFocusLost
        String text = textSemestreRUC.getText();
        facade.getIntegridade().semestreList(text);
    }//GEN-LAST:event_textSemestreRUCFocusLost

    private void textAnoRAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textAnoRAFocusLost
        String text = textAnoRA.getText();
        facade.getIntegridade().anoList(text);
    }//GEN-LAST:event_textAnoRAFocusLost

    private void textSemestreRAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textSemestreRAFocusLost
        String text = textSemestreRA.getText();
        facade.getIntegridade().semestreList(text);
    }//GEN-LAST:event_textSemestreRAFocusLost

    private void comboCodAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCodAAActionPerformed
        int id = Integer.parseInt(comboCodAA.getSelectedItem().toString());
        comboTurnoAA.removeAllItems();
        for(Turno t : facade.getTurnoDAO().turnosDaUC(id)) {
            comboTurnoAA.addItem(Integer.toString(t.getTurno()));
        }
    }//GEN-LAST:event_comboCodAAActionPerformed

    private void buttonAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAAActionPerformed
        try{
            int idAluno = Integer.parseInt(comboIDAlunoAA.getSelectedItem().toString());
            int codigoUC = Integer.parseInt(comboCodAA.getSelectedItem().toString());
            int numeroTurno = Integer.parseInt(comboTurnoAA.getSelectedItem().toString());
            
            facade.alocarAluno(idAluno, codigoUC, numeroTurno);
        }catch(NumberFormatException e){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormDirecaoCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonAAActionPerformed

    private void buttonGuardarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGuardarTActionPerformed
        int id = Integer.parseInt(comboTurnoT.getSelectedItem().toString());
        String dia = textDiaT1.getText();
        int hora = Integer.parseInt(textHoraT1.getText());
        int codigoUC = facade.getTurnoDAO().getUCTurno(id);
        int capSala = Integer.parseInt(textCap1.getText());
        int idTipo = Integer.parseInt(textTipoT.getText());
        int idDocente = Integer.parseInt(textDocT.getText());
        Turno turno = new Turno(id, dia, hora, facade.getTurnoDAO().getUC(codigoUC), capSala, facade.getTurnoDAO().getTipo(idTipo), facade.getDocenteDAO().getDocente(idDocente));
        
        try {
            facade.editarTurno(turno);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormDirecaoCurso.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_buttonGuardarTActionPerformed

    private void textDiaT1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textDiaT1FocusLost
        String text = textDiaT1.getText();
        facade.getIntegridade().diaList(text);
    }//GEN-LAST:event_textDiaT1FocusLost

    private void textHoraT1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textHoraT1FocusLost
        String hora = textHoraT1.getText();
        facade.getIntegridade().horaList(hora);
    }//GEN-LAST:event_textHoraT1FocusLost

    private void textDiaT2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textDiaT2FocusLost
        String text = textDiaT2.getText();
        facade.getIntegridade().diaList(text);
    }//GEN-LAST:event_textDiaT2FocusLost

    private void textHoraT2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textHoraT2FocusLost
        String hora = textHoraT2.getText();
        facade.getIntegridade().horaList(hora);
    }//GEN-LAST:event_textHoraT2FocusLost

    private void textTipoTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textTipoTFocusLost
        String tipo = textTipoT.getText();
        facade.getIntegridade().tipoList(tipo);
    }//GEN-LAST:event_textTipoTFocusLost

    private void buttonEliminarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEliminarTActionPerformed
        facade.removerTurno(Integer.parseInt(comboTurnoT.getSelectedItem().toString()));
        comboTurnoT.removeItemAt(comboTurnoT.getSelectedIndex());
    }//GEN-LAST:event_buttonEliminarTActionPerformed

    private void buttonRegTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRegTurnoActionPerformed
        String dia = textDiaT2.getText();
        int hora = Integer.parseInt(textHoraT2.getText());
        int codigoUC = Integer.parseInt(comboCodT2.getSelectedItem().toString());
        int capSala = Integer.parseInt(textCap2.getText());
        int idTipo = Integer.parseInt(comboTipoT.getSelectedItem().toString());
        int idDocente = Integer.parseInt(comboDocT.getSelectedItem().toString());
        Turno t = new Turno(0, dia, hora, facade.getTurnoDAO().getUC(codigoUC), capSala, facade.getTurnoDAO().getTipo(idTipo), facade.getDocenteDAO().getDocente(idDocente));
        try {
            facade.adicionarTurno(t);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormDirecaoCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonRegTurnoActionPerformed

    private void button2FaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2FaseActionPerformed
        facade.mudarFase(2);
        fases();
    }//GEN-LAST:event_button2FaseActionPerformed

    private void button3FaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3FaseActionPerformed
        facade.mudarFase(3);
        fases();
    }//GEN-LAST:event_button3FaseActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelT;
    private javax.swing.JButton button1Fase;
    private javax.swing.JButton button2Fase;
    private javax.swing.JButton button3Fase;
    private javax.swing.JButton buttonAA;
    private javax.swing.JButton buttonEliminarT;
    private javax.swing.JButton buttonGuardarT;
    private javax.swing.JButton buttonRA;
    private javax.swing.JButton buttonRUC;
    private javax.swing.JButton buttonRegTurno;
    private javax.swing.JComboBox<String> comboCodAA;
    private javax.swing.JComboBox<String> comboCodT2;
    private javax.swing.JComboBox<String> comboCursoRUC;
    private javax.swing.JComboBox<String> comboDocT;
    private javax.swing.JComboBox<String> comboDocenteRUC;
    private javax.swing.JComboBox<String> comboEstatutoRA;
    private javax.swing.JComboBox<String> comboIDAlunoAA;
    private javax.swing.JComboBox<String> comboTipoT;
    private javax.swing.JComboBox<String> comboTurnoAA;
    private javax.swing.JComboBox<String> comboTurnoT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelAA;
    private javax.swing.JPanel panelRA;
    private javax.swing.JPanel panelRUC;
    private javax.swing.JTabbedPane tabDirCurso;
    private javax.swing.JTextField textAbrevRUC;
    private javax.swing.JTextField textAnoRA;
    private javax.swing.JTextField textAnoRUC;
    private javax.swing.JTextField textCap1;
    private javax.swing.JTextField textCap2;
    private javax.swing.JTextField textCodRUC;
    private javax.swing.JTextField textDiaT1;
    private javax.swing.JTextField textDiaT2;
    private javax.swing.JTextField textDocT;
    private javax.swing.JTextField textEmailRA;
    private javax.swing.JTextField textHoraT1;
    private javax.swing.JTextField textHoraT2;
    private javax.swing.JTextField textIDAlunoRA;
    private javax.swing.JTextField textNomeRA;
    private javax.swing.JTextField textNomeRUC;
    private javax.swing.JTextField textPasswordRA;
    private javax.swing.JTextField textSemestreRA;
    private javax.swing.JTextField textSemestreRUC;
    private javax.swing.JTextField textTipoT;
    // End of variables declaration//GEN-END:variables
}