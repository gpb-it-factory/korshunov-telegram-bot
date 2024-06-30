package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.currentBalance.currentBalanceClientImpl.CurrentBalanceClientHttp;
import com.gpb.minibank.service.commandHandler.commands.dto.response.AccountDTO;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Error;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Result;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CurrentBalanceTest {

    @Mock
    CurrentBalanceClientHttp currentBalanceClientHttp;

    @InjectMocks
    CurrentBalance currentBalance;

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
    void testCurrentBalanceWithCorrectData() {
        var id = UUID.randomUUID().toString();
        var accountDTO = new AccountDTO(id,"new", "5000.00");
        var response = ResponseEntity.status(200).body(Result.response(accountDTO));

        Mockito.doReturn(response).when(currentBalanceClientHttp).runRequest(any());

        Assertions.assertEquals(currentBalance.exec(update), "Название счёта: new\nБаланс счёта: 5000.00");
    }

    @Test
    void testCurrentBalanceWithError() {
        var error = new Error("message", "type", "400", "trace_id");
        var response = ResponseEntity.status(200).body(Result.error(error));

        Mockito.doReturn(response).when(currentBalanceClientHttp).runRequest(any());

        Assertions.assertEquals(currentBalance.exec(update), "message");
    }

    @Test
    void testCurrentBalanceWithRestException() {
        Mockito.doThrow(RestClientException.class).when(currentBalanceClientHttp).runRequest(any());

        Assertions.assertEquals(currentBalance.exec(update), "Сервис не доступен!");
    }

    @Test
    void testCurrentBalanceWithHttpStatusCodeException() {
        Mockito.doThrow(new HttpStatusCodeException(HttpStatus.BAD_REQUEST) {})
                .when(currentBalanceClientHttp).runRequest(any());

        Assertions.assertEquals("Ошибка!\nЧто-то пошло не так!", currentBalance.exec(update));
    }
}
