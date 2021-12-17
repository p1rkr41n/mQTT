package Core;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Broker extends javax.swing.JFrame implements Observer {

    /**
     * Creates new form UI
     */
    ServerManager mServerManager;

    public Broker() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTurnOn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtStatus = new javax.swing.JTextArea();
        btnCountTopic = new javax.swing.JButton();
        btnCountUser = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("I'm broker");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnTurnOn.setText("Turn On");
        btnTurnOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurnOnActionPerformed(evt);
            }
        });

        txtStatus.setColumns(20);
        txtStatus.setRows(5);
        jScrollPane1.setViewportView(txtStatus);

        btnCountTopic.setText("Count Topic");
        btnCountTopic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCountTopicActionPerformed(evt);
            }
        });

        btnCountUser.setText("Count User");
        btnCountUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCountUserActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SF Pro Text", 1, 18)); // NOI18N
        jLabel1.setText("Broker manager");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btnTurnOn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnCountUser)
                                                                .addGap(81, 81, 81)
                                                                .addComponent(btnCountTopic)))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnTurnOn)
                                        .addComponent(btnCountTopic)
                                        .addComponent(btnCountUser))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTurnOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurnOnActionPerformed
        // TODO add your handling code here:
        mServerManager = new ServerManager(this);
        mServerManager.StartServer();
    }//GEN-LAST:event_btnTurnOnActionPerformed

    private void btnCountUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCountUserActionPerformed
        // TODO add your handling code here:
        txtStatus.append("Numbers of user(s): " + mServerManager.CountUser() + "\n");
    }//GEN-LAST:event_btnCountUserActionPerformed

    private void btnCountTopicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCountTopicActionPerformed
        // TODO add your handling code here:
        txtStatus.append("Numbers of topic(s): " + mServerManager.CountTopic() + "\n");
    }//GEN-LAST:event_btnCountTopicActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            if (mServerManager != null)
                mServerManager.Dispose();
        } catch (IOException ex) {
            Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Broker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Broker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Broker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Broker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Broker().setVisible(true);

            }
        });
    }

    // Variables declaration //GEN-BEGIN:variables
    private javax.swing.JButton btnCountTopic;
    private javax.swing.JButton btnCountUser;
    private javax.swing.JButton btnTurnOn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtStatus;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        txtStatus.append(arg.toString() + "\n");
    }
}

