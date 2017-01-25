/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import model.Database;
import model.Email;
import model.ErrorCode;
import model.Language;
import model.MessageType;
import model.Sms;

/**
 *
 * @author jim
 */
public class NewMessagePanel extends javax.swing.JPanel {

    /**
     * Creates new form LanguageOptionsPanel
     */
    public NewMessagePanel(Database db) {
        initComponents();
        fillComboBoxes(db);
    }

    public void fillComboBoxes(Database db){
        
        emailComboBox.setEnabled(false);
        smsComboBox.setEnabled(false);
        
        errorCodeComboBox.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList list,
                                               Object value,
                                               int index,
                                               boolean isSelected,
                                               boolean cellHasFocus){
            ErrorCode errorCode = (ErrorCode)value;
            value = errorCode.toString(db.currentLanguage);
            return super.getListCellRendererComponent(list, value,
                    index, isSelected, cellHasFocus);
            }
        });
        
        typeComboBox.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList list,
                                               Object value,
                                               int index,
                                               boolean isSelected,
                                               boolean cellHasFocus){
            MessageType messageType = (MessageType) value;
            value = messageType.toString(db.currentLanguage);
            return super.getListCellRendererComponent(list, value,
                    index, isSelected, cellHasFocus);
            }
        });
        
        emailComboBox.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList list,
                                               Object value,
                                               int index,
                                               boolean isSelected,
                                               boolean cellHasFocus){
            Email email = (Email)value;
            value = email.toStringForComboBox();
            return super.getListCellRendererComponent(list, value,
                    index, isSelected, cellHasFocus);
            }
        });
        
        smsComboBox.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList list,
                                               Object value,
                                               int index,
                                               boolean isSelected,
                                               boolean cellHasFocus){
            Sms sms = (Sms)value;
            value = sms.toStringForComboBox();
            return super.getListCellRendererComponent(list, value,
                    index, isSelected, cellHasFocus);
            }
        });
        
        
        db.alErrorCodes.forEach((errorCode) -> {
            errorCodeComboBox.addItem(errorCode);
        });
        
        db.alMessageTypes.forEach((messageType) -> {
            typeComboBox.addItem(messageType);
        });
        
        db.alEmails.forEach((email) ->{
            emailComboBox.addItem(email);
        });
        
        db.alSms.forEach((sms) ->{
            smsComboBox.addItem(sms);
        });
        
        db.alTextFiles.forEach((fileName)->{
            fileComboBox.addItem(fileName);
        });
        
    }

    public JComboBox getErrorCodeComboBox()
    {
        return errorCodeComboBox;
    }

    public JComboBox getLanguageComboBox()
    {
        return languageComboBox;
    }

    public JTextField getParam1TextField()
    {
        return param1TextField;
    }

    public JTextField getParam2TextField()
    {
        return param2TextField;
    }

    public JTextField getParam3TextField()
    {
        return param3TextField;
    }

    public JComboBox getTypeComboBox()
    {
        return typeComboBox;
    }

    public JComboBox<String> getEmailComboBox() {
        return emailComboBox;
    }

    public JComboBox<String> getSmsComboBox() {
        return smsComboBox;
    }
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        languageComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        errorCodeComboBox = new javax.swing.JComboBox();
        param1TextField = new javax.swing.JTextField();
        param2TextField = new javax.swing.JTextField();
        param3TextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        previewButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        emailComboBox = new javax.swing.JComboBox();
        smsComboBox = new javax.swing.JComboBox();
        emailCheckBox = new javax.swing.JCheckBox();
        smsCheckBox = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        fileComboBox = new javax.swing.JComboBox();
        fileCheckBox = new javax.swing.JCheckBox();

        languageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("Language:");

        jLabel1.setText("Type:");

        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Errorcode:");

        errorCodeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                errorCodeComboBoxItemStateChanged(evt);
            }
        });

        jLabel4.setText("Parameter 1:");

        jLabel5.setText("Parameter 2:");

        jLabel6.setText("Parameter 3:");

        jLabel7.setText("Message:");

        previewButton.setText("Preview");
        previewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previewButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("Email:");

        jLabel9.setText("Sms:");

        emailComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailComboBoxActionPerformed(evt);
            }
        });

        emailCheckBox.setText("Send mail");
        emailCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailCheckBoxActionPerformed(evt);
            }
        });

        smsCheckBox.setText("Send sms");
        smsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsCheckBoxActionPerformed(evt);
            }
        });

        jLabel10.setText("File:");

        fileCheckBox.setText("Save to file");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                    .addComponent(jLabel3)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(jLabel10)))))
                        .addGap(18, 34, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(previewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeComboBox, 0, 185, Short.MAX_VALUE)
                            .addComponent(languageComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(errorCodeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(param2TextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(param1TextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(param3TextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(smsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fileComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailCheckBox)
                            .addComponent(smsCheckBox)
                            .addComponent(fileCheckBox))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(errorCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(emailComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(smsCheckBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9)
                        .addComponent(smsComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(fileComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileCheckBox))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(param1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addComponent(param2TextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(param3TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(previewButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_typeComboBoxActionPerformed
    {//GEN-HEADEREND:event_typeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void errorCodeComboBoxItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_errorCodeComboBoxItemStateChanged
    {//GEN-HEADEREND:event_errorCodeComboBoxItemStateChanged
        languageComboBox.removeAllItems();
        ErrorCode selectedErrorCode = (ErrorCode)errorCodeComboBox.getSelectedItem();
        ArrayList<Language> alLanguages = new ArrayList<>();
        
        for (Language language : selectedErrorCode.getAlDescriptions().keySet())
        {
            languageComboBox.addItem(language);
        }
        
        int paramCounter = selectedErrorCode.getAlDescriptions().values().iterator().next().split("\\$p\\$", -1).length-1;
        
        switch(paramCounter){
            case 0:  param1TextField.setEnabled(false);
                     param2TextField.setEnabled(false);
                     param3TextField.setEnabled(false);
                     break;
            case 1:  param1TextField.setEnabled(true);
                     param2TextField.setEnabled(false);
                     param3TextField.setEnabled(false);
                     break;
            case 2:  param1TextField.setEnabled(true);
                     param2TextField.setEnabled(true);
                     param3TextField.setEnabled(false);
                     break;
            default: param1TextField.setEnabled(true);
                     param2TextField.setEnabled(true);
                     param3TextField.setEnabled(true);
                     break;
        }
    }//GEN-LAST:event_errorCodeComboBoxItemStateChanged

    private void languageComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_languageComboBoxActionPerformed
    {//GEN-HEADEREND:event_languageComboBoxActionPerformed
    }//GEN-LAST:event_languageComboBoxActionPerformed

    private void previewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_previewButtonActionPerformed
    {//GEN-HEADEREND:event_previewButtonActionPerformed
        if(typeComboBox.getSelectedItem() != null && 
           errorCodeComboBox.getSelectedItem() != null &&
           languageComboBox.getSelectedItem() != null){
            MessageType messageType = (MessageType)typeComboBox.getSelectedItem();
            ErrorCode errorCode = (ErrorCode)errorCodeComboBox.getSelectedItem();
            Language language = (Language)languageComboBox.getSelectedItem();

            String param1 = param1TextField.getText();
            String param2 = param2TextField.getText();
            String param3 = param3TextField.getText();

            String createDate = new Timestamp(System.currentTimeMillis()).toString();
            String errorCodeDescription = errorCode.toString(language);
            String type = messageType.toString(language);

            int placeholders = 0;

            while(errorCodeDescription.contains("$p$") && placeholders <= 2){
                switch(placeholders){
                    case 0: errorCodeDescription = errorCodeDescription.replaceFirst("\\$p\\$", param1);
                            break;
                    case 1: errorCodeDescription = errorCodeDescription.replaceFirst("\\$p\\$", param2);
                            break;
                    case 2: errorCodeDescription = errorCodeDescription.replaceFirst("\\$p\\$", param3);
                            break;
                    default: break;
                }
                placeholders++;
            }
            
            messageLabel.setText(createDate + " " +  type + ": " + errorCodeDescription);
        }
    }//GEN-LAST:event_previewButtonActionPerformed

    private void emailComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailComboBoxActionPerformed

    private void emailCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailCheckBoxActionPerformed
        if(emailCheckBox.isSelected()) emailComboBox.setEnabled(true);
        else emailComboBox.setEnabled(false);
    }//GEN-LAST:event_emailCheckBoxActionPerformed

    private void smsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smsCheckBoxActionPerformed
        if(smsCheckBox.isSelected()) smsComboBox.setEnabled(true);
        else smsComboBox.setEnabled(false);
    }//GEN-LAST:event_smsCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox emailCheckBox;
    private javax.swing.JComboBox emailComboBox;
    private javax.swing.JComboBox errorCodeComboBox;
    private javax.swing.JCheckBox fileCheckBox;
    private javax.swing.JComboBox fileComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox languageComboBox;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JTextField param1TextField;
    private javax.swing.JTextField param2TextField;
    private javax.swing.JTextField param3TextField;
    private javax.swing.JButton previewButton;
    private javax.swing.JCheckBox smsCheckBox;
    private javax.swing.JComboBox smsComboBox;
    private javax.swing.JComboBox typeComboBox;
    // End of variables declaration//GEN-END:variables
}
