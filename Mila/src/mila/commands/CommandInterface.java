/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mila.commands;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import mila.routing.*;
import mila.Storage.ObjectStore;

/**
 *
 * @author David
 */
public class CommandInterface implements Serializable{
    
    private Command[] command = new Command[200];
    
    public CommandInterface(){
        addCommand("help","Displays a list of commands");
        addMethod("help","listCommands");
        lockCommand("help",true);
        addCommand("status","Details on the program status");
        addCommand("debug","Toggle debugging mode");
        addCommand("cmd","Toggle command mode");
        addCommand("me","User information");
        addCommand("save","Save the current status");
        addCommand("admin","Admin mode: [password]");
        addCommand("profiles","Display a list of profiles");
        addAdmin("profiles");
        addCommand("reboot","Reboot the AI system");
        addAdmin("reboot");
        addCommand("git","pull omnee from repo");
        addAdmin("git");
//        addCommand("cmdrename","Rename a command: [old name] [new name]");
//        addCommand("cmddescribe","Change a command description: [command] [description]");
    }
    
    private void addCommand(String name, String description){
        if(command.length<1){
            command[0] = new Command(name,description); 
        }
        else{
            command[command[0].count] = new Command(name,description);     
        }
    }
    
    private void addAdmin(String cmd){
         command[findCommand(cmd)].setAdmin(true);
    }
    
    private void addMethod(String cmd, String method){
        command[findCommand(cmd)].setMethod(method);
    }
    
    private void lockCommand(String cmd, boolean lock){
        command[findCommand(cmd)].setLock(true);
    }
    
    public String sendCommand(String cmdlong, Profile user, Chats chatInterface){
        
        String[] cmd = cmdlong.split(" ");
        
        int location = findCommand(cmd[0]);
        
        switch(cmd[0].substring(1)){
            case "help":
                return listCommands(user);
            case "cmdrename":
                location = findCommand(cmd[1]);
                if(location==-1) return "Command "+cmd[1]+" doesn't exist";
                command[location].setName(cmd[2]);
                return "[/cmdrename] - Command "+cmd[1]+" has been renamed to "+cmd[2];
            case "cmddescribe":
                location = findCommand(cmd[1]);
                if(location==-1) return "Command "+cmd[1]+" doesn't exist";
                String description = "";
                for(int i = 2; i<cmd.length; i++){
                    description += cmd[i]+" ";
                }
                command[location].setDescription(description);
                return "[/cmddescribe] - Command "+cmd[1]+" has a new description: "+description;
            case "debug":
                if(user.getTerminalState()==1){
                    user.setTerminalState(0);
                    return "[debug] - Debugging mode is off";
                }
                else {
                    user.setTerminalState(1);
                    return "[debug] - Debugging mode is on";
                }
            case "cmd":
                if(user.getTerminalState()==2){
                    user.setTerminalState(0);
                    return "[cmd] - Command mode is off";
                }
                else {
                    user.setTerminalState(2);
                    return "[cmd] - Command mode is on";
                }
            case "status":
                return "System Name: Mila V0.1\nState: "+user.getStateString();
            case "me":
                return user.allInfo();
            case "save":
                ObjectStore save = new ObjectStore();
                save.writeObject("save/chatInterface.mila",chatInterface);
                return "Session saved";
            case "admin":
                if(cmd[1].equals("pass")){
                    user.setAdmin(true);
                    return "Password correct, you are now an administrator";
                }
                else{
                    user.setAdmin(false);
                    return "Incorrect password";
                }
            case "profiles":
                if(!command[location].getAdmin()||(user.isAdmin())){
                    return chatInterface.displayInfo();
                }
                else{
                    return "This command requires admin rights!";
                }
            case "reboot":
                if(!command[location].getAdmin()||(user.isAdmin())){
                    ObjectStore save2 = new ObjectStore();
                    save2.writeObject("save/chatInterface.mila",chatInterface);
                    reboot();
                }
                else{
                    return "This command requires admin rights!";
                }
            case "git":
                if(!command[location].getAdmin()||(user.isAdmin())){
                    linuxCommand("git --work-tree=/var/zpanel/hostdata/zadmin/public_html/omnee-app_com pull origin master");
                }
                else{
                    return "This command requires admin rights!";
                }
                return "Git pulled omnee";
        }    

        return "The command ["+cmd[0]+"] is not recognised, try /help for a list of commands";
    }
    
//    public static void writeToFile(File path, Chats chatInterface)
//    {
//        try(ObjectOutputStream write= new ObjectOutputStream (new FileOutputStream(path)))
//        {
//            write.writeObject(chatInterface);
//        }
//        catch(NotSerializableException nse)
//        {
//            //do something
//        }
//        catch(IOException eio)
//        {
//            //do something
//        }
//    }
    
    private int findCommand(String cmd){
        if(cmd.startsWith("/")){
            cmd = cmd.substring(1);
        }
        for(int i = 0; i<command[0].count; i++){
            if(command[i].isName(cmd)){
                return i;
            };
        }
        return -1;
    }
    
    private String listCommands(Profile user){
        String list = "";
        
        for(int i = 0;i<command[0].count;i++){
            if(user.isAdmin()){
               list += "\n"+command[i].getDetails();
            }
            else{
                if(!command[i].getAdmin()){
                list += "\n"+command[i].getDetails();
                }
            }
        }
        return "Here are a list of the commands:\n"+list;
    }
    
    public static void reboot(){
    String shutdownCommand;
    String operatingSystem = System.getProperty("os.name");

    if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
        shutdownCommand = "sudo reboot";
    }
    else  {
        shutdownCommand = "shutdown.exe -r -t 0";
    }

        try {
            Runtime.getRuntime().exec(shutdownCommand);
        } catch (IOException ex) {
            Logger.getLogger(CommandInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    System.exit(0);
}
    
    public static void linuxCommand(String linuxcmd){
        try {
            Runtime.getRuntime().exec(linuxcmd);
        } catch (IOException ex) {
            Logger.getLogger(CommandInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    //System.exit(0);
}
}
