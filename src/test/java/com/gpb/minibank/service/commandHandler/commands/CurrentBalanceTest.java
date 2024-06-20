package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.currentBalance.currentBalanceClientImpl.CurrentBalanceClientHttp;
import com.gpb.minibank.service.commandHandler.commands.dto.response.CurrentBalanceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;

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
        var currentBalanceDTO = new CurrentBalanceDTO("new", new BigDecimal("5000.00"));
        var response = ResponseEntity.status(200).body(currentBalanceDTO);

        Mockito.doReturn(response).when(currentBalanceClientHttp).runRequest(any());

        Assertions.assertEquals(currentBalance.exec(update), "Название счёта: new\nБаланс счёта: 5000.00");
    }

    @Test
    void testCurrentBalanceWithError() {
        var response = ResponseEntity.status(400).build();

        Mockito.doReturn(response).when(currentBalanceClientHttp).runRequest(any());

        Assertions.assertEquals(currentBalance.exec(update), "Упс!\nЧто-то пошло не так!");
    }

    @Test
    void testCurrentBalanceWithRestException() {
        Mockito.doThrow(RestClientException.class).when(currentBalanceClientHttp).runRequest(any());

        Assertions.assertEquals(currentBalance.exec(update), "Сервис не доступен!");
    }
}
