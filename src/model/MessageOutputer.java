/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.Timer;

/**
 *
 * @author Jim
 */
public class MessageOutputer {

    private String host, user, password;
    private String apiKey, apiSecret;
    private Database db = null;
    private int lastMessageSended = -1;

    public MessageOutputer(Database db, String emailSMTPServer, String emailUsername, String emailPassword, String smsApiKey, String smsApiSecret) {
        this.host = emailSMTPServer;
        this.user = emailUsername;
        this.password = emailPassword;

        this.apiKey = smsApiKey;
        this.apiSecret = smsApiSecret;
        this.db = db;
    }

    public MessageOutputer() {
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public void saveToFile(ArrayList<Message> alMessages) {
        for (Message alMessage : alMessages) {
            String path = alMessage.textFile.path;
            File file = new File(path);
            if (file.exists()) {
                try (PrintWriter out = new PrintWriter(new FileOutputStream(path, true))) {
                    out.append(alMessage.toString(alMessage.language));
                    out.println();
                } catch (IOException ex) {
                    Logger.getLogger(MessageOutputer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                    out.print(alMessage.toString(alMessage.language));
                    out.println();
                } catch (IOException ex) {
                    Logger.getLogger(MessageOutputer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void sendMail(ArrayList<Message> alMessages) {

        //Get the session object  
        Properties props = new Properties();
        //       props.put("mail.smtp.starttls.enable", "true");  
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        for (Message alMessage : alMessages) {

            //Compose the message  
            try {
                MimeMessage email = new MimeMessage(session);
                email.setFrom(new InternetAddress(user));
                email.addRecipient(MimeMessage.RecipientType.TO,
                        new InternetAddress(alMessage.emailRecipient.address));
                email.setSubject("Messagehandler message");
                email.setText(alMessage.toString(alMessage.language));

                //send the email  
                Transport.send(email);

                System.out.println("email sent successfully...");

            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendSms(ArrayList<Message> alMessages) {
        for (Message alMessage : alMessages) {
            try {
                String url = "https://rest.nexmo.com/sms/json";
                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                //add reuqest header
                con.setRequestMethod("POST");

                String urlParameters = "api_key=" + apiKey + "&api_secret="
                        + apiSecret + "&to=" + alMessage.smsRecipient.number
                        + "&from=\"MsgHandler\"&text="
                        + alMessage.toString(alMessage.language);

                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());
            } catch (MalformedURLException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void doJob() {
        System.out.println("should do job");
        Runnable messageSender = new Runnable() {
            @Override
            public void run() {
                System.out.println("started!!!!");
                Timer timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("running");
                        db.getMessages();
                        Collections.sort(db.alMessages);
                        int i = lastMessageSended;
                        while (db.alMessages.get(i).id <= lastMessageSended) {
                            i++;
                        }
                        List<Message> alMessagesToBeSent = db.alMessages.subList(i, db.alMessages.size());

                        ArrayList<Message> alEmails = new ArrayList<>();
                        ArrayList<Message> alSms = new ArrayList<>();
                        ArrayList<Message> alTextFiles = new ArrayList<>();

                        for (Message message : alMessagesToBeSent) {
                            if (message.emailRecipient != null) {
                                alEmails.add(message);
                            }
                            if (message.textFile != null) {
                                alTextFiles.add(message);
                            }
                            if (message.smsRecipient != null) {
                                alSms.add(message);
                            }
                        }
                        lastMessageSended = alMessagesToBeSent.get(alMessagesToBeSent.size() - 1).id;
                        sendMail(alEmails);
                        sendSms(alSms);
                        saveToFile(alTextFiles);
                    }
                });
                timer.start();
            }
        };
        messageSender.run();
    }

    public String toString(ArrayList<Message> alMessages) {
        String result = "";
        for (Message alMessage : alMessages) {
            result += alMessage.toString(alMessage.language) + "\n";
        }
        return result;
    }
}
