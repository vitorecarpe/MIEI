package presentation;
import business.Aposta;
import business.Apostador;
import business.BetESS;
import business.Equipa;
import business.Evento;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class Admin extends javax.swing.JFrame {

    BetESS betess;
    
    public Admin(BetESS b) {
        
        this.betess = b;
        
        initComponents();
        
        this.setTitle("Área de Administração");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/logo2.png"));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(155, 35, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        this.logo.setIcon(icon);
        
        fillCombos();
        
       
    }
    
    private void fillCombos(){
        ArrayList<Equipa> eqDisp = new ArrayList<>(betess.getEquipas().values());
        ArrayList<Evento> evAtiv = new ArrayList<>();
        for(Evento e : this.betess.getEventos().values()){
            if(e.getEstado())
                evAtiv.add(e);
        }
        for(Evento e : evAtiv){
            eqDisp.remove(e.getEquipaC());
            eqDisp.remove(e.getEquipaF());
            this.eventCombo.addItem(e.getEquipaC().getNome() + " X " + e.getEquipaF().getNome());
        }
        for(Equipa eq : eqDisp){
            this.casaCombo.addItem(eq.getNome());
            this.foraCombo.addItem(eq.getNome());
        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        perfilButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Saldo = new javax.swing.JLabel();
        Levantar = new javax.swing.JLabel();
        Levantar1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        casaCombo = new javax.swing.JComboBox<>();
        Levantar2 = new javax.swing.JLabel();
        foraCombo = new javax.swing.JComboBox<>();
        oddV = new javax.swing.JTextField();
        oddE = new javax.swing.JTextField();
        oddD = new javax.swing.JTextField();
        criarButton = new javax.swing.JButton();
        eventCombo = new javax.swing.JComboBox<>();
        resField = new javax.swing.JTextField();
        Levantar3 = new javax.swing.JLabel();
        fecharButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        logo.setText("jLabel1");

        perfilButton.setBackground(new java.awt.Color(0, 0, 0));
        perfilButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        perfilButton.setForeground(new java.awt.Color(255, 102, 102));
        perfilButton.setText("Logout");
        perfilButton.setBorderPainted(false);
        perfilButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        perfilButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        perfilButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perfilButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Área de Administração");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(107, 107, 107)
                .addComponent(perfilButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(207, 207, 207))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(perfilButton, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addComponent(jLabel1))
            .addComponent(logo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Saldo.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        Saldo.setForeground(new java.awt.Color(255, 255, 255));
        Saldo.setText("Criar evento");

        Levantar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Levantar.setForeground(new java.awt.Color(255, 255, 255));
        Levantar.setText("Resultado:");

        Levantar1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        Levantar1.setForeground(new java.awt.Color(255, 255, 255));
        Levantar1.setText("Fechar evento");

        Levantar2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Levantar2.setForeground(new java.awt.Color(255, 255, 255));
        Levantar2.setText("x");

        criarButton.setText("Criar evento");
        criarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarButtonActionPerformed(evt);
            }
        });

        Levantar3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Levantar3.setForeground(new java.awt.Color(180, 180, 180));
        Levantar3.setText("(2-1 por exemplo)");

        fecharButton.setText("Fechar evento");
        fecharButton.setToolTipText("");
        fecharButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fecharButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(casaCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Levantar2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(foraCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(oddV, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(oddE, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(oddD, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(74, 74, 74)
                                .addComponent(criarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Levantar1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(eventCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Levantar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(resField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Levantar3)))
                                .addGap(73, 73, 73)
                                .addComponent(fecharButton)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Saldo)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(criarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(casaCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Levantar2)
                            .addComponent(foraCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(oddE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(oddD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(oddV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(Levantar1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(eventCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Levantar)
                            .addComponent(Levantar3)))
                    .addComponent(fecharButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void perfilButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_perfilButtonActionPerformed
        Login login = new Login(this.betess);
        login.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_perfilButtonActionPerformed

    private void fecharButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fecharButtonActionPerformed
        String[] equipas = split(this.eventCombo.getSelectedItem().toString(), " X ");
        ArrayList<Evento> evAtiv = new ArrayList<>();
        for(Evento e : this.betess.getEventos().values()){
            if(e.getEstado())
                evAtiv.add(e);
        }
        for(Evento e : evAtiv){
            if(e.getEquipaC().getNome().equals(equipas[0]) && e.getEquipaF().getNome().equals(equipas[1])){
                this.betess.finalizarEvento(e, resField.getText());
                String[] venc = split(resField.getText(),"-");
                int res;
                if(Integer.parseInt(venc[0])>Integer.parseInt(venc[1])){ //equipa casa venceu
                    System.out.println("equipa casa venceu\n");
                    res = 1;
                }
                else if(Integer.parseInt(venc[1])>Integer.parseInt(venc[0])){ //equipa fora venceu
                    System.out.println("equipa fora venceu\n");
                    res = 3;
                }
                else{ //empate
                    System.out.println("empate\n");
                    res = 2;
                }
                for(Apostador a : this.betess.getApostadores().values()){
                    //ArrayList<Aposta> toRem = new ArrayList<>();
                    for(Aposta ap : a.getApostas()){
                        if(ap.getEvento().equals(e)){
                            if(ap.getResultado()==res){
                                if(res==1) a.adicionarESSCoins(e.getOddV()*ap.getValor());
                                else if(res==2) a.adicionarESSCoins(e.getOddE()*ap.getValor());
                                else if(res==3) a.adicionarESSCoins(e.getOddD()*ap.getValor());
                                //toRem.add(ap);
                                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/check.png"));
                                JOptionPane.showMessageDialog(null, "Evento encerrado e prémios distribuídos.", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
                                //a.getApostas().remove(ap);
                            }
                            ap.notificaApostador();
                        }
                        
                    }
                    //a.getApostas().removeAll(toRem);
                }  
            }
        }
        try {
            betess.save(betess);
        } catch (IOException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        Admin admin = new Admin(this.betess);
        admin.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_fecharButtonActionPerformed

    private void criarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarButtonActionPerformed
        Equipa casa = new Equipa();
        Equipa fora = new Equipa();
        for(Equipa a : this.betess.getEquipas().values()){
            if(a.getNome().equals(casaCombo.getSelectedItem().toString())){
                casa = a;
            }
            else if(a.getNome().equals(foraCombo.getSelectedItem().toString())){
                fora = a;
            }
        }
        if(casaCombo.getSelectedItem().toString().equals(foraCombo.getSelectedItem().toString())){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/forbidden.png"));
            JOptionPane.showMessageDialog(null, "As equipas selecionadas são a mesma. Por favor escolha outra.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else{
            int id = this.betess.getEventos().size()+1;
            Evento evento = new Evento(id, Double.parseDouble(oddV.getText()) , Double.parseDouble(oddE.getText()), Double.parseDouble(oddD.getText()), true, " ", casa, fora); 
            this.betess.criarEvento(evento);
            try {
                betess.save(betess);
            } catch (IOException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/check.png"));
            JOptionPane.showMessageDialog(null, "Evento criado e disponível.", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }

    }//GEN-LAST:event_criarButtonActionPerformed

    private String[] split(String res, String sig){
        String[] parts = res.split(sig);
        return parts;
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Levantar;
    private javax.swing.JLabel Levantar1;
    private javax.swing.JLabel Levantar2;
    private javax.swing.JLabel Levantar3;
    private javax.swing.JLabel Saldo;
    private javax.swing.JComboBox<String> casaCombo;
    private javax.swing.JButton criarButton;
    private javax.swing.JComboBox<String> eventCombo;
    private javax.swing.JButton fecharButton;
    private javax.swing.JComboBox<String> foraCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField oddD;
    private javax.swing.JTextField oddE;
    private javax.swing.JTextField oddV;
    private javax.swing.JButton perfilButton;
    private javax.swing.JTextField resField;
    // End of variables declaration//GEN-END:variables
}
