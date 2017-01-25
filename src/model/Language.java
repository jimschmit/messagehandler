/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Jim
 */
public class Language extends Table{
    
    protected String description;

    public Language(int id, String description)
    {
        super(id);
        this.description = description;
    }

    @Override
    public String toString()
    {
        return description + " ";
    }
}
