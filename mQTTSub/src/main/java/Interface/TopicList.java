package Interface;

import Core.ActionType;
import Core.ClientManager;
import Core.Result;
import Core.ResultCode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TopicList extends javax.swing.JFrame implements Observer {

    /**
     * Creates new form TopicList
     */
    ClientManager mClientManager;
    String mUserName;
    Login mLogin;
    List<String> joinedList = new ArrayList<String>();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - huy
    private JScrollPane jScrollPane1;
    private JTable tableTopicList;
    private JButton btnRefresh;
    private JButton btnJoinTopic;
    private JButton btnLogout;
    private JTextField txtTopicName;
    private JButton btnCreateTopic;
    private JLabel jLabel1;

    public TopicList(Login login, ClientManager clientManager) {
        initComponents();
        mLogin = login;
        mClientManager = clientManager;
        mClientManager.addObserver(this);
    }

    public void FillTopicList(Result result) {
        DefaultTableModel dtm = (DefaultTableModel) tableTopicList.getModel();
        if (result.mContent.length() > 0) {

            String[] rows = result.mContent.split("<row>");
            for (int i = 0; i < rows.length; i++) //fist row is header
            {
                String[] cols = rows[i].split("<col>");

                dtm.addRow(cols);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - huy
    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        tableTopicList = new JTable();
        btnRefresh = new JButton();
        btnJoinTopic = new JButton();
        btnLogout = new JButton();
        txtTopicName = new JTextField();
        btnCreateTopic = new JButton();
        jLabel1 = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Topic");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                formWindowClosing(e);
            }

            @Override
            public void windowOpened(WindowEvent e) {
                formWindowOpened(e);
            }
        });
        var contentPane = getContentPane();

        //======== jScrollPane1 ========
        {

            //---- tableTopicList ----
            tableTopicList.setModel(new DefaultTableModel(
                    new Object[][]{
                    },
                    new String[]{
                            "Topic ID", "Topic name", "Numbers of member"
                    }
            ) {
                final boolean[] columnEditable = new boolean[]{
                        false, false, false
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            jScrollPane1.setViewportView(tableTopicList);
        }

        //---- btnRefresh ----
        btnRefresh.setText("Reload list");
        btnRefresh.addActionListener(e -> btnRefreshActionPerformed(e));

        //---- btnJoinTopic ----
        btnJoinTopic.setText("Join");
        btnJoinTopic.addActionListener(e -> btnJoinTopicActionPerformed(e));

        //---- btnLogout ----
        btnLogout.setText("Leave");
        btnLogout.addActionListener(e -> btnLogoutActionPerformed(e));

        //---- txtTopicName ----
        txtTopicName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtTopicNameKeyTyped(e);
            }
        });

        //---- btnCreateTopic ----
        btnCreateTopic.setText("Create topic");
        btnCreateTopic.addActionListener(e -> btnCreateTopicActionPerformed(e));

        //---- jLabel1 ----
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabel1.setText("Topic list");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 528, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(btnJoinTopic, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnLogout, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTopicName)
                                        .addComponent(btnCreateTopic, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnRefresh, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addContainerGap(351, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(345, 345, 345))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(23, 23, 23)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(btnRefresh)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnJoinTopic)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                                .addComponent(txtTopicName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnCreateTopic)
                                                .addGap(116, 116, 116)
                                                .addComponent(btnLogout))
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateTopicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateTopicActionPerformed
        // TODO add your handling code here:
        String topicName = txtTopicName.getText().trim();
        if (topicName.length() == 0) {
            JOptionPane.showMessageDialog(null, "Input topic name", "Failed", JOptionPane.WARNING_MESSAGE);
            txtTopicName.requestFocus();
            return;
        }
        mClientManager.CreateTopic(topicName);
    }//GEN-LAST:event_btnCreateTopicActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        btnRefresh.setEnabled(false);
        DefaultTableModel dtm = (DefaultTableModel) tableTopicList.getModel();
        dtm.setRowCount(0);
        mClientManager.GetTopicList();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnJoinTopicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinTopicActionPerformed
        // TODO add your handling code here:
        int indexRow = tableTopicList.getSelectedRow();
        if (indexRow < 0) {
            JOptionPane.showMessageDialog(null, "Choose a topic", "Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String topicID = tableTopicList.getValueAt(indexRow, 0).toString();
        if (joinedList.contains(topicID)) {
            JOptionPane.showMessageDialog(null, "You have joined this topic", "Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }
        joinedList.add(topicID);
        mClientManager.JoinTopic(topicID);
    }//GEN-LAST:event_btnJoinTopicActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        mClientManager.GetTopicList();
        this.setTitle("Welcome, " + mClientManager.mUsername);
    }//GEN-LAST:event_formWindowOpened

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        mClientManager.Logout();
        mClientManager.Dispose();
        mLogin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        mClientManager.Logout();
        mClientManager.Dispose();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void txtTopicNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTopicNameKeyTyped
        // TODO add your handling code here:
        String after = txtTopicName.getText() + evt.getKeyChar();
        after = after.toLowerCase();
        if (after.contains("<row>") || after.contains("<col>"))  //cannot use <row> or <col> in topic name
            evt.consume();
    }//GEN-LAST:event_txtTopicNameKeyTyped

    @Override
    public void update(Observable o, Object arg) {
        // disable 2 line to allow input and auto set name

        txtTopicName.setText(mClientManager.mUsername);
        txtTopicName.setEditable(false);
        joinedList.clear(); // clear list joined
        List<String> joiedList = new ArrayList<String>();
        btnRefresh.setEnabled(true);
        Result result = (Result) arg;
        if (result.mResultCode.equals(ResultCode.ERROR)) {
            JOptionPane.showMessageDialog(null, result.mContent, "Failed", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return;
        }
        switch (result.mActionType) {
            case ActionType.GET_LIST_TOPIC: {
                FillTopicList(result);
                break;
            }
            case ActionType.CREATE_TOPIC: {
                mClientManager.deleteObservers();
                TopicConversation topicConversation = new TopicConversation(this, mClientManager, result.mContent, txtTopicName.getText().trim(), 1);
                topicConversation.setVisible(true);
                this.setVisible(false);
                break;
            }
            case ActionType.JOIN_TOPIC: {
                int indexRow = tableTopicList.getSelectedRow();
                if (indexRow < 0) {
                    JOptionPane.showMessageDialog(null, "Choose a topic", "Failed", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String topicID = tableTopicList.getValueAt(indexRow, 0).toString();
                String topicName = tableTopicList.getValueAt(indexRow, 1).toString();
                if (joiedList.contains(topicID)) {
                    JOptionPane.showMessageDialog(null, "You have joined this topic", "Failed", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                joiedList.add(topicID);
                int memsOfTopic = Integer.parseInt(tableTopicList.getValueAt(indexRow, 2).toString());
                mClientManager.deleteObserver(this);
                TopicConversation topicConversation = new TopicConversation(this, mClientManager, topicID, topicName, memsOfTopic + 1);
                topicConversation.setVisible(true);
                btnRefresh.setEnabled(false);
//                this.setVisible(false);
                break;
            }
        }
    }
    // End of variables declaration//GEN-END:variables
}
