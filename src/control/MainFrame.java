/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import model.Database;
import model.MessageFetcher;
import model.MessageOutputer;
import view.EmailFrame;
import view.EmailPanel;
import view.ErrorCodePanel;
import view.FilePanel;
import view.LanguagePanel;
import view.MessagePanel;
import view.MessageTypePanel;
import view.SmsFrame;
import view.SmsPanel;
import view.StartUpPanel;
import view.UserPanel;

/**
 *
 * @author Jim
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private StartUpPanel startUpPanel;
    private LoginFrame loginFrame;
    private LanguagePanel languagePanel;
    private UserPanel userPanel;
    private ErrorCodePanel errorPanel;
    private MessageTypePanel messageTypePanel;
    private MessagePanel messagePanel;
    private EmailPanel emailPanel;
    private SmsPanel smsPanel;
    private FilePanel filePanel;
    private EmailFrame emailSettingsFrame = new EmailFrame();
    private SmsFrame smsSettingsFrame = new SmsFrame();
    private Database db;
    
    MessageOutputer outputer = null;
//    private String host, user, password, recipient;
//    private String apiKey, apiSecret;

    public MainFrame()
    {
        initComponents();
        this.setLocationRelativeTo(null);
        loginFrame = new LoginFrame();
        loginFrame.setMainFrame(this);
        addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                db.logout();
            }
            
});
        
//        StartUpPanel = new StartUpPanel();
//        if (JOptionPane.showConfirmDialog(this, StartUpPanel, "Initialize", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
//        {
//            String host = StartUpPanel.getHostTextField().getText();
//            String dbUser = StartUpPanel.getDbUsernameTextField().getText();
//            String dbPassword = StartUpPanel.getDbPasswordTextField().getText();
//            String database = StartUpPanel.getDatabaseTextField().getText();
//            int port = 3306;
//            if(StartUpPanel.getPortTextField().getText()!= ""){
//                port = Integer.valueOf(StartUpPanel.getPortTextField().getText());
//            }
//
//            String remoteHost = StartUpPanel.getRemoteHostTextField().getText();
//            String sshUsername = StartUpPanel.getSshUsernameTextField().getText();
//            String sshPassword = StartUpPanel.getSshPasswordTextField().getText();
//            int sshPort = Integer.valueOf(StartUpPanel.getSshPortTextField().getText());
//            int bindPort = Integer.valueOf(StartUpPanel.getBindPortTextField().getText());
//
//            if (StartUpPanel.getNoButton().isSelected())
//            {
//                if (host != "" && dbUser != "" && dbPassword != "" && database != "")
//                {
//                    db = new Database(dbUser, host, port, dbPassword, database);
//                }
//
//            }
//            else{
//                if(host != "" && dbUser != "" && dbPassword != "" && database != ""
//                        && remoteHost != "" && sshUsername != "" && sshPassword != ""){
//                    try
//                    {
//                        db = new Database(host, sshUsername, sshPassword, sshPort, remoteHost, bindPort, dbUser, dbPassword, database, port);
//                    } catch (JSchException ex)
//                    {
//                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        };
    }
    
    public void init(){
        languagePanel = new LanguagePanel(db);
        userPanel = new UserPanel(db);
        errorPanel = new ErrorCodePanel(db);
        messageTypePanel = new MessageTypePanel(db);
        messagePanel = new MessagePanel(db, smsSettingsFrame, emailSettingsFrame, outputer);
        emailPanel = new EmailPanel(db);
        smsPanel = new SmsPanel(db);
        filePanel = new FilePanel(db);
        
        tabbedPane.addTab("Languages", languagePanel);
        tabbedPane.addTab("Users", userPanel);
        tabbedPane.addTab("Errorcodes", errorPanel);
        tabbedPane.addTab("Messagetypes", messageTypePanel);
        tabbedPane.addTab("Messages", messagePanel);
        tabbedPane.addTab("Emails", emailPanel);
        tabbedPane.addTab("Sms", smsPanel);
        tabbedPane.addTab("Files", filePanel);
        outputer = new MessageOutputer();
        outputer.doJob();
    }
    
    public void setDb(Database db){
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

        jMenuItem1 = new javax.swing.JMenuItem();
        tabbedPane = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        closeMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        smsMenuItem = new javax.swing.JMenuItem();
        emailMenuItem = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tabbedPane.setPreferredSize(new java.awt.Dimension(1200, 500));
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        FileMenu.setText("File");

        closeMenuItem.setText("Close");
        FileMenu.add(closeMenuItem);

        jMenuBar1.add(FileMenu);

        settingsMenu.setText("Settings");

        smsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        smsMenuItem.setText("SMS");
        smsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsMenuItemActionPerformed(evt);
            }
        });
        settingsMenu.add(smsMenuItem);

        emailMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        emailMenuItem.setText("Email");
        emailMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailMenuItemActionPerformed(evt);
            }
        });
        settingsMenu.add(emailMenuItem);

        jMenuBar1.add(settingsMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_tabbedPaneStateChanged
    {//GEN-HEADEREND:event_tabbedPaneStateChanged
        if(tabbedPane.getSelectedComponent() == messagePanel){
            MessageFetcher fetcher = new MessageFetcher(db, messagePanel.getMessageList());
            Thread thread = new Thread(fetcher);
            thread.start();
        }
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void smsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smsMenuItemActionPerformed
        smsSettingsFrame.setVisible(true);
        
    }//GEN-LAST:event_smsMenuItemActionPerformed

    private void emailMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailMenuItemActionPerformed
        emailSettingsFrame.setVisible(true);
    }//GEN-LAST:event_emailMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                new MainFrame().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JMenuItem emailMenuItem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JMenuItem smsMenuItem;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
