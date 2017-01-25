/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Jim
 */
public class StartUpPanel extends javax.swing.JPanel {

    /**
     * Creates new form startUpPanel
     */
    public StartUpPanel()
    {
        initComponents();
        setVisible(true);
        setEnabled(true);
    }

    public JLabel getBindPortLabel()
    {
        return bindPortLabel;
    }

    public JTextField getBindPortTextField()
    {
        return bindPortTextField;
    }

    public JLabel getDatabaseLabel()
    {
        return databaseLabel;
    }

    public JTextField getDatabaseTextField()
    {
        return databaseTextField;
    }

    public JLabel getDbPasswordLabel()
    {
        return dbPasswordLabel;
    }

    public JTextField getDbPasswordTextField()
    {
        return dbPasswordTextField;
    }

    public JLabel getDbUsernameLabel()
    {
        return dbUsernameLabel;
    }

    public JTextField getDbUsernameTextField()
    {
        return dbUsernameTextField;
    }

    public JLabel getHostLabel()
    {
        return hostLabel;
    }

    public JTextField getHostTextField()
    {
        return hostTextField;
    }

    public JRadioButton getNoButton()
    {
        return noButton;
    }

    public JLabel getRemoteHostLabel()
    {
        return remoteHostLabel;
    }

    public JTextField getRemoteHostTextField()
    {
        return remoteHostTextField;
    }

    public JLabel getSshPasswordLabel()
    {
        return sshPasswordLabel;
    }

    public JTextField getSshPasswordTextField()
    {
        return sshPasswordTextField;
    }

    public JLabel getSshPortLabel()
    {
        return sshPortLabel;
    }

    public JTextField getSshPortTextField()
    {
        return sshPortTextField;
    }

    public ButtonGroup getSshRadioGroup()
    {
        return sshRadioGroup;
    }

    public JLabel getSshUsernameLabel()
    {
        return sshUsernameLabel;
    }

    public JTextField getSshUsernameTextField()
    {
        return sshUsernameTextField;
    }

    public JRadioButton getYesButton()
    {
        return yesButton;
    }

    public JTextField getPortTextField()
    {
        return portTextField;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        sshRadioGroup = new javax.swing.ButtonGroup();
        yesButton = new javax.swing.JRadioButton();
        noButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        hostLabel = new javax.swing.JLabel();
        dbUsernameLabel = new javax.swing.JLabel();
        dbPasswordLabel = new javax.swing.JLabel();
        databaseLabel = new javax.swing.JLabel();
        hostTextField = new javax.swing.JTextField();
        dbUsernameTextField = new javax.swing.JTextField();
        dbPasswordTextField = new javax.swing.JTextField();
        databaseTextField = new javax.swing.JTextField();
        remoteHostLabel = new javax.swing.JLabel();
        sshUsernameLabel = new javax.swing.JLabel();
        sshPasswordLabel = new javax.swing.JLabel();
        sshPortLabel = new javax.swing.JLabel();
        bindPortLabel = new javax.swing.JLabel();
        remoteHostTextField = new javax.swing.JTextField();
        sshUsernameTextField = new javax.swing.JTextField();
        sshPasswordTextField = new javax.swing.JTextField();
        sshPortTextField = new javax.swing.JTextField();
        bindPortTextField = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();
        portTextField = new javax.swing.JTextField();

        sshRadioGroup.add(yesButton);
        yesButton.setText("yes");
        yesButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                yesButtonActionPerformed(evt);
            }
        });

        sshRadioGroup.add(noButton);
        noButton.setSelected(true);
        noButton.setText("no");

        jLabel1.setText("Use SSH:");

        hostLabel.setText("host:");

        dbUsernameLabel.setText("dbUsername:");

        dbPasswordLabel.setText("dbPassword:");

        databaseLabel.setText("database:");

        remoteHostLabel.setText("remote host:");

        sshUsernameLabel.setText("ssh username:");

        sshPasswordLabel.setText("ssh password:");

        sshPortLabel.setText("ssh port:");

        bindPortLabel.setText("bind port to:");

        portLabel.setText("port:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(yesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(noButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hostLabel)
                            .addComponent(dbUsernameLabel)
                            .addComponent(dbPasswordLabel)
                            .addComponent(databaseLabel)
                            .addComponent(portLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(hostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dbUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(81, 81, 81)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(sshUsernameLabel)
                                    .addComponent(remoteHostLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(remoteHostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sshUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dbPasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(81, 81, 81)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(sshPortLabel)
                                            .addComponent(sshPasswordLabel)
                                            .addComponent(bindPortLabel)))
                                    .addComponent(databaseTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sshPasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sshPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bindPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yesButton)
                    .addComponent(noButton)
                    .addComponent(jLabel1))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hostLabel)
                    .addComponent(hostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remoteHostLabel)
                    .addComponent(remoteHostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbUsernameLabel)
                    .addComponent(dbUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sshUsernameLabel)
                    .addComponent(sshUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbPasswordLabel)
                    .addComponent(dbPasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sshPasswordLabel)
                    .addComponent(sshPasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(databaseLabel)
                    .addComponent(databaseTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sshPortLabel)
                    .addComponent(sshPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bindPortLabel)
                    .addComponent(bindPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portLabel)
                    .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void yesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_yesButtonActionPerformed
    {//GEN-HEADEREND:event_yesButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yesButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bindPortLabel;
    private javax.swing.JTextField bindPortTextField;
    private javax.swing.JLabel databaseLabel;
    private javax.swing.JTextField databaseTextField;
    private javax.swing.JLabel dbPasswordLabel;
    private javax.swing.JTextField dbPasswordTextField;
    private javax.swing.JLabel dbUsernameLabel;
    private javax.swing.JTextField dbUsernameTextField;
    private javax.swing.JLabel hostLabel;
    private javax.swing.JTextField hostTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton noButton;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portTextField;
    private javax.swing.JLabel remoteHostLabel;
    private javax.swing.JTextField remoteHostTextField;
    private javax.swing.JLabel sshPasswordLabel;
    private javax.swing.JTextField sshPasswordTextField;
    private javax.swing.JLabel sshPortLabel;
    private javax.swing.JTextField sshPortTextField;
    private javax.swing.ButtonGroup sshRadioGroup;
    private javax.swing.JLabel sshUsernameLabel;
    private javax.swing.JTextField sshUsernameTextField;
    private javax.swing.JRadioButton yesButton;
    // End of variables declaration//GEN-END:variables
}