package com.gpb.minibank.service.messageCreater;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MessageCreater {

    public static SendMessage createMessage (Long id, String text) {
        var message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        return message;
    }
}
