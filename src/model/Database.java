/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jim_Laptop
 */
public class Database
{
    private String host, dbUser, dbPassword, database;
    private int port;
    private Connection conn;
//    private ResultSet resultSet;
    private PreparedStatement sth;
    
    private String sshUsername;                   // SSH loging username
    private String sshPassword;                   // SSH login password
    private String sshHost;                       // hostname or ip or SSH server
    private int sshPort;                          // remote SSH host port number
    private String remoteHost;                    // hostname or ip of your database server
    private int localPortToRebind;                // local port number use to bind SSH tunnel
    
    
    public final static int ADMIN = 1;
    public final static int EDITER = 2;
    public final static int VIEWER = 3;
    public int currentUser = ADMIN;
    public Language currentLanguage = null;
    
    
    public ArrayList<Language> alLanguages = new ArrayList<>();
    public ArrayList<User> alUsers = new ArrayList<>();
    public ArrayList<ErrorCode> alErrorCodes = new ArrayList<>();
    public ArrayList<MessageType>alMessageTypes = new ArrayList<>();
    public ArrayList<Message>alMessages = new ArrayList<>();
    public ArrayList<Email>alEmails = new ArrayList<>();
    public ArrayList<Sms>alSms = new ArrayList<>();
    public ArrayList<TextFile>alTextFiles = new ArrayList<>();

    public Database(String user, String host, int port, String password, String database)
    {
        this.host = host;
        this.dbUser = user;
        this.dbPassword = password;
        this.port = port;
        this.database = database;
    }

    public Database(String sshHost, String sshUsername, String sshPassword, int sshPort, String remoteHost, int localPortToRebind, String dbUser, String dbPassword, String database, int port) throws JSchException
    {
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.database = database;
        this.sshUsername = sshUsername;
        this.sshPassword = sshPassword;
        this.sshHost = sshHost;
        this.sshPort = sshPort;
        this.remoteHost = remoteHost;
        this.localPortToRebind = localPortToRebind;
        
        this.host = remoteHost;
        this.port = localPortToRebind;
        
        
        if(dbUser == "mh_admin") currentUser = ADMIN;
        else if(dbUser == "mh_editer") currentUser = EDITER;
        else{
            if(dbUser == "mh_viewer") currentUser = VIEWER;
        }
        
        doSshTunnel(sshUsername, sshPassword, sshHost, sshPort, remoteHost, localPortToRebind, port);
    }
    
    
    
