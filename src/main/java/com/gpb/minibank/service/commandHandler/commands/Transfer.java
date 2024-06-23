package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.transferClient.TranferClient;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateTransferRequestDTO;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Error;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Result;
import com.gpb.minibank.service.commandHandler.commands.dto.response.TransferResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class Transfer implements Command {

    private static final int INDEX_OF_TARGET_USER_NAME = 1;

    private static final int INDEX_OF_AMOUNT = 2;

    private final String nameOfCommand;

    private final TranferClient tranferClient;

    public Transfer(TranferClient tranferClient) {
        this.nameOfCommand = "/transfer";
        this.tranferClient = tranferClient;
    }

    @Override
    public String getName() {
        return nameOfCommand;
    }

    @Override
    public String exec(Update update) {
        if (!check(update.getMessage().getText())) {
            return "Данные введены в некорректном формате.\nПроверьте правильность введённых данных!";
        }
        var dataFromUser = update.getMessage().getText().split(" ");
        var createTransferRequestDTO = new CreateTransferRequestDTO(update.getMessage().getChat().getUserName(),
                dataFromUser[INDEX_OF_TARGET_USER_NAME],
                dataFromUser[INDEX_OF_AMOUNT]);
        try {
            var response = tranferClient.runRequest(createTransferRequestDTO);
            return getMessage((ResponseEntity<Result<TransferResponseDTO, Error>>) response);
        } catch (HttpClientErrorException error) {
            return "Ошибка!\nЧто-то пошло не так!";
        } catch (RestClientException error) {
            return "Сервис не доступен!";
        }
    }

    public String getMessage(ResponseEntity<Result<TransferResponseDTO, Error>> response) {
        var transferResponseDTO = response.getBody().getResponse();
        if (transferResponseDTO.isPresent()) {
            return "Перевод выполнен!\nИдентификатор перевода: " + transferResponseDTO.get().getTransferId();
        }
        return response.getBody().getError().get().getMessage();
    }

    public boolean check(String data) {
        Pattern pattern = Pattern.compile("^/transfer [a-zA-Z]{5,} [1-9][0-9]+(\\.[0-9]{2})?$");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
}
