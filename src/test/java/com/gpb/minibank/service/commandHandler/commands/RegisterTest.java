package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.registerClient.registerClientImpl.RegisterClientHttp;
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
public class RegisterTest {

    @Mock
    public RegisterClientHttp registerClientHttp;

    @InjectMocks
    public Register register;

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
    void testRegisterWithCorrectData() {
        Mockito.doReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build())
                .when(registerClientHttp).runRequest(any());

        var result = register.exec(update);

        Assertions.assertEquals(result, "Вы успешно зарегистрированы!");
    }

    @Test
    void testRegisterWithErrorCode() {
        Mockito.doThrow(new HttpStatusCodeException(HttpStatus.NOT_FOUND) {})
                .when(registerClientHttp).runRequest(any());

        var result = register.exec(update);

        Assertions.assertEquals(result, "Упс!\nЧто-то пошло не так!");
    }

    @Test
    void testRegisterWithUsageOneMoreTime() {
        Mockito.doThrow(new HttpStatusCodeException(HttpStatus.BAD_REQUEST) {})
                .when(registerClientHttp).runRequest(any());

        var result = register.exec(update);

        Assertions.assertEquals(result,"Ошибка!\nВы уже зарегистрированы!");
    }

    @Test
    void testRegisterWithRestException() {
        Mockito.doThrow(RestClientException.class)
                .when(registerClientHttp).runRequest(any());

        var result = register.exec(update);

        Assertions.assertEquals(result, "Сервис не доступен!");
    }
}
