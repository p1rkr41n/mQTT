package Interface;

import Core.ActionType;
import Core.ClientManager;
import Core.Result;
import Core.ResultCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends JFrame implements Observer {

    /**
     * Creates new form Login
     */
    ClientManager mClientManager;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - huy
    private JLabel jLabel1;
    private JTextField txtUserName;
    private JButton btnPublish;
    private JLabel jLabel2;
    private JButton btnSubcribe;

    public Login() {
        initComponents();
    }

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
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - huy
    private void initComponents() {
        jLabel1 = new JLabel();
        txtUserName = new JTextField();
        btnPublish = new JButton();
        jLabel2 = new JLabel();
        btnSubcribe = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("mQTT");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                formWindowClosing(e);
            }
        });
        var contentPane = getContentPane();

        //---- jLabel1 ----
        jLabel1.setText("USERNAME:");

        //---- txtUserName ----
        txtUserName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtUserNameKeyTyped(e);
            }
        });

        //---- btnPublish ----
        btnPublish.setText("Publish");
        btnPublish.addActionListener(e -> btnPublishActionPerformed(e));

        //---- jLabel2 ----
        jLabel2.setFont(new Font("SF Pro Text", Font.BOLD, 14));
        jLabel2.setText("Input your username:");

        //---- btnSubcribe ----
        btnSubcribe.setText("Subcribe");
        btnSubcribe.addActionListener(e -> btnSubcribeActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addComponent(jLabel2)
                                                        .addComponent(txtUserName, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(106, 106, 106)
                                                .addComponent(btnPublish)
                                                .addGap(31, 31, 31)
                                                .addComponent(btnSubcribe)))
                                .addContainerGap(49, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addGap(32, 32, 32)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnPublish)
                                        .addComponent(btnSubcribe))
                                .addContainerGap(47, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void update(Observable o, Object arg) {
        btnPublish.setEnabled(true);
        btnSubcribe.setEnabled(true);
        Result result = (Result) arg;
        if (result.mResultCode.equals(ResultCode.ERROR)) {

            JOptionPane.showMessageDialog(null, result.mContent, "Failed",JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
            return;
        } else if (result.mActionType.equals(ActionType.LOGIN)) {
            mClientManager.mUsername = txtUserName.getText().trim();
            mClientManager.deleteObserver(this);   //delete this observer
            TopicList topicList = new TopicList(this, mClientManager);
            topicList.setVisible(true);
            this.setVisible(false);
        }
    }

    private void btnPublishActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnPublishActionPerformed
        // TODO add your handling code here:
        String userName = txtUserName.getText().trim();
        String pubsub = "Publisher";
        if (userName.length() == 0) {
            JOptionPane.showMessageDialog(null, "Force input locate", "Input Invalid", JOptionPane.WARNING_MESSAGE);
            txtUserName.requestFocus();
            return;
        }
        if (!userName.startsWith("/")) {
            JOptionPane.showMessageDialog(null, "Publisher must start with /", "Form is not correct", JOptionPane.WARNING_MESSAGE);
            txtUserName.requestFocus();
            return;
        }
        if (mClientManager != null) {
            mClientManager.Dispose();
        }
        mClientManager = new ClientManager(this);
        if (mClientManager.StartConnect()) {
            try {
                btnPublish.setEnabled(false);
//                btnSubcribe.setEnabled(false);
                mClientManager.Login(userName);
                mClientManager.Pubsub(pubsub);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnPublishActionPerformed

    private void btnSubcribeActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSubcribeActionPerformed
        // TODO add your handling code here:
        String userName = txtUserName.getText().trim();
        String pubsub = "Subcriber";
        if (userName.length() == 0) {
            JOptionPane.showMessageDialog(null, "Force input locate", "Input Invalid", JOptionPane.WARNING_MESSAGE);
            txtUserName.requestFocus();
            return;
        }
        if (userName.startsWith("/")) {
            JOptionPane.showMessageDialog(null, "Subcriber can't start with /", "Form is not correct", JOptionPane.WARNING_MESSAGE);
            txtUserName.requestFocus();
            return;
        }
        if (mClientManager != null) {
            mClientManager.Dispose();
        }
        mClientManager = new ClientManager(this);
        if (mClientManager.StartConnect()) {
            try {
                btnSubcribe.setEnabled(false);
                mClientManager.Login(userName);
                mClientManager.Pubsub(pubsub);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSubcribeActionPerformed

    private void txtUserNameKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyTyped
        // TODO add your handling code here:
        if (evt.getKeyChar() == ';')
            evt.consume();
    }//GEN-LAST:event_txtUserNameKeyTyped

    private void formWindowClosing(WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (mClientManager != null)
            mClientManager.Dispose();
    }//GEN-LAST:event_formWindowClosing
    // End of variables declaration//GEN-END:variables


}
