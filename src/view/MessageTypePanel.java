/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.sun.java.accessibility.util.AWTEventMonitor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Database;
import model.Language;

/**
 *
 * @author Jim
 */
public class MessageTypePanel extends javax.swing.JPanel
{

    private Database db = null;
    private MessageTypeOptionsPanel optionsPanel = null;

    /**
     * Creates new form UserPanel
     *
     * @param db
     */
    public MessageTypePanel(Database db)
    {
        initComponents();
        this.db = db;
        db.getMessageTypes();
        fillList();
    }
    
    
    public void fillList(){
        messageTypeList.setListData(db.toMessageTypeArray());
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

        addMessageTypeButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTypeList = new javax.swing.JList<String>();

        setBackground(new java.awt.Color(204, 255, 255));
        setPreferredSize(new java.awt.Dimension(1200, 500));

        addMessageTypeButton.setText("add");
        addMessageTypeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addMessageTypeButtonActionPerformed(evt);
            }
        });

        messageTypeList.setPreferredSize(null);
        messageTypeList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                messageTypeListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(messageTypeList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(426, 426, 426)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addGap(424, 424, 424))
            .addGroup(layout.createSequentialGroup()
                .addGap(574, 574, 574)
                .addComponent(addMessageTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addMessageTypeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addMessageTypeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addMessageTypeButtonActionPerformed
    {//GEN-HEADEREND:event_addMessageTypeButtonActionPerformed
        NewMessageTypePanel newMessageTypePanel = new NewMessageTypePanel(db);
        int result = JOptionPane.showConfirmDialog(this, newMessageTypePanel, "Add new messagetype", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) db.addMessageType(newMessageTypePanel.getFiLanguage(), newMessageTypePanel.getDescriptionTextField().getText());
        messageTypeList.setListData(db.toMessageTypeArray());
    }//GEN-LAST:event_addMessageTypeButtonActionPerformed

    private void messageTypeListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_messageTypeListValueChanged
        try{
            optionsPanel = new MessageTypeOptionsPanel(db, db.alMessageTypes.get(messageTypeList.getSelectedIndex()), messageTypeList);
            JOptionPane.showMessageDialog(this, optionsPanel, "Message type", JOptionPane.DEFAULT_OPTION);
        }
        catch(IndexOutOfBoundsException e){
            
        }
    }//GEN-LAST:event_messageTypeListValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMessageTypeButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> messageTypeList;
    // End of variables declaration//GEN-END:variables
}
