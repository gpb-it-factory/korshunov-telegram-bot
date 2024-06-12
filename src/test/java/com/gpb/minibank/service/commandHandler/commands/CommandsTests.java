package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.registerClient.registerClientImpl.RegisterClientHttp;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateUserDTO;
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
    public RegisterClientHttp registerClientHttp;

    @InjectMocks
    public Register register;

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
        chat.setId(1000L);
        chat.setUserName("@vasyl");
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

    @Test
    void testPing() {
        update.getMessage().setText("/ping");

        var result = new Ping().exec(update);

        Assertions.assertEquals("pong", result);
    }

    @Test
    void testRegisterWithCorrectData() {
        var user = new CreateUserDTO(1000L, "@vasyl");
        Mockito.doReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build())
                .when(registerClientHttp).runRequest(user);

        var result = register.exec(update);

        Assertions.assertEquals("Вы успешно зарегистрированы!", result);
    }

    @Test
    void testRegisterWithWrongData() {
        update.getMessage().getChat().setId(-111L);
        update.getMessage().getChat().setUserName("");
        var user = new CreateUserDTO(-111L, "");
        Mockito.when(registerClientHttp.runRequest(user))
                .thenThrow(new HttpStatusCodeException(HttpStatus.BAD_REQUEST) {});

        var result = register.exec(update);

        Assertions.assertEquals("Упс!\nЧто-то пошло не так!", result);
    }

    @Test
    void testRegisterWithRestException() {
        var user = new CreateUserDTO(1000L, "@vasyl");
        Mockito.doThrow(RestClientException.class)
                .when(registerClientHttp).runRequest(user);

        var result = register.exec(update);

        Assertions.assertEquals("Сервис не доступен!", result);
    }
}
