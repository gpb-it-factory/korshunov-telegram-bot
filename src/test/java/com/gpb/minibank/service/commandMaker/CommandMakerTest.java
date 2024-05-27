package com.gpb.minibank.service.commandMaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandMakerTest {

    static Update updateResult;

    static String textMessage = "Привет, "
            + "Рома"
            + "!\nЯ telegram-бот от GPB мини-банка!"
            + "\n\nЯ умею выполнять команду /ping."
            + "\nНапиши (или нажми) /ping, и я отвечу тебе!";

    @BeforeAll
    static void createUpdate() {
        var chat = new Chat();
        chat.setFirstName("Рома");
        var message = new Message();
        message.setChat(chat);
        var update = new Update();
        update.setMessage(message);
        updateResult = update;
    }

    @Test
    void testCommandMakerStart() {
        updateResult.getMessage().setText("/start");
        var result = CommandMaker.work(updateResult);
        Assertions.assertEquals(result, textMessage);
    }

    @Test
    void testCommandMakerPing() {
        updateResult.getMessage().setText("/ping");
        var result = CommandMaker.work(updateResult);
        Assertions.assertEquals(result, "pong");
    }

    @Test
    void testCommandMakerWrongCommand() {
        updateResult.getMessage().setText("/wrongCommand");
        var result = CommandMaker.work(updateResult);
        Assertions.assertEquals(result, "Я не умею выполнять команду '/wrongCommand'!");
    }
}
