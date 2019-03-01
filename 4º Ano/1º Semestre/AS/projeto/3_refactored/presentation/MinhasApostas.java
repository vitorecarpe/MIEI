package presentation;

import business.Aposta;
import business.Apostador;
import data.BetESS;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class MinhasApostas extends javax.swing.JFrame {

    private BetESS betess;
    private Apostador apostador;
    private DefaultTableModel model;

    public MinhasApostas(BetESS b, Apostador a) {        
        this.betess = b;
        this.apostador = a;
        
        preencherTabela();
        
        this.setTitle("Minhas Apostas");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/logo2.png"));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(155, 35, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        this.logo.setIcon(icon);
        perfilButton.setBackground(new Color(0,0,0));
        perfilButton.setContentAreaFilled(false);
        perfilButton.setOpaque(true);
        perfilButton.setText(this.apostador.getNome());
    }
    
    public void preencherTabela(){
        String[] colunas = {"ID","Evento", "Resultado", "Valor", "Ganhos"};
        Object[][] data = new Object[apostador.getApostasAtivas().size()][5];
        int i=0;
        for (Aposta a : apostador.getApostasAtivas()){
            
            data[i][0] = a.getEventoID();
            data[i][1] = a.getEquipaCasaNome()+" x "+a.getEquipaForaNome();
            data[i][2] = a.equipaPalpite();
            data[i][3] = a.getValor();
            data[i][4] = a.ganhos(true);
            i++;
        }
        model = new DefaultTableModel(data,colunas);

        initComponents();
        
        betsTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        betsTable.getColumnModel().getColumn(1).setPreferredWidth(230);
        betsTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        betsTable.getColumnModel().getColumn(3).setPreferredWidth(40);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        perfilButton = new javax.swing.JButton();
        movimentosButton = new javax.swing.JButton();
        apostasButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        betsTable = new javax.swing.JTable();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        logo.setText("jLabel1");

        perfilButton.setBackground(new java.awt.Color(0, 0, 0));
        perfilButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        perfilButton.setForeground(new java.awt.Color(255, 102, 102));
        perfilButton.setText("Perfil");
        perfilButton.setBorderPainted(false);
        perfilButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        perfilButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        perfilButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perfilButtonActionPerformed(evt);
            }
        });

        movimentosButton.setBackground(new java.awt.Color(0, 0, 0));
        movimentosButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        movimentosButton.setForeground(new java.awt.Color(204, 204, 204));
        movimentosButton.setText("Depositar/Levantar");
        movimentosButton.setBorderPainted(false);
        movimentosButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        movimentosButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        movimentosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movimentosButtonActionPerformed(evt);
            }
        });

        apostasButton.setBackground(new java.awt.Color(0, 0, 0));
        apostasButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        apostasButton.setForeground(new java.awt.Color(204, 204, 204));
        apostasButton.setText("Eventos ativos");
        apostasButton.setBorderPainted(false);
        apostasButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        apostasButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        apostasButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apostasButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(apostasButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(movimentosButton)
                .addGap(69, 69, 69)
                .addComponent(perfilButton, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(perfilButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(movimentosButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(logo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(apostasButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        betsTable.setModel(model);
        betsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(betsTable);

        deleteButton.setText("Eliminar");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void movimentosButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movimentosButtonActionPerformed
        DepositarLevantar dl = new DepositarLevantar(this.betess, apostador);
        dl.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_movimentosButtonActionPerformed

    private void apostasButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apostasButtonActionPerformed
        Home home = new Home(this.betess, apostador);
        home.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_apostasButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int row = betsTable.getSelectedRow();
        int value = (Integer) model.getValueAt(row, 0);
        Aposta aposta = new Aposta();
        for(Aposta a: apostador.getApostas()){
            if (a.getEventoID() == value) aposta = a;
        }
        apostador.removerAposta(aposta);
        saveNrefresh();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void perfilButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_perfilButtonActionPerformed
        Login login = new Login(this.betess);
        login.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_perfilButtonActionPerformed

    private void saveNrefresh(){
        betess.save();
        MinhasApostas ma = new MinhasApostas(betess, apostador);
        ma.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println("Error in Main");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton apostasButton;
    private javax.swing.JTable betsTable;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logo;
    private javax.swing.JButton movimentosButton;
    private javax.swing.JButton perfilButton;
    // End of variables declaration//GEN-END:variables
}