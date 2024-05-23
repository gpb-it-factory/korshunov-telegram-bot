package com.gpb.minibank.service.messageSender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MessageSender extends DefaultAbsSender {

    public MessageSender(@Value("${bot.token}") String botToken) {
        super(new DefaultBotOptions(), botToken);
    }

    public void sendMessage(SendMessage message) throws TelegramApiException {
        this.execute(message);
    }
}
