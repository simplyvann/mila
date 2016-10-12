/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mila.commands;

/**
 *
 * @author David
 */
public class Command {
    private String name;
    private String description;
    public static int count = 0;
    private String method;
    private boolean lock = false;
    private boolean admin = false;
    
    public Command(String name, String description){   
        this.name = name;
        this.description = description;
        count++;
    }
    
    public boolean getAdmin(){
        return admin;
    }
    
    public void setAdmin(boolean val){
        admin = val;
    }
    
    public String getDetails(){
        return "/"+name+" - "+description;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setMethod(String method){
        this.method = method;
    }
    
    public void setLock(boolean lock){
        this.lock = lock;
    } 
    
    public int getCount(){
        return count;
    }
    
    public boolean isName(String name){
        if(this.name.equals(name)){
            return true;
        }
        return false;
    }
    
    public boolean hasMethod(){
        if(method!=null){
            return true;
        }
        return false;
    }
    
    public String getMethod(){
        return method;
    }
    
}
