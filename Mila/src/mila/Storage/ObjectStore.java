/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mila.Storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import mila.routing.Chats;
import mila.routing.Router;
//import java.io.Serializable;

/**
 *
 * @author David
 */
public class ObjectStore {
    
    public ObjectStore(){
        
    }
    
    public boolean isFile(String path){
        try{
        // Open file to read from, named SavedObj.sav.
        FileInputStream saveFiles = new FileInputStream(path);
        ObjectInputStream save = new ObjectInputStream(saveFiles);
        save.close();
        return true;
        //this.chatInterface = chatRead;
        }   catch (FileNotFoundException ex) {  
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ObjectStore.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return false;
    }
    
    public Object readObject(String path)
    {
        // Wrap all in a try/catch block to trap I/O errors.
        try{
        // Open file to read from, named SavedObj.sav.
        FileInputStream saveFile = new FileInputStream(path);

        // Create an ObjectInputStream to get objects from save file.
        ObjectInputStream save = new ObjectInputStream(saveFile);

        // Now we do the restore.
        // readObject() returns a generic Object, we cast those back
        // into their original class type.
        // For primitive types, use the corresponding reference class.
        return save.readObject();
        //this.chatInterface = chatRead;
    }   catch (FileNotFoundException ex) {  
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }   
        return null;
    }
    
    public void writeObject(String path, Object obj){
        try{  // Catch errors in I/O if necessary.
                    // Open a file to write to, named SavedObj.sav.
                    FileOutputStream saveFile=new FileOutputStream(path);

                    // Create an ObjectOutputStream to put objects into save file.
                    ObjectOutputStream save = new ObjectOutputStream(saveFile);

                    // Now we do the save.
                    save.writeObject(obj);

                    // Close the file.
                    save.close(); // This also closes saveFile.
                    }
                    catch(Exception exc){
                    exc.printStackTrace(); // If there was an error, print the info.
                    }
    }
}
