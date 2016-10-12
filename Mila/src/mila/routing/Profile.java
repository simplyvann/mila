/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mila.routing;

import java.io.Serializable;
/**
 *
 * @author David
 */
public class Profile implements Serializable{
    private int terminalState = 0;
    private int id;
    private String firstname;
    private String lastname;
    private boolean admin = false;
    private int messageCount = 0;
    
    public boolean isAdmin(){
        return admin;
    }
    
    public void setAdmin(boolean val){
        admin = val;
    }
    
    public void messageCount(){
        messageCount++;
    }
    
    public String allInfo(){
        return "ID: "+id+"\nName: "+firstname+" "+lastname+"\nMessages sent: "+messageCount+"\nAdmin: "+admin+"\nState: "+getStateString();
    }
    
    public Profile(int id, String firstname, String lastname){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        System.out.println("[profile added]");
    }
    
    public Profile(){
        
    }
    
    public boolean idEquals(int id){
        if(id==this.id){
            //System.out.println("[found id]");
            return true;
        }
        //System.out.println("[not found id]");
        return false;
    }
    
    public void setTerminalState(int terminalState){
        this.terminalState = terminalState;
    }
    
    public int getTerminalState(){
        return terminalState;
    }
    
    public String getStateString(){
        switch(terminalState){
            case 0: return "Message";
            case 1: return "Debugging";
            case 2: return "Command";
            default: return "Can't find state";
        }
    }
}
