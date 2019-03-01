package cliente.forms;

import cliente.ClienteConnection;
import cliente.User;
import java.util.ArrayList;
import java.util.HashMap;
import servidor.Server;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KIKO
 */
public class MenuForm extends javax.swing.JFrame {
    private final JFrame login;
    private final ClienteConnection connection;
    private User user;
    private HashMap<String,ArrayList<Server>> bdServers;

    private DefaultTableModel modelDemand;
    private DefaultTableModel modelBid;
    private DefaultTableModel modelMy;

    /**
     * Creates new form MenuForm
     * @param login Login Form reference
     * @param connection Connection to the server
     * @param user User that's logged in
     * @param bdServers Servers List
     */
    public MenuForm(JFrame login, User user, HashMap<String,ArrayList<Server>> bdServers, ClienteConnection connection) {
        this.login = login;
        this.connection = connection;
        this.user = user;
        this.bdServers = bdServers;
        fillDemandTable();
        fillBidTable();
        fillMyServersTable();
        initComponents();
        fillMyInfoArea();
        adjustColumnSizes();
        this.setTitle("Cloud Server Manager - " + user.getEmail());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.demandServersTable.setDefaultEditor(Object.class, null);
        this.bidServersTable.setDefaultEditor(Object.class, null);
        this.myServersTable.setDefaultEditor(Object.class, null);
    }

    public void fillDemandTable(){
        String[] colunas = {"Tipo","Número","Preço/hora"};
        Object[][] data = new Object[this.bdServers.size()][3];
        int i=0;
        for (String tipo : this.bdServers.keySet()){
            ArrayList<Server> aux = new ArrayList<>();
            for(Server s : this.bdServers.get(tipo)){
                if( !s.getUsed() || (s.getUsed() && s.getIsLeilao() ) ){
                    aux.add(s);
                }
            }
            if( aux.size()>0 ){
                data[i][0] = tipo;
                data[i][1] = aux.size();
                data[i][2] = aux.get(0).getPrice();
                i++;
            }
        }
        modelDemand = new DefaultTableModel(data,colunas);
    }
    public void fillBidTable(){
        String[] colunas = {"Tipo","Número","Preço/hora"};
        Object[][] data = new Object[this.bdServers.size()][3];
        int i=0;
        for (String tipo : this.bdServers.keySet()){
            double minBid=100000000;
            ArrayList<Server> aux = new ArrayList<>();
            Server auxS = null;
            for(Server s : this.bdServers.get(tipo)){
                if(!s.getUsed()){
                    auxS = s;
                    aux.add(s);
                }
                else if(s.getIsLeilao() && s.getLastBid()<minBid){
                    minBid=s.getLastBid();
                    aux.add(s);
                }
            }
            if( auxS!=null || aux.size()>0 ){
                if(auxS!=null){
                    data[i][0] = tipo;
                    data[i][1] = aux.size();
                    data[i][2] = 0.01;
                }
                else{
                    data[i][0] = tipo;
                    data[i][1] = aux.size();
                    data[i][2] = minBid;
                }
                i++;
            }
        }
        modelBid = new DefaultTableModel(data,colunas);
    }
    public void fillMyServersTable(){
        String[] colunas = {"ID Reserva","Nome"};
        Object[][] data = new Object[this.user.getServidoresAlocados().size()][2];
        int i=0;
        for (Server s : this.user.getServidoresAlocados()){
            data[i][0] = s.getIdReserva();
            data[i][1] = s.getNome();
            i++;
        }
        modelMy = new DefaultTableModel(data,colunas);

        myServersTable = new JTable(modelMy);
    }
    public void fillMyInfoArea(){
        this.emailLabel.setText("User email: " + user.getEmail());
        this.payPerHourLabel.setText("Money per Hour: " + user.getPayPerHour());
        this.debtLabel.setText("Debt: " + user.getDebt());
    }

