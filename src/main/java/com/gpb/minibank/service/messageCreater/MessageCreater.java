package com.gpb.minibank.service.messageCreater;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageCreater {

    public static SendMessage createMessage (Long id, String text) {
        var message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        return message;
    }
}
