/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mila;

import mila.routing.Router;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 *
 * @author David
 */
public class BotInterface extends TelegramLongPollingBot {
    
    public static final String BOT_USERNAME = "Mila_0_Bot";
    public static final String BOT_TOKEN = "277537438:AAHzTSmgoCjTLkB83-93vtCg7eNaGzGn0qg";
    public static Router router = new Router();
    
    @Override
    public String getBotToken() {
        return BotInterface.BOT_TOKEN;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        //check if the update has a message
        if(update.hasMessage()){
            Message message = update.getMessage();
            User user = message.getFrom();

            //check if the message has text. it could also contain for example a location ( message.hasLocation() )
            if(message.hasText()){
                //create an object that contains the information to send back the message
                SendMessage sendMessageRequest = new SendMessage();
                sendMessageRequest.setChatId(message.getChatId().toString()); //who should get from the message the sender that sent it.
            
                
                String reply = router.routeMessage(Integer.parseInt(message.getChatId().toString()),message.getText(),user.getFirstName(),user.getLastName());
                
                sendMessageRequest.setText(reply);
                
                System.out.println("\nReply: "+reply+"\n----------------------------------");
                
                try {
                    sendMessage(sendMessageRequest); //at the end, so some magic and send the message ;)
                } catch (TelegramApiException e) {
                //do some error handling
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



    
    
}
