/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Jim
 */
public class Message extends Table implements Comparable<Message>{
    private User sender;
    private ErrorCode errorCode;
    private String timestamp;
    private MessageType messageType;
    private String param1, param2, param3;
    public Language language;
    public Email emailRecipient;
    public Sms smsRecipient;
    public TextFile textFile;

    public Message(User sender, ErrorCode errorCode, String timestamp, MessageType messageType, Language language, Email emailRecipient, Sms smsRecipient, TextFile textFile, int id, String param1, String param2, String param3)
    {
        super(id);
        this.sender = sender;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
        this.messageType = messageType;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.emailRecipient = emailRecipient;
        this.smsRecipient = smsRecipient;
        this.language = language;
        this.textFile = textFile;
    }
    
    public String replacePlaceholders(String target, String param1, String param2, String param3){
        int placeholders = 0;
                
                while(target.contains("$p$") && placeholders <= 2){
                    switch(placeholders){
                        case 0: target = target.replaceFirst("\\$p\\$", param1);
                                break;
                        case 1: target = target.replaceFirst("\\$p\\$", param2);
                                break;
                        case 2: target = target.replaceFirst("\\$p\\$", param3);
                                break;
                        default: break;
                    }
                    placeholders++;
                }
        
        return target;
    }
    
    public String toString(Language language){
        return id + " " + timestamp + " " + sender + " " + 
                messageType.toString(language) + ": " +
                replacePlaceholders(errorCode.toString(language),
                param1, param2, param3);
    }

    @Override
    public int compareTo(Message o) {
        if(this.id > o.id) return 1;
        else if(this.id == o.id) return 0;
        else return -1;
    }
    
    
    
    
    
}
