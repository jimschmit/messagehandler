/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Jim
 */
public class MessageType extends Table{

    private HashMap<Language, String> alDescriptions = new HashMap<>();
    
    public MessageType(int id, String description, Language language)
    {
        super(id);
        addDescription(language, description);
    }
    public void addDescription(Language language, String description){
        alDescriptions.put(language, description);
    }
    
    @Override
    public String toString(){
        Set<Language> keySet = alDescriptions.keySet();
        Iterator it = keySet.iterator();
        String description = alDescriptions.get(it.next());
        return description;
    }
    
    public String toString(Language language){
        Set<Language> keySet = alDescriptions.keySet();
        if(alDescriptions.containsKey(language)){
            return alDescriptions.get(language);
        }
        else return alDescriptions.values().iterator().next();
    }

    public HashMap<Language, String> getAlDescriptions()
    {
        return alDescriptions;
    }
    
    
}
