package com.gpb.minibank.service.commandHandler.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class PingTest {

    static Update update;

    @BeforeAll
    public static void createUpdate() {
        var chat = new Chat();
        chat.setFirstName("Василий");
        chat.setId(1000L);
        chat.setUserName("@vasyl");
        var message = new Message();
        message.setChat(chat);
        var resultUpdate = new Update();
        resultUpdate.setMessage(message);
        update = resultUpdate;
    }

    @Test
    void testPing() {
        update.getMessage().setText("/ping");

        var result = new Ping().exec(update);

        Assertions.assertEquals("pong", result);
    }
}
