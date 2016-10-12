/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mila.routing;

import java.util.logging.Level;
import java.util.logging.Logger;
import mila.commands.CommandInterface;
import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import mila.Storage.ObjectStore;

/**
 *
 * @author David
 */
public class Router implements Serializable{
    
    private int type = 0;
    private String reply = "No Reply";
    public Chats chatInterface = new Chats();
    private CommandInterface command = new CommandInterface();
    
    
    public String routeMessage(int id, String message, String firstname, String lastname){
        System.out.println("Message = "+message+"\nID = "+id+"\nName = "+firstname+" "+lastname);
        
        chatInterface.setProfile(id,firstname,lastname);
        Profile user = chatInterface.getProfile(id);
        int state = user.getTerminalState();
        user.messageCount();
        
        if(state==2){
            if(!message.startsWith("/")){
              message="/"+message;  
            }
        }
        if(message.startsWith("/")){
            state = 2;
        }
           
        if(state>1){
            reply = command.sendCommand(message,user,chatInterface);
        }
        else{
            reply = "You sent the message: "+message;
        }
        return reply;
    }

    public Router() {
        ObjectStore save = new ObjectStore();
        if(save.readObject("save/chatInterface.mila")!=null){
           this.chatInterface = (Chats) save.readObject("chatInterface.mila"); 
        }
                
    }
    
    public void readFromFile()
    {
        // Wrap all in a try/catch block to trap I/O errors.
        try{
        // Open file to read from, named SavedObj.sav.
        FileInputStream saveFile = new FileInputStream("ChatInterface.sav");

        // Create an ObjectInputStream to get objects from save file.
        ObjectInputStream save = new ObjectInputStream(saveFile);

        // Now we do the restore.
        // readObject() returns a generic Object, we cast those back
        // into their original class type.
        // For primitive types, use the corresponding reference class.
        Chats chatRead = (Chats) save.readObject();
        this.chatInterface = chatRead;
    }   catch (FileNotFoundException ex) {   
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
