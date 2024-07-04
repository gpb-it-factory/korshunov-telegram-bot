package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.transferClient.TranferClient;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateTransferRequestDTO;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Error;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Result;
import com.gpb.minibank.service.commandHandler.commands.dto.response.TransferResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
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
            log.info("-- Проверяю команду /transfer на соответствие форме --");
            return "Данные введены в некорректном формате.\nПроверьте правильность введённых данных!";
        }
        var dataFromUser = update.getMessage().getText().split(" ");
        var createTransferRequestDTO = new CreateTransferRequestDTO(update.getMessage().getChat().getUserName(),
                dataFromUser[INDEX_OF_TARGET_USER_NAME],
                dataFromUser[INDEX_OF_AMOUNT]);
        try {
            var response = tranferClient.runRequest(createTransferRequestDTO);
            log.info("-- Ответ получен --");
            return getMessage((ResponseEntity<Result<TransferResponseDTO, Error>>) response);
        } catch (HttpStatusCodeException error) {
            log.info("-- Произошла ошибка: {} --", error.getMessage());
            return "Ошибка!\nЧто-то пошло не так!";
        } catch (RestClientException error) {
            log.info("-- Сервис не доступен --");
            return "Сервис не доступен!";
        }
    }

    public String getMessage(ResponseEntity<Result<TransferResponseDTO, Error>> response) {
        var transferResponseDTO = response.getBody().getResponse();
        if (transferResponseDTO.isPresent()) {
            log.info("Денежные средства успешно переведены");
            return "Перевод выполнен!\nИдентификатор перевода: " + transferResponseDTO.get().getTransferId();
        }
        log.info("Произошла ошибка при запросе на перевод денежных средств");
        return response.getBody().getError().get().getMessage();
    }

    public boolean check(String data) {
        Pattern pattern = Pattern.compile("^/transfer [a-zA-Z]{5,} [1-9][0-9]+(\\.[0-9]{2})?$");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
}
