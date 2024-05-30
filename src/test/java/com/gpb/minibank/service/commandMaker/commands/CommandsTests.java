package com.gpb.minibank.service.commandMaker.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandsTests {

    static Update updateResult;

    static String textOfResponse = "Привет, "
            + "Василий!"
            + "\nЯ telegram-бот от GPB мини-банка!"
            + "\n\nЯ умею выполнять команду /ping."
            + "\nНапиши (или нажми) /ping, и я отвечу тебе!";

    @BeforeAll
    public static void createUpdate() {
        var chat = new Chat();
        chat.setFirstName("Василий");
        var message = new Message();
        message.setChat(chat);
        var update = new Update();
        update.setMessage(message);
        updateResult = update;
    }

    @Test
    void testStart() {
        updateResult.getMessage().setText("/start");
        var result = new Start().exec(updateResult);

        Assertions.assertEquals(textOfResponse, result);
    }

    @Test
    void testPing() {
        updateResult.getMessage().setText("/ping");
        var result = new Ping().exec();

        Assertions.assertEquals("pong", result);
    }
}
