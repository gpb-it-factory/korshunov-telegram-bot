package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.createAccountClient.createAccountClientImpl.CreateAccountClientHttp;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CreateAccountTest {

    @Mock
    public CreateAccountClientHttp createAccountClientHttp;

    @InjectMocks
    public CreateAccount createAccount;

    static Update update;

    @BeforeAll
    public static void createUpdate() {
        var chat = new Chat();
        chat.setFirstName("Василий");
        chat.setId(1000L);
        var message = new Message();
        message.setChat(chat);
        var resultUpdate = new Update();
        resultUpdate.setMessage(message);
        update = resultUpdate;
    }

    @Test
    void testCreateAccountWithCorrectData() {
        Mockito.doReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build())
                .when(createAccountClientHttp).runRequest(any());

        var result = createAccount.exec(update);

        Assertions.assertEquals(result, "Счёт создан!");
    }

    @Test
    void testRegisterWithError() {
        Mockito.doReturn(ResponseEntity.status(400).build()).when(createAccountClientHttp).runRequest(any());

        var result = createAccount.exec(update);

        Assertions.assertEquals(result, "Упс!\nЧто-то пошло не так!");
    }

    @Test
    void testRegisterWithRestException() {
        Mockito.doThrow(RestClientException.class)
                .when(createAccountClientHttp).runRequest(any());

        var result = createAccount.exec(update);

        Assertions.assertEquals(result, "Сервис не доступен!");
    }
}
