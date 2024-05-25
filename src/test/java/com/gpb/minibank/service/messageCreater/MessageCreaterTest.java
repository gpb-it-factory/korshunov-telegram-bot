package com.gpb.minibank.service.messageCreater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageCreaterTest {

    @Test
    void testCreateMessage() {
        var result = MessageCreater.createMessage(1L, "hello");
        Assertions.assertInstanceOf(SendMessage.class, result);
        Assertions.assertNotNull(result.getChatId());
        Assertions.assertEquals(result.getText(), "hello");
    }
}