    private void adjustColumnSizes(){
        demandServersTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        demandServersTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        demandServersTable.getColumnModel().getColumn(2).setPreferredWidth(100);

        bidServersTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        bidServersTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        bidServersTable.getColumnModel().getColumn(2).setPreferredWidth(100);

        myServersTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        myServersTable.getColumnModel().getColumn(1).setPreferredWidth(150);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        demandPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        demandServersTable = new javax.swing.JTable();
        buyButton = new javax.swing.JButton();
        bidPanel = new javax.swing.JPanel();
        bidSpinner = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        bidServersTable = new javax.swing.JTable();
        bidButton = new javax.swing.JButton();
        myServersPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        myServersTable = new javax.swing.JTable();
        removeServerButton = new javax.swing.JButton();
        emailLabel = new javax.swing.JLabel();
        payPerHourLabel = new javax.swing.JLabel();
        debtLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        demandServersTable.setModel(this.modelDemand);
        jScrollPane1.setViewportView(demandServersTable);

        buyButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        buyButton.setText("USE SERVER");
        buyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout demandPanelLayout = new javax.swing.GroupLayout(demandPanel);
        demandPanel.setLayout(demandPanelLayout);
        demandPanelLayout.setHorizontalGroup(
            demandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(demandPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(demandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, demandPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        demandPanelLayout.setVerticalGroup(
            demandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, demandPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Demand server", demandPanel);

        bidSpinner.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        bidSpinner.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.01f));

        bidServersTable.setModel(this.modelBid);
        jScrollPane2.setViewportView(bidServersTable);

        bidButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        bidButton.setText("BID SERVER");
        bidButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bidButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bidPanelLayout = new javax.swing.GroupLayout(bidPanel);
        bidPanel.setLayout(bidPanelLayout);
        bidPanelLayout.setHorizontalGroup(
            bidPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bidPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bidPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bidPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bidSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(bidButton, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                .addContainerGap())
        );
        bidPanelLayout.setVerticalGroup(
            bidPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bidPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(bidPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bidButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bidSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Bid server", bidPanel);

        myServersTable.setModel(this.modelMy);
        jScrollPane3.setViewportView(myServersTable);

        removeServerButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        removeServerButton.setText("REMOVE SERVER");
        removeServerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeServerButtonActionPerformed(evt);
            }
        });

        emailLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        emailLabel.setText("Email");

        payPerHourLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        payPerHourLabel.setText("Per Hour");

        debtLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        debtLabel.setText("Debt");

        javax.swing.GroupLayout myServersPanelLayout = new javax.swing.GroupLayout(myServersPanel);
        myServersPanel.setLayout(myServersPanelLayout);
        myServersPanelLayout.setHorizontalGroup(
            myServersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myServersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(myServersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, myServersPanelLayout.createSequentialGroup()
                        .addComponent(debtLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeServerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addComponent(emailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(payPerHourLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        myServersPanelLayout.setVerticalGroup(
            myServersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myServersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(payPerHourLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(myServersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(myServersPanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(removeServerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(myServersPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(debtLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("My servers", myServersPanel);

        logoutButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        logoutButton.setText("LOGOUT");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        refreshButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        refreshButton.setText("REFRESH");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(logoutButton)
                        .addGap(18, 18, 18)
                        .addComponent(refreshButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bidButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bidButtonActionPerformed
        double bid = new Double(bidSpinner.getValue().toString());
        int row = bidServersTable.getSelectedRow();
        String tipo = bidServersTable.getModel().getValueAt(row, 0).toString();

        // o servidor fica encarrege de verificar se a nova licitaçao é maior que a anterior
        String response = this.connection.sendRequest("BID " + user.getEmail() + " " + tipo + " " + bid);
        String status = response.split(" ")[0];
        if(status.equals("SUCCESS")){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
            JOptionPane.showMessageDialog(null, "Licitação efetuada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        } else{
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/forbidden.png"));
            JOptionPane.showMessageDialog(null, "Valor licitado não é superior à licitação atual", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }

        this.refresh();
    }//GEN-LAST:event_bidButtonActionPerformed

    private void buyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyButtonActionPerformed
        int row = demandServersTable.getSelectedRow();
        String tipo = demandServersTable.getModel().getValueAt(row, 0).toString();

        String response = this.connection.sendRequest("BUY " + user.getEmail() + " " + tipo);
        String status = response.split(" ")[0];
        if( status.equals("SUCCESS") ){
            //Declarar como utilizado & adicionar à lista de servers do cliente!
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
            JOptionPane.showMessageDialog(null, "Servidor adicionado à sua lista!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else{
            //Declarar erro ou falha do pedido
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Servidor não adicionado à sua lista!", "Erro", JOptionPane.INFORMATION_MESSAGE, icon);
        }

        this.refresh();
    }//GEN-LAST:event_buyButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // fecha o menu
        this.connection.closeConnection();
        // abre o login
        this.login.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        this.refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void refresh(){
        // request refresh User and Servers list
        this.connection.sendRequest("GET_USER_SERVERS " + this.user.getEmail());
        this.connection.receiveUserAndServers();
        this.user = this.connection.getUser();
        this.bdServers = this.connection.getServers();

        // refresh window
        MenuForm menu = new MenuForm(this.login,this.user,this.bdServers,this.connection);
        menu.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }

    private void removeServerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeServerButtonActionPerformed
        int row = myServersTable.getSelectedRow();
        String idReserva = myServersTable.getModel().getValueAt(row, 0).toString();

        //Efetuar o pedido ao servidor
        String response = this.connection.sendRequest("REM " + user.getEmail() + " " + idReserva);
        String status = response.split(" ")[0];
        if( status.equals("SUCCESS") ){
            //Declarar como utilizado & adicionar à lista de servers do cliente!
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/check.png"));
            JOptionPane.showMessageDialog(null, "Servidor removido da sua lista!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else{
            //Declarar erro ou falha do pedido
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Servidor não removido da sua lista!", "Erro", JOptionPane.INFORMATION_MESSAGE, icon);
        }

        this.refresh();
    }//GEN-LAST:event_removeServerButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bidButton;
    private javax.swing.JPanel bidPanel;
    private javax.swing.JTable bidServersTable;
    private javax.swing.JSpinner bidSpinner;
    private javax.swing.JButton buyButton;
    private javax.swing.JLabel debtLabel;
    private javax.swing.JPanel demandPanel;
    private javax.swing.JTable demandServersTable;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel myServersPanel;
    private javax.swing.JTable myServersTable;
    private javax.swing.JLabel payPerHourLabel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton removeServerButton;
    // End of variables declaration//GEN-END:variables
}
