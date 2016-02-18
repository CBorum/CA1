/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Borum
 */
public class ClientGUI extends javax.swing.JFrame implements Observer {

    private static String ip;
    private static int port;
    private ChatClient cc = new ChatClient();
    private String username;
    private DefaultListModel<String> usersIn;

    /**
     * Creates new form ClientGUI
     */
    public ClientGUI() {
        usersIn = new DefaultListModel<String>();
        initComponents();
        while (username == null) {
            username = JOptionPane.showInputDialog("Enter username");
        }
        try {
            usernameLabel.setText(username);
//            System.out.println("ip: " + ip + ", port:" + port);
            cc.connect("13.69.255.236", 9090);
            cc.send("USER#" + username);
            cc.addObserver(this);
            recievedTextArea.append("Connected");
            new Thread(cc).start();
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        recievedTextArea = new javax.swing.JTextArea();
        textInputField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        logoutBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        usersList = new javax.swing.JList<>(usersIn);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        recievedTextArea.setEditable(false);
        recievedTextArea.setColumns(20);
        recievedTextArea.setLineWrap(true);
        recievedTextArea.setRows(5);
        recievedTextArea.setFocusable(false);
        jScrollPane1.setViewportView(recievedTextArea);

        textInputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textInputFieldKeyReleased(evt);
            }
        });

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        logoutBtn.setText("Logout");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(usernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutBtn))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(logoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addComponent(usernameLabel))
        );

        jScrollPane3.setViewportView(usersList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(textInputField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textInputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        actionSendMessage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void textInputFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textInputFieldKeyReleased
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            actionSendMessage();
        }
    }//GEN-LAST:event_textInputFieldKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            cc.stop();
        } catch (NullPointerException ex) {
            setVisible(false);
            dispose();
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed

        cc.stop();
        setVisible(false);
        dispose();
    }//GEN-LAST:event_logoutBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JTextArea recievedTextArea;
    private javax.swing.JTextField textInputField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JList<String> usersList;
    // End of variables declaration//GEN-END:variables

    public void actionSendMessage() {
//        if (textInputField.getText().equals("LOGOUT#")) {
//            cc.stop();
//            setVisible(false);
//            dispose();
//        } else if (!textInputField.getText().trim().isEmpty()) {
//            recievedTextArea.append("\n" + textInputField.getText()); //for testing
//            cc.send(textInputField.getText());
//            textInputField.setText("");
//        }
        int[] usersInt = usersList.getSelectedIndices();
        String sendString = "SEND#";
        System.out.println(usersInt);
        if (usersInt.length == 1 && usersIn.get(usersInt[0]).equals("All")) {
            sendString += "*";
        } else {
            for (int i = 0; i < usersInt.length; i++) {
                String userName = usersIn.get(usersInt[i]);
                if (!userName.equals("All")) {
                    sendString += userName + ',';
                }
            }
            sendString = sendString.substring(0, sendString.length() - 1);
        }
        
        sendString += '#' + textInputField.getText();
        cc.send(sendString);
        
        textInputField.setText("");
        //System.out.println(users);
//        recievedTextArea.append("\n" + textInputField.getText()); //for testing
//        cc.send(textInputField.getText());
//        textInputField.setText("");
    }

    @Override
    public void update(Observable o, Object arg) {
        String returnedMessage = (String) arg;
//        recievedTextArea.append("\n" + returnedMessage);
//        recievedTextArea.setCaretPosition(recievedTextArea.getDocument().getLength());
        
        String[] msg = returnedMessage.split("#");
        switch (msg[0]) {
            case "USERS":
                String[] users = msg[1].split(",");
                System.out.println(msg[1]);
                usersIn.clear();
                usersIn.addElement("All");
                usersList.setSelectedIndex(0);
                for (String user : users) {
                    //if username == this username
                    usersIn.addElement(user);
                }
                break;
            case "MESSAGE":
                recievedTextArea.append("\n" + msg[1] + ": " + msg[2]);
                recievedTextArea.setCaretPosition(recievedTextArea.getDocument().getLength());
                break;
        }
    }
}
