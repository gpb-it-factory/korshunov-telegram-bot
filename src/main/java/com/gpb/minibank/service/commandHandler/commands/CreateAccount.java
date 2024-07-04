package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.createAccountClient.CreateAccountClient;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public final class CreateAccount implements Command {

    private final static String DEFAULT_NAME_OF_ACCOUNT = "Акционный";

    private final String nameOfCommand;

    private CreateAccountClient createAccountClient;

    public CreateAccount(CreateAccountClient createAccountClient) {
        this.nameOfCommand = "/createaccount";
        this.createAccountClient = createAccountClient;
    }

    @Override
    public String getName() {
        return this.nameOfCommand;
    }

    @Override
    public String exec(Update update) {
        try {
            var createAccountDTO = new CreateAccountDTO(DEFAULT_NAME_OF_ACCOUNT);
            var response = createAccountClient.runRequest(update.getMessage().getChatId(), createAccountDTO);
            log.info("-- Ответ получен --");
            return createMessage(response);
        } catch (RestClientException error) {
            log.info("-- Сервис не доступен --");
            return "Сервис не доступен!";
        }
    }

    public String createMessage(ResponseEntity<?> response) {
        var statusCode = response.getStatusCode();
        if (statusCode.isSameCodeAs(HttpStatus.NO_CONTENT)) {
            log.info("-- Счёт успешно создан --");
            return "Счёт создан!";
        }
        log.info("-- Произошла ошибка при запросе на создание счёта для пользователя --");
        return "Упс!\nЧто-то пошло не так!";
    }
}