    public void connectToDatabase() throws ClassNotFoundException, SQLException{
        
        Class.forName("com.mysql.jdbc.Driver");  
        conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + localPortToRebind + "/" + database, dbUser, dbPassword);  
    }
    
    public void closeConnection(){
        try
        {
            conn.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeResultSet(){
        try
        {
            resultSet.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeStatement(){
        try
        {
            sth.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getCurrentUser()
    {
        return currentUser;
    }
    
    private static void doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
  {
    final JSch jsch = new JSch();
    Session session = jsch.getSession( strSshUser, strSshHost, 22 );
    session.setPassword( strSshPassword );
    final Properties config = new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig( config );
    session.connect();
    session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
  }
    
    public  void disconnectSsh() throws JSchException{
        final JSch jsch = new JSch();
        Session session = jsch.getSession(sshUsername, sshHost, 22 );
        session.setPassword( sshPassword );

        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig( config );
        
        session.disconnect();
        session.delPortForwardingR(sshHost, 3306);
        session.delPortForwardingL("localhost", 3366);
    }
    
    public boolean loginUser(String name, String password) throws JSchException{
        boolean result = false;
        try
        {   
            ResultSet resultSet;
            connectToDatabase();
            sth = conn.prepareStatement("SELECT idUser FROM tbluser WHERE dtUsername = '" + name + "' && dtPassword = '" + password + "';");
            resultSet = sth.executeQuery();
            if(resultSet.first() == false){
                result = false;
            }
            else{
                int id = resultSet.getInt("idUser");
                sth = conn.prepareStatement("UPDATE tbluser SET dtLoggedIn = TRUE WHERE idUser = " + id + ";");
                sth.executeUpdate();
                
                sth = conn.prepareStatement("SELECT (dtLevel) FROM tbluser WHERE idUser = " + id);
                resultSet = sth.executeQuery();
                resultSet.first();
                int level = resultSet.getInt("dtLevel");
                
                if(level == ADMIN) currentUser = ADMIN;
                else if(level == EDITER ) currentUser = EDITER;
                else currentUser = VIEWER;
                
                closeStatement();
                closeResultSet();
                closeConnection();
                result = true;
            }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void logout(){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("UPDATE tbluser SET dtLoggedIn = FALSE WHERE dtLoggedIn = TRUE");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    //----------------------Languages-------------------------------------------------------
    public void addLanguage(String description){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_insertLanguage(" + "\"" 
                    + description + "\", @status, @error)");
            sth.executeUpdate();
            sth = conn.prepareStatement("SELECT MAX(idLanguage) FROM tbllanguage");
            resultSet = sth.executeQuery();
            resultSet.first();
            alLanguages.add(new Language(resultSet.getInt(1), description));
            
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getLanguages(){
        ResultSet tempResultSet;
        try
        {
            connectToDatabase();
            alLanguages.clear();
            sth = conn.prepareStatement("SELECT * FROM tbllanguage;");
            tempResultSet = sth.executeQuery();
            tempResultSet.first();
            while(!tempResultSet.isAfterLast()){
                int id = tempResultSet.getInt("idLanguage");
                String description = tempResultSet.getString("dtDescription");
                alLanguages.add(new Language(id, description));
                tempResultSet.next();
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
     public Language getLanguage(int idLanguage){
        Language language = null;
        for (Language alLanguage : alLanguages)
        {
            if(alLanguage.id == idLanguage) language = alLanguage;
        }
        return language;
    }
     
    public int getFiLanguage(Language language){
       int result = -1;
       try
       {
           connectToDatabase();
           sth = conn.prepareStatement("SELECT (idLanguage) FROM tbllanguage WHERE dtDescription = '" + language.description + "'");
           resultSet = sth.executeQuery();
           resultSet.first();
           result =  resultSet.getInt("idLanguage");
       } catch (ClassNotFoundException ex)
       {
           Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex)
       {
           Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
       }
       return result;
   }
    
    public void updateLanguage(int id, String newDescription){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_updateLanguage(" + id + ", " 
                    + "\"" + newDescription + "\", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alLanguages.size()-1;
             while(!found && i >= 0){
                 if(alLanguages.get(i).id == id){
                     alLanguages.get(i).description = newDescription;
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteLanguage(int id)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteLanguage(" + id + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alLanguages.size()-1;
             while(!found && i >= 0){
                 if(alLanguages.get(i).id == id){
                    alLanguages.remove(i);
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String[] toLanguageArray(){
        String[] arResult = new String[alLanguages.size()];
        int i = 0;
        for (Language language: alLanguages) {
            arResult[i] = language.toString();
            i++;
        }
        
        return arResult;
    }
    
    public int getLastIdLanguage(){
        int id = -1;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("SELECT MAX(idLanguage) FROM tbllanguage");
            resultSet = sth.executeQuery();
            id = resultSet.getInt("0");
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
        return id;
    }
    
    //--------------------------------Users--------------------------------------------------
    public void addUser(String name, String password, int level)
    {
        int id;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_insertUser('" + name +"'" + ", " + "'" + password + "'" + ", " + level + ", @status, @error)" );
            sth.executeUpdate();
            sth = conn.prepareStatement("SELECT MAX(idUser) FROM tbluser");
            resultSet = sth.executeQuery();
            resultSet.first();
            id = resultSet.getInt(1);
            closeStatement();
            closeResultSet();
            sth = conn.prepareStatement("SELECT * FROM tbluser WHERE idUser = " + id);
            resultSet = sth.executeQuery();
            resultSet.first();
            Timestamp createDate = resultSet.getTimestamp("dtCreateDate");
            
            alUsers.add(new User(id, name, password, createDate, level));
            closeResultSet();
            closeStatement();
            closeConnection();
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getUsers(){
        ResultSet tempResultSet;
        try
        {
            connectToDatabase();
            alUsers.clear();
            sth = conn.prepareStatement("SELECT * FROM tbluser;");
            tempResultSet = sth.executeQuery();
            tempResultSet.first();
            while(!tempResultSet.isAfterLast()){
                int id = tempResultSet.getInt("idUser");
                String name = tempResultSet.getString("dtUsername");
                String password = tempResultSet.getString("dtPassword");
                Timestamp createDate = tempResultSet.getTimestamp("dtCreateDate");
                int level = tempResultSet.getInt("dtLevel");
                alUsers.add(new User(id, name, password, createDate, level));
                tempResultSet.next();
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
    public void updateUser(int id, String name, String password, int level){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_updateUser(" + id + ", " 
                    + "\"" + name + "\", " + "\"" + password + "\", " + level + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alUsers.size()-1;
             while(!found && i >= 0){
                 if(alUsers.get(i).id == id){
                     alUsers.get(i).name = name;
                     alUsers.get(i).password = password;
                     alUsers.get(i).level = level;
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteUser(int id)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteUser(" + id + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alUsers.size()-1;
             while(!found && i >= 0){
                 if(alUsers.get(i).id == id){
                    alUsers.remove(i);
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User getUserLoggedIn(){
        ResultSet tempResultSet = null;
        User user = null;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("SELECT * FROM tbluser WHERE dtLoggedIn = TRUE;");
            tempResultSet = sth.executeQuery();
            
            tempResultSet.first();
            
            int id = tempResultSet.getInt("idUser");
            String name = tempResultSet.getString("dtUsername");
            String password = tempResultSet.getString("dtPassword");
            Timestamp createDate = tempResultSet.getTimestamp("dtCreateDate");
            int level = tempResultSet.getInt("dtLevel");
            
            user = new User(id, name, password, createDate, level);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
        
        return user;
    }
    
    public User getUser(int id){
        User user = null;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("SELECT * FROM tbluser WHERE idUser = " + id);
            resultSet = sth.executeQuery();
            resultSet.first();
            user = new User(id, resultSet.getString("dtUsername"),
                            resultSet.getString("dtPassword"),
                            resultSet.getTimestamp("dtCreateDate"),
                            resultSet.getInt("dtLevel"));
            
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeResultSet();
            closeConnection();
        }
        return user;
    }
    
    public String[] toUserArray(){
        String[] arResult = new String[alUsers.size()];
        int i = 0;
        for (User user: alUsers) {
            arResult[i] = user.toString();
            i++;
        }
        
        return arResult;
    }
    
    //-----------------------------------ErrorCodes------------------------------------------
    
    public void addErrorCode(int fiLanguage, String description){
        int idErrorCode;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_insertErrorCode(@status, @error)" );
            sth.executeUpdate();
            sth = conn.prepareStatement("SELECT MAX(idErrorCode) FROM tblerrorcode");
            resultSet = sth.executeQuery();
            resultSet.first();
            idErrorCode = resultSet.getInt(1);
            closeStatement();
            closeResultSet();
            
            sth = conn.prepareStatement("CALL p_insertErrorCode_language(" + idErrorCode
                    + ", " + fiLanguage + ", '" + description + "'" + ", @status, @errorCode)");
            sth.executeUpdate();
            
            alErrorCodes.add(new ErrorCode(idErrorCode, description, getLanguage(fiLanguage)));
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getErrorCodes(){
        ResultSet tempResultSet;
        try
        {
            connectToDatabase();
            alErrorCodes.clear();
            sth = conn.prepareStatement("SELECT * FROM tblerrorcode_language;");
            tempResultSet = sth.executeQuery();
            tempResultSet.first();
            while(!tempResultSet.isAfterLast()){
                int fiErrorCode = tempResultSet.getInt("fiErrorCode");
                int fiLanguage = tempResultSet.getInt("fiLanguage");
                Language language = getLanguage(fiLanguage);
                String description = tempResultSet.getString("dtDescription");
                
                boolean add = true;
                for (ErrorCode errorCode : alErrorCodes)
                {
                    if(errorCode.id == fiErrorCode){
                        errorCode.addDescription(language, description);
                        add = false;
                    }
                }
                
                if(add) alErrorCodes.add(new ErrorCode(fiErrorCode, description, language));
                tempResultSet.next();
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
    public ErrorCode getErrorCode(int id){
        ErrorCode result = null;
        for (ErrorCode errorCode : alErrorCodes)
        {
            if(errorCode.id == id) result = errorCode;
        }
        
        return result;
    }
    
    public int getFiErrorCode(int fiLanguage, String description){
        int result = -1;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("SELECT (fiErrorCode) FROM "
                    + "tblerrorcode_language WHERE fiLanguage = '" + fiLanguage + 
                    "' AND dtDescription = '" + description + "'");
            resultSet = sth.executeQuery();
            resultSet.first();
            result =  resultSet.getInt(1);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String getErrorCodeDescription(int id, Language language){
        String result = "";
        for (ErrorCode errorCode : alErrorCodes)
        {
            if(errorCode.id == id){
                result = errorCode.getAlDescriptions().get(language);
            }
        }
        return result;
    }
    
    public void addErrorCodeDescription(Language language, int fiErrorCode,
            String description){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_insertErrorCode_language(" + 
                    fiErrorCode +", " + getFiLanguage(language) + ", '" +
                    description + "', @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            for (ErrorCode errorCode : alErrorCodes)
            {
                if(errorCode.id == fiErrorCode)alErrorCodes.get(alErrorCodes.indexOf(errorCode)).
                        addDescription(language, description);
            }
            
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteErrorCodeDescription(int fiErrorCode, int fiLanguage)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteErrorCode_language(" + fiErrorCode + ", " + fiLanguage + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alErrorCodes.size()-1;
             while(!found && i >= 0){
                 if(alErrorCodes.get(i).id == fiErrorCode){
                    alErrorCodes.get(i).getAlDescriptions().remove(getLanguage(fiLanguage));
                    if(alErrorCodes.get(i).getAlDescriptions().size() == 0) alErrorCodes.remove(i);
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAllErrorCodeDescriptions(int fiErrorCode){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteErrorCode(" + fiErrorCode + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            int i = alErrorCodes.size()-1;
            boolean found = false;
            while(i >= 0 && found == false){
                if(alErrorCodes.get(i).id == fiErrorCode) {
                    alErrorCodes.remove(i);
                    found = true;
                }
                i--;
            }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateErrorCodeDescription(int fiErrorCode, Language language,
            String newDescription){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_updateErrorCode_language(" + 
                    fiErrorCode +", " + language.id + ", '" + newDescription +
                    "', @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            int i = alErrorCodes.size()-1;
            boolean found = false;
            while(i >= 0 && found == false){
                if(alErrorCodes.get(i).id == fiErrorCode) {
                    alErrorCodes.get(i).getAlDescriptions().put(language, newDescription);
                    found = true;
                }
                i--;
            }
            
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String[] toErrorCodeArray(){
        String[] arResult = new String[alErrorCodes.size()];
        int i = 0;
        for (ErrorCode errorCode: alErrorCodes) {
            arResult[i] = errorCode.toString();
            i++;
        }
        
        return arResult;
    }
    
    //-----------------------------------------MessageType------------------------------------------------------------------------

    public String[] toMessageTypeArray(){
        String[] arResult = new String[alMessageTypes.size()];
        int i = 0;
        for (MessageType messageType: alMessageTypes) {
            arResult[i] = messageType.toString();
            i++;
        }
        
        return arResult;
    }

    public void deleteMessageTypeDescription(int fiMessageType, int fiLanguage)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteMessageType_language(" + fiMessageType + ", " + fiLanguage + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alMessageTypes.size()-1;
             while(!found && i >= 0){
                 if(alMessageTypes.get(i).id == fiMessageType){
                    alMessageTypes.get(i).getAlDescriptions().remove(getLanguage(fiLanguage));
                    if(alMessageTypes.get(i).getAlDescriptions().size() == 0) alMessageTypes.remove(i);
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addMessageTypeDescription(Language language, int fiMessageType,
            String description)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_insertMessageType_language(" + 
                    fiMessageType +", " + getFiLanguage(language) + ", '" +
                    description + "', @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            for (MessageType messageType : alMessageTypes)
            {
                if(messageType.id == fiMessageType)alMessageTypes.get(alMessageTypes.indexOf(messageType)).
                        addDescription(language, description);
            }
            
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAllMessageTypeDescriptions(int fiMessageType)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteMessageType(" + fiMessageType + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            int i = alMessageTypes.size()-1;
            boolean found = false;
            while(i >= 0 && found == false){
                if(alMessageTypes.get(i).id == fiMessageType) {
                    alMessageTypes.remove(i);
                    found = true;
                }
                i--;
            }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getMessageTypeDescription(int id, Language language)
    {
        String result = null;
        for (MessageType messageType : alMessageTypes)
        {
            if(messageType.id == id){
                result = messageType.getAlDescriptions().get(language);
            }
        }
        return result;
    }

    public void addMessageType(int fiLanguage, String description)
    {
        int idMessageType;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_insertMessageType(@status, @error)");
            sth.executeUpdate();
            sth = conn.prepareStatement("SELECT MAX(idMessageType) FROM tblmessagetype");
            resultSet = sth.executeQuery();
            resultSet.first();
            idMessageType = resultSet.getInt(1);
            closeStatement();
            closeResultSet();
            
            sth = conn.prepareStatement("CALL p_insertMessageType_language(" + idMessageType
                    + ", " + fiLanguage + ", '" + description + "'" + ", @status, @errorCode)");
            sth.executeUpdate();
            
            alMessageTypes.add(new MessageType(idMessageType, description, getLanguage(fiLanguage)));
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getMessageTypes()
    {
        ResultSet tempResultSet;
        try
        {
            connectToDatabase();
            alMessageTypes.clear();
            sth = conn.prepareStatement("SELECT * FROM tblmessagetype_language;");
            tempResultSet = sth.executeQuery();
            tempResultSet.first();
            while(!tempResultSet.isAfterLast()){
                int fiMessageType = tempResultSet.getInt("fiMessageType");
                int fiLanguage = tempResultSet.getInt("fiLanguage");
                Language language = getLanguage(fiLanguage);
                String description = tempResultSet.getString("dtDescription");
                boolean add = true;
                for (MessageType messageType : alMessageTypes)
                {
                    if(messageType.id == fiMessageType){
                        messageType.addDescription(language, description);
                        add = false;
                    }
                }
                
                if(add) alMessageTypes.add(new MessageType(fiMessageType, description, language));
                tempResultSet.next();
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
    public MessageType getMessageType(int id){
        MessageType result = null;
        for (MessageType messageType : alMessageTypes)
        {
            if(messageType.id == id) result = messageType;
        }
        
        return result;
    }
    
    public int getFiMessageType(int fiLanguage, String description){
        int result = -1;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("SELECT (fiMessageType) FROM "
                    + "tblmessagetype_language WHERE fiLanguage = '" + fiLanguage + 
                    "' AND dtDescription = '" + description + "'");
            resultSet = sth.executeQuery();
            resultSet.first();
            result =  resultSet.getInt(1);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void updateMessageTypeDescription(int fiMessageType, 
            Language language, String newDescription)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_updateMessageType_language(" + 
                    fiMessageType +", " + language.id + ", '" + newDescription +
                    "', @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            int i = alMessageTypes.size()-1;
            boolean found = false;
            while(i >= 0 && found == false){
                if(alMessageTypes.get(i).id == fiMessageType) {
                    alMessageTypes.get(i).getAlDescriptions().put(language, newDescription);
                    found = true;
                }
                i--;
            }
            
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //---------------------------Messages--------------------------------------------------------

    public void getMessages()
    {
        ResultSet tempResultSet;
        try
        {
            connectToDatabase();
            alMessages.clear();
            sth = conn.prepareStatement("SELECT * FROM tblmessage");
            tempResultSet = sth.executeQuery();
            tempResultSet.first();
            while(!tempResultSet.isAfterLast()){
                
                int id = tempResultSet.getInt("idMessage");
                int fiMessageType = tempResultSet.getInt("fiMessageType");
                int fiErrorCode = tempResultSet.getInt("fiErrorCode");
                int fiUser = tempResultSet.getInt("fiUser");
                int fiEmail = tempResultSet.getInt("fiEmail");
                int fiSms = tempResultSet.getInt("fiSms");
                int fiLanguage = tempResultSet.getInt("fiLanguage");
                int fiTextFile = tempResultSet.getInt("fiTextFile");
                
                MessageType messageType = getMessageType(fiMessageType);
                User user = getUser(fiUser);
                ErrorCode errorCode = getErrorCode(fiErrorCode);
                Email email = getEmail(fiEmail);
                Sms sms = getSms(fiSms);
                Language language = getLanguage(fiLanguage);
                TextFile textFile = getTextFile(fiTextFile);
                
                String param1 = tempResultSet.getString("dtParam1");
                String param2 = tempResultSet.getString("dtParam2");
                String param3 = tempResultSet.getString("dtParam3");
                
                String createDate = tempResultSet.getTimestamp("dtCreateDate").toString();
                String sender = user.name;
                String type = messageType.toString(currentLanguage);
                
                Message message = new Message(user, errorCode, createDate, messageType,language, email,sms, textFile, id, param1, param2, param3);
                alMessages.add(message);
                tempResultSet.next();
            }
            closeConnection();
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
    public String replacePlaceholders(String target, String param1, String param2,
            String param3){
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
    
    public Message getMessage(int id){
        Message result = null;
        for (Message message : alMessages)
        {
            if(message.id == id) result = message;
        }
        
        return result;
    }

    public String[] toMessageArray()
    {
        String[] arResult = new String[alMessages.size()];
        int i = 0;
        for (Message message: alMessages) {
            arResult[i] = message.toString(currentLanguage);
            i++;
        }
        
        return arResult;
    }
    
    public void addMessage(User user, MessageType messageType, ErrorCode errorCode,
            Email email, Sms sms, Language language,
            String param1, String param2, String param3)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_sendMessage(" + user.id + ", " + 
                    messageType.id + ", " + 
                    errorCode.id + ", 1, " + email.id + ", " + sms.id
                     + ", " + language.id + ", "+  param1 + ", " + param2 +
                    ", " + param3 +")");
            sth.executeUpdate();
            getMessages();
            
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
    public void deleteMessage(int id){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteMessage(" + id + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alMessages.size()-1;
             while(!found && i >= 0){
                 if(alMessages.get(i).id == id){
                    alMessages.remove(i);
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
    public int getLastIdMessage(){
        int id = -1;
        ResultSet tempResultSet = null;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("SELECT MAX(idMessage) FROM tblmessage");
            tempResultSet = sth.executeQuery();
            id = resultSet.getInt("0");
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    //------------------------------Emails-----------------------------------------------------------------------------
    
    public void addEmail(String email, String name)
    {
        int id;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_insertEmail('" + email +"'" + ", " + "'" + name + "', @status, @error)" );
            sth.executeUpdate();
            sth = conn.prepareStatement("SELECT MAX(idEmail) FROM tblemail");
            resultSet = sth.executeQuery();
            resultSet.first();
            id = resultSet.getInt(1);
            closeStatement();
            closeResultSet();
            sth = conn.prepareStatement("SELECT * FROM tblemail WHERE idemail = " + id);
            resultSet = sth.executeQuery();
            resultSet.first();
            
            alEmails.add(new Email(id, email, name));
            closeResultSet();
            closeStatement();
            closeConnection();
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getEmails(){
        ResultSet tempResultSet;
        try
        {
            connectToDatabase();
            alEmails.clear();
            sth = conn.prepareStatement("SELECT * FROM tblemail;");
            tempResultSet = sth.executeQuery();
            tempResultSet.first();
            while(!tempResultSet.isAfterLast()){
                int id = tempResultSet.getInt("idEmail");
                String address = tempResultSet.getString("dtAddress");
                String name = tempResultSet.getString("dtName");
                alEmails.add(new Email(id, address,name));
                tempResultSet.next();
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
    public void updateEmail(int id, String address, String name){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_updateEmail(" + id + ", '" 
                    + address + "', '" + name + "', @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alEmails.size()-1;
             while(!found && i >= 0){
                 if(alEmails.get(i).id == id){
                     alEmails.get(i).address = address;
                     alEmails.get(i).name = name;
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteEmail(int id)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteEmail(" + id + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alEmails.size()-1;
             while(!found && i >= 0){
                 if(alEmails.get(i).id == id){
                    alEmails.remove(i);
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Email getEmail(int id){
        Email email = null;
        if(id != 0){
            try
            {
                connectToDatabase();
                sth = conn.prepareStatement("SELECT * FROM tblemail WHERE idEmail = " + id);
                resultSet = sth.executeQuery();
                resultSet.first();
                email = new Email(id, resultSet.getString("dtAddress"),
                                resultSet.getString("dtName"));

            } catch (ClassNotFoundException ex)
            {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex)
            {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                closeResultSet();
                closeConnection();
            }
        }
        return email;
    }
    
    public String[] toEmailArray(){
        String[] arResult = new String[alEmails.size()];
        int i = 0;
        for (Email email: alEmails) {
            arResult[i] = email.toString();
            i++;
        }
        
        return arResult;
    }
    
    //------------------------------Sms-----------------------------------------------------------------------------
    
    public void addSms(int number, String name)
    {
        int id;
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_insertSms(" + number + ", " + "'" + name + "', @status, @error)" );
            sth.executeUpdate();
            sth = conn.prepareStatement("SELECT MAX(idSms) FROM tblsms");
            resultSet = sth.executeQuery();
            resultSet.first();
            id = resultSet.getInt(1);
            closeStatement();
            closeResultSet();
            sth = conn.prepareStatement("SELECT * FROM tblsms WHERE idSms = " + id);
            resultSet = sth.executeQuery();
            resultSet.first();
            
            alSms.add(new Sms(id, number, name));
            closeResultSet();
            closeStatement();
            closeConnection();
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getSmses(){
        ResultSet tempResultSet;
        try
        {
            connectToDatabase();
            alSms.clear();
            sth = conn.prepareStatement("SELECT * FROM tblsms;");
            tempResultSet = sth.executeQuery();
            tempResultSet.first();
            while(!tempResultSet.isAfterLast()){
                int id = tempResultSet.getInt("idSms");
                int number = tempResultSet.getInt("dtNumber");
                String name = tempResultSet.getString("dtName");
                alSms.add(new Sms(id, number,name));
                tempResultSet.next();
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
    public void updateSms(int id, int number, String name){
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_updateSms(" + id + ", " 
                    + number + ", '" + name + "', @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alSms.size()-1;
             while(!found && i >= 0){
                 if(alSms.get(i).id == id){
                     alSms.get(i).number = number;
                     alSms.get(i).name = name;
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteSms(int id)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteSms(" + id + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alSms.size()-1;
             while(!found && i >= 0){
                 if(alSms.get(i).id == id){
                    alSms.remove(i);
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Sms getSms(int id){
        Sms sms = null;
        if(id != 0){
            try
            {
                connectToDatabase();
                sth = conn.prepareStatement("SELECT * FROM tblsms WHERE idSms = " + id);
                resultSet = sth.executeQuery();
                resultSet.first();
                sms = new Sms(id, resultSet.getInt("dtNumber"),
                                resultSet.getString("dtName"));

            } catch (ClassNotFoundException ex)
            {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex)
            {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                closeResultSet();
                closeConnection();
            }
        }
        return sms;
    }
    
    public String[] toSmsArray(){
        String[] arResult = new String[alSms.size()];
        int i = 0;
        for (Sms sms: alSms) {
            arResult[i] = sms.toString();
            i++;
        }
        
        return arResult;
    }
    
    //----------------------TextFiles-------------------------------------------------------
    public void addTextFile(String path){
        try
        {
            String oldPath = path;
            path = path.replaceAll("\\\\", "\\\\\\\\\\\\");
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_insertTextFile(" + "\"" 
                    + path + "\", @status, @error)");
            System.out.println("sth: " + sth);
            sth.executeUpdate();
            sth = conn.prepareStatement("SELECT MAX(idTextFile) FROM tbltextfile");
            resultSet = sth.executeQuery();
            resultSet.first();
            alTextFiles.add(new TextFile(resultSet.getInt(1), oldPath));
            
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getTextFiles(){
        ResultSet tempResultSet;
        try
        {
            connectToDatabase();
            alTextFiles.clear();
            sth = conn.prepareStatement("SELECT * FROM tbltextfile;");
            tempResultSet = sth.executeQuery();
            tempResultSet.first();
            while(!tempResultSet.isAfterLast()){
                int id = tempResultSet.getInt("idTextFile");
                String path = tempResultSet.getString("dtFilename");
                System.out.println("get files path: " + path);
                alTextFiles.add(new TextFile(id, path));
                tempResultSet.next();
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            closeConnection();
        }
    }
    
     public TextFile getTextFile(int idTextFile){
        TextFile textFile = null;
        for (TextFile alTextFile : alTextFiles
)        {
            if(alTextFile.id == idTextFile) textFile = alTextFile;
        }
        return textFile;
    }
     
    
    public void updateTextFile(int id, String newPath){
        try
        {
            String oldPath = newPath;
            newPath = newPath.replaceAll("\\\\", "\\\\\\\\\\\\");
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_updateTextFile(" + id + ", " 
                    + "\"" + newPath + "\", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alTextFiles.size()-1;
             while(!found && i >= 0){
                 if(alTextFiles.get(i).id == id){
                     alTextFiles.get(i).path = oldPath;
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteTextFile(int id)
    {
        try
        {
            connectToDatabase();
            sth = conn.prepareStatement("CALL p_deleteTextFile(" + id + ", @status, @error)");
            sth.executeUpdate();
            closeStatement();
            closeConnection();
            
            boolean found = false;
            int i = alTextFiles.size()-1;
             while(!found && i >= 0){
                 if(alTextFiles.get(i).id == id){
                    alTextFiles.remove(i);
                     found = true;
                 }
                i--;
             }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String[] toTextFileArray(){
        String[] arResult = new String[alTextFiles.size()];
        int i = 0;
        for (TextFile textFile: alTextFiles) {
            arResult[i] = textFile.toString();
            i++;
        }
        
        return arResult;
    }
    //----------------------------------------------------------------------------------------------------------------
    
    
    
    
//    public static void main(String[] args)
//    {
//        try
//        {
//            Database db = new Database("192.168.56.10", "jim", "q1w2e3!", 22, "localhost", 3366, "mh_admin", "q1w2e3!", "messagehandler", 3306);
//            db.getErrorCodes();
//            db.getLanguages();
//            db.getMessageTypes();
//            db.getMessages();
//            db.logout();
//            System.exit(0);
//        } catch (JSchException ex)
//        {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        finally{
//            System.exit(0);
//        }
//    }
}