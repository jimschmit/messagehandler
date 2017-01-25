/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jim
 */
public class TextFile extends Table{
    
    public String path;
    
    public TextFile(int id, String path) {
        super(id);
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
    
    
    
}
