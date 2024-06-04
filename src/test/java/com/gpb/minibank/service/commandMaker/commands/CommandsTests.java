package com.gpb.minibank.service.commandMaker.commands;

import com.gpb.minibank.service.commandMaker.commands.requestRunner.RegisterRequestRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@ExtendWith(MockitoExtension.class)
public class CommandsTests {

    @Mock
    public RegisterRequestRunner registerRequestRunner;

    @InjectMocks
    public Register register;

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
        chat.setLinkedChatId(1000L);
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
        var result = new Ping().exec(updateResult);

        Assertions.assertEquals("pong", result);
    }

    @Test
    void testRegister() {
        Mockito.doReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build())
                .when(registerRequestRunner).runRequest(updateResult);
        var result = register.exec(updateResult);
        Assertions.assertEquals("Вы успешно зарегистрированы!", result);
    }

    @Test
    void testRegisterWithRestException() {
        Mockito.doThrow(RestClientException.class)
                .when(registerRequestRunner).runRequest(updateResult);
        var result = register.exec(updateResult);

        Assertions.assertEquals("Сервис не доступен!", result);
    }

    @Test
    void testRegisterWithExceptionFromMiddle() {
        Mockito.when(registerRequestRunner.runRequest(updateResult))
                .thenThrow(new HttpStatusCodeException(HttpStatus.BAD_REQUEST) {});
        var result = register.exec(updateResult);

        Assertions.assertEquals("Произошла ошибка.\n", result);
    }
}
