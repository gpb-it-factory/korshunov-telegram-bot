package com.gpb.minibank.service.messageSender;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageSender extends DefaultAbsSender {

    private SendMessage sendMessage;

    public MessageSender(String botToken, SendMessage sendMessage) {
        super(new DefaultBotOptions(), botToken);
        this.sendMessage = sendMessage;
    }

    public void sendMessage() throws TelegramApiException {
        this.execute(sendMessage);
    }
}
