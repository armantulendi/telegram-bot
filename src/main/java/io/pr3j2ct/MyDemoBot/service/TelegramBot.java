package io.pr3j2ct.MyDemoBot.service;

import io.pr3j2ct.MyDemoBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig botConfig;

    public TelegramBot(BotConfig botConfig){
        this.botConfig=botConfig;
    }
    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()&&update.getMessage().hasText()){
            String messageText=update.getMessage().getText();
            long chatId=update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    startCommandReceived(chatId,update.getMessage().getChat().getFirstName());
                    break;
                case "/command":
                    startCommandReceived(chatId,update.getMessage().getChat().getFirstName());
                    break;
                default: sendMessage(chatId,messageText);
            }

        }

    }
    private void startCommandReceived(long chatId,String name){
        String answer=""+name+",ты че берега попутал?";
        sendMessage(chatId,answer);
    }
    private void sendMessage(long chatId,String textSend){
        SendMessage message=new SendMessage();
        message.setChatId(chatId);
        message.setText(textSend);
        try{
            execute(message);
        }catch (TelegramApiException e){
            throw new RuntimeException();
        }
    }
}
