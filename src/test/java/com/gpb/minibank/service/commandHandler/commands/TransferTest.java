package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.transferClient.transferClientImpl.TransferClientHttp;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Error;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Result;
import com.gpb.minibank.service.commandHandler.commands.dto.response.TransferResponseDTO;
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
public class TransferTest {

    @Mock
    TransferClientHttp transferClientHttp;

    @InjectMocks
    Transfer transfer;

    static Update update;

    @BeforeAll
    public static void createUpdate() {
        var chat = new Chat();
        chat.setFirstName("Василий");
        chat.setId(1000L);
        chat.setUserName("@vasyl");
        var message = new Message();
        message.setChat(chat);
        message.setText("/transfer foryou 500.00");
        var resultUpdate = new Update();
        resultUpdate.setMessage(message);
        update = resultUpdate;
    }

    @Test
    void testTransferWithCorrectData() {
        var id = UUID.randomUUID().toString();
        var TransferResponseDTO = new TransferResponseDTO(id);
        Mockito.doReturn(ResponseEntity.status(HttpStatus.OK).body(Result.response(TransferResponseDTO)))
                .when(transferClientHttp).runRequest(any());

        var result = transfer.exec(update);

        Assertions.assertEquals(result, "Перевод выполнен!\nИдентификатор перевода: " + id);
    }

    @Test
    void testTransferWithErrorCode() {
        var error = new Error("message", "type", "400", "trace_id");
        Mockito.doReturn(ResponseEntity.status(200).body(Result.error(error)))
                .when(transferClientHttp).runRequest(any());

        var result = transfer.exec(update);

        Assertions.assertEquals(result, "message");
    }

    @Test
    void testTransferWithRestException() {
        Mockito.doThrow(RestClientException.class)
                .when(transferClientHttp).runRequest(any());

        var result = transfer.exec(update);

        Assertions.assertEquals(result, "Сервис не доступен!");
    }

    @Test
    void testCurrentBalanceWithHttpStatusCodeException() {
        Mockito.doThrow(new HttpStatusCodeException(HttpStatus.BAD_REQUEST) {})
                .when(transferClientHttp).runRequest(any());

        Assertions.assertEquals("Ошибка!\nЧто-то пошло не так!", transfer.exec(update));
    }

    @Test
    void testTransferWithWrongInputData() {
        update.getMessage().setText("/transfer foryou 0");
        var expected = "Данные введены в некорректном формате.\nПроверьте правильность введённых данных!";

        var result = transfer.exec(update);

        Assertions.assertEquals(expected, result);
    }
}
