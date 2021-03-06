/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JOptionPane;
import model.Database;

/**
 *
 * @author Jim
 */
public class LanguagePanel extends javax.swing.JPanel
{

    private Database db = null;
    private LanguageOptionsPanel optionsPanel = null;

    /**
     * Creates new form UserPanel
     *
     * @param db
     */
    public LanguagePanel(Database db)
    {
        initComponents();
        this.db = db;
        db.getLanguages();
        System.out.println(db.alLanguages);
        fillList();
    }
    
    
    public void fillList(){
        languageList.setListData(db.toLanguageArray());
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
    private void initComponents() {

        addLanguageButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        languageList = new javax.swing.JList<>();

        setBackground(new java.awt.Color(204, 255, 255));
        setPreferredSize(new java.awt.Dimension(1200, 500));

        addLanguageButton.setText("add");
        addLanguageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLanguageButtonActionPerformed(evt);
            }
        });

        languageList.setPreferredSize(null);
        languageList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                languageListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(languageList);

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
                .addComponent(addLanguageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addLanguageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addLanguageButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addLanguageButtonActionPerformed
    {//GEN-HEADEREND:event_addLanguageButtonActionPerformed
        String description = JOptionPane.showInputDialog("Insert your desired description..");
        db.addLanguage(description);
        languageList.setListData(db.toLanguageArray());
    }//GEN-LAST:event_addLanguageButtonActionPerformed

    private void languageListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_languageListValueChanged
        System.out.println(db.alLanguages);
        optionsPanel = new LanguageOptionsPanel(db, db.alLanguages.get(languageList.getSelectedIndex()).id, languageList);
        JOptionPane.showMessageDialog(this, optionsPanel, "Language", JOptionPane.DEFAULT_OPTION);
    }//GEN-LAST:event_languageListValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addLanguageButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> languageList;
    // End of variables declaration//GEN-END:variables
}
