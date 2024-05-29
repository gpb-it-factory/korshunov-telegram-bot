package com.gpb.minibank.service.commandMaker;

import com.gpb.minibank.service.commandMaker.commands.Ping;
import com.gpb.minibank.service.commandMaker.commands.Register;
import com.gpb.minibank.service.commandMaker.commands.Start;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public class CommandMakerTest {

    static CommandMaker commandMaker;

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
        commandMaker = new CommandMaker(
                new Start(),
                new Ping(),
                new Register()
        );
    }

    @Test
    void testCommandMakerStart() {
        updateResult.getMessage().setText("/start");
        var result = commandMaker.work(updateResult);
        Assertions.assertEquals(result, textMessage);
    }

    @Test
    void testCommandMakerPing() {
        updateResult.getMessage().setText("/ping");
        var result = commandMaker.work(updateResult);
        Assertions.assertEquals(result, "pong");
    }

    @Test
    void testCommandMakerWrongCommand() {
        updateResult.getMessage().setText("/wrongCommand");
        var result = commandMaker.work(updateResult);
        Assertions.assertEquals(result, "Я не умею выполнять команду '/wrongCommand'!");
    }
}
