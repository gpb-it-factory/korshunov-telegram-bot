package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.registerClient.RegisterClient;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public final class Register implements Command {

    private final String nameOfCommand;

    private final RegisterClient registerClient;

    public Register(RegisterClient registerClient) {
        this.nameOfCommand = "/register";
        this.registerClient = registerClient;
    }

    @Override
    public String getName() {
        return this.nameOfCommand;
    }

    public String exec(Update update) {
        try {
            var createUserDTO = new CreateUserDTO(
                    update.getMessage().getChatId(),
                    update.getMessage().getChat().getUserName());
            log.info("-- Ответ получен --");
            var response = registerClient.runRequest(createUserDTO);
            return createMessage(response);
        } catch (RestClientException error) {
            log.info("-- Сервис не доступен --");
            return "Сервис не доступен!";
        }
    }

    public String createMessage(ResponseEntity<?> response) {
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)) {
            log.info("-- Пользователь успешно зарегистрирован --");
            return "Вы успешно зарегистрированы!";
        }
        log.info("-- Произошла ошибка при запросе на регистрацию пользователя --");
        return "Упс!\nЧто-то пошло не так!";
    }
}
