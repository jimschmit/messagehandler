/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Jim
 */
public class Sms extends Table{

    public int number;
    public String name;
    public Sms(int id, int number, String name)
    {
        super(id);
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name + ": " + number;
    }
    
    public String toStringForComboBox(){
        return name;
    }
    
    
    
}
