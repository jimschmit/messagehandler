/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import model.Database;

/**
 *
 * @author jim
 */
public class EmailPanel extends javax.swing.JPanel {
    
    
    private Database db = null;
    private EmailOptionsPanel optionsPanel = null;
    /**
     * Creates new form UserPanel
     */
    public EmailPanel(Database db) {
        initComponents();
        this.db = db;
        db.getEmails();
        fillList();
    }
    
    public void fillList(){
        emailList.setListData(db.toEmailArray());
    }
    public void setDatabase(Database db)
    {
        this.db = db;
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

        addEmailButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        emailList = new javax.swing.JList<String>();

        setBackground(new java.awt.Color(204, 255, 255));

        addEmailButton.setText("add");
        addEmailButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addEmailButtonActionPerformed(evt);
            }
        });

        emailList.setPreferredSize(null);
        emailList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                emailListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(emailList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(426, 426, 426)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addGap(414, 414, 414))
            .addGroup(layout.createSequentialGroup()
                .addGap(598, 598, 598)
                .addComponent(addEmailButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addEmailButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addEmailButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addEmailButtonActionPerformed
    {//GEN-HEADEREND:event_addEmailButtonActionPerformed
        NewEmailPanel emailPanel = new NewEmailPanel();
        int result = JOptionPane.showConfirmDialog(this, emailPanel, "Add new email", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION)
        {
            db.addEmail(emailPanel.getEmail(), emailPanel.getName());
        }
        emailList.setListData(db.toEmailArray());
    }//GEN-LAST:event_addEmailButtonActionPerformed

    private void emailListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_emailListValueChanged
    {//GEN-HEADEREND:event_emailListValueChanged
        optionsPanel = new EmailOptionsPanel(db, db.alEmails.get(emailList.getSelectedIndex()), emailList);
        JOptionPane.showMessageDialog(this, optionsPanel, "Email", JOptionPane.DEFAULT_OPTION);
    }//GEN-LAST:event_emailListValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEmailButton;
    private javax.swing.JList<String> emailList;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
