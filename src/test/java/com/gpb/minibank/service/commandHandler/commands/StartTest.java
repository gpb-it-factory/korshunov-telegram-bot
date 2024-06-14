package com.gpb.minibank.service.commandHandler.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartTest {

    static Update update;

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
        var resultUpdate = new Update();
        resultUpdate.setMessage(message);
        update = resultUpdate;
    }

    @Test
    void testStart() {
        update.getMessage().setText("/start");

        var result = new Start().exec(update);

        Assertions.assertEquals(textOfResponse, result);
    }
}
