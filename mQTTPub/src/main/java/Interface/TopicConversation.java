/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Core.ActionType;
import Core.ClientManager;
import Core.Result;
import Core.ResultCode;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class TopicConversation extends javax.swing.JFrame implements Observer {

    /**
     * Creates new form TopicConversation
     */
    ClientManager mClientManager;
    String mTopicID = "";
    String mTopicName = "";
    TopicList mTopicList;
    Login mLogin;
    int mMemsOfTopic = 1;
    Random random = new Random();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - huy
    private JScrollPane jScrollPane1;
    private JTextArea txtContentConversation;
    private JScrollPane jScrollPane2;
    private JTextArea txtMessToSend;
    private JButton btnSend;
    private JButton btnLogout;

    public TopicConversation(TopicList topicList, ClientManager cm, String topicID, String topicName, int memsOfTopic) {
        initComponents();
        mTopicList = topicList;
        mClientManager = cm;
        mTopicID = topicID;
        mTopicName = topicName;
        mMemsOfTopic = memsOfTopic;
        mClientManager.addObserver(this);
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
        txtContentConversation = new JTextArea();
        jScrollPane2 = new JScrollPane();
        txtMessToSend = new JTextArea();
        btnSend = new JButton();
        btnLogout = new JButton();
        txtMessToSend.setText("Hello");
        txtMessToSend.setEditable(false);

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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

        //======== jScrollPanehttps://stackhowto.com/wp-content/uploads/2021/08/how-to-disable-jtextarea-and-jtextfield-1.png1 ========
        {

            //---- txtContentConversation ----
            txtContentConversation.setEditable(false);
            txtContentConversation.setColumns(20);
            txtContentConversation.setRows(5);
            jScrollPane1.setViewportView(txtContentConversation);
        }

        //======== jScrollPane2 ========
        {

            //---- txtMessToSend ----
            txtMessToSend.setColumns(20);
            txtMessToSend.setRows(5);
            jScrollPane2.setViewportView(txtMessToSend);
        }

        //---- btnSend ----
        btnSend.setText("Send");
        btnSend.addActionListener(e -> btnSendActionPerformed(e));

        //---- btnLogout ----
        btnLogout.setText("Logout");
        btnLogout.addActionListener(e -> btnLogoutActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(jScrollPane1)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(jScrollPane2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(556, 556, 556)
                                                .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setTitle("Username: " + mClientManager.mUsername + "      Topic name: " + mTopicName + "     Topic ID: " + mTopicID + "     Member(s): " + mMemsOfTopic);

    }//GEN-LAST:event_formWindowOpened

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        btnSend.setEnabled(false);
        GenData genData = new GenData();
        genData.start();

    }//GEN-LAST:event_btnSendActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        mClientManager.LeaveTopic();
        mClientManager.deleteObserver(this);
        mClientManager.addObserver(mTopicList);
        mTopicList.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        mClientManager.LeaveTopic();
        mClientManager.deleteObserver(this);
        mClientManager.addObserver(mTopicList);
//        mTopicList.setVisible(true);

        mClientManager.Logout();
        mClientManager.Dispose();
//        mTopicList.dispose();
        System.exit(0);

    }//GEN-LAST:event_btnLogoutActionPerformed

    @Override
    public void update(Observable o, Object arg) {
        Result result = (Result) arg;

        if (result.mResultCode.equals(ResultCode.ERROR)) {
            JOptionPane.showMessageDialog(null, result.mContent, "Failed", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return;
        }
        switch (result.mActionType) {
            case ActionType.SEND_MESSAGE: {
                String[] lines = result.mContent.split(";", -1);
                String sender = lines[0];
                String messContent = lines[1];
                messContent = messContent.replaceAll("<br>", "\n");
                if (sender.equals(mClientManager.mUsername))
                    txtContentConversation.append("You: " + messContent + "\n");
                else
                    txtContentConversation.append(sender + ": " + messContent + "\n");
                break;
            }
            case ActionType.UPDATE_NUMBER_USER: {
                String memsOfTopic = result.mContent;
                mMemsOfTopic = Integer.parseInt(memsOfTopic);
                this.setTitle("Username: " + mClientManager.mUsername + "      Topic name: " + mTopicName + "     ID: " + mTopicID + "     Member(s): " + mMemsOfTopic);
                break;
            }
            case ActionType.NOTIFY_JUST_JOIN_TOPIC: {
                String userJoin = result.mContent;
                txtContentConversation.append("<" + userJoin + "> joined\n");
                break;
            }
            case ActionType.NOTIFY_JUST_LEAVE_TOPIC: {
                String userJoin = result.mContent;
                txtContentConversation.append("<" + userJoin + "> left\n");
                break;
            }
        }
    }

    //Auto gen data and send to broker(btnSend action)
    public class GenData extends Thread {
        public void run() {
            if (txtMessToSend.getText().trim().length() == 0)
                return;

            for (; ; ) {
                mClientManager.SendMess(txtMessToSend.getText().trim());
                // Random data
                Random rd = new Random();
                int iData = rd.nextInt(101);    // tr??? v??? 1 s??? nguy??n n???m trong ph???m vi [0...101]  (gi??? s??? tr?????ng h???p publiser g???i l??n nhi???t ?????)
                String Data = String.valueOf(iData);
                txtMessToSend.setText(Data);

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    // End of variables declaration//GEN-END:variables

}