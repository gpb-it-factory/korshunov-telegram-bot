package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.registerClient.RegisterClient;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

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
            var response = registerClient.runRequest(createUserDTO);
            return createMessage(response);
        } catch (HttpStatusCodeException error) {
            ResponseEntity<?> response = ResponseEntity.status(error.getStatusCode()).build();
            return createMessage(response);
        } catch (RestClientException error) {
            return "Сервис не доступен!";
        }
    }

    public String createMessage(ResponseEntity<?> response) {
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)) {
            return "Вы успешно зарегистрированы!";
        }
        return "Упс!\nЧто-то пошло не так!";
    }
}
