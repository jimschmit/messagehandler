/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.JList;

/**
 *
 * @author Jim
 */
public class MessageFetcher implements Runnable{
    private Database db;
    private JList messageList;

    public MessageFetcher(Database db, JList messageList)
    {
        this.db = db;
        this.messageList = messageList;
    }
    
    

    @Override
    public void run()
    {
        db.getMessages();
        messageList.setListData(db.toMessageArray());
    }
    
}
