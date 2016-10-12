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
public class Chats extends Profile implements Serializable{
    
    private int profileLength = 0;
    private Profile[] profile = new Profile[100];
    
    public void loadProfiles(){
    }
    
    private int findProfile(int id){
        //System.out.println("[profiles]");
        for(int i = 0; i<profileLength; i++){
            //System.out.println("[searching profile]");
            if(profile[i].idEquals(id)){
                return i;
            }
        }
        return -1;
    }
    
    private int addProfile(int id, String firstname, String lastname){
        profile[profileLength] = new Profile(id, firstname, lastname);
        profileLength++;
        return (profileLength-1);
    }
    
    public Profile getProfile(int id){
        return profile[findProfile(id)];
    }
    
    public int profileState(Profile user){
        //improve
            return user.getTerminalState();
    }

    public void setProfile(int id, String firstname, String lastname) {
        int position = findProfile(id);
        if(position == -1){
            position = addProfile(id,firstname,lastname);
        }
    }
    
    public String displayInfo(){
        String content = "";
        for(int i = 0; i<profileLength; i++){
            content += profile[i].allInfo()+"\n\n";
        }
        return content;
    }
}
