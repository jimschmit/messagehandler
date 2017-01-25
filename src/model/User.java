/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author jim
 */
public class User extends Table{
    public String name, password;
    public Timestamp createDate;
    public int level;

    public User(int id, String name, String password, Timestamp createDate, int level)
    {
        super(id);
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.level = level;
    }

    @Override
    public String toString()
    {
        return name;
    }
    
    
    
    
}
