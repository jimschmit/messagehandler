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
public class Email extends Table{
    public String address, name;

    public Email(int id, String address, String name)
    {
        super(id);
        this.address = address;
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name + ": " + address;
    }
    
    public String toStringForComboBox(){
        return name;
    }
}
