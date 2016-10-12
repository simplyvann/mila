/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mila;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 *
 * @author David
 */
public class Mila extends BotInterface{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                telegramBotsApi.registerBot(new BotInterface());
            } catch (TelegramApiException e) {
                String LOGTAG = "";
                BotLogger.error(LOGTAG, e);
            }
            
            System.out.println("Mila 0.1 Live\n-----------------------------------------------------\n");
        
    }
    
}
