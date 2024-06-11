package com.gpb.minibank.service.commandMaker.commands;

import com.gpb.minibank.service.commandMaker.commands.requestRunner.RegisterRequestRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public final class Register implements Command {

    private final String nameOfCommand;

    private final RegisterRequestRunner registerRequestRunner;

    public Register(RegisterRequestRunner registerRequestRunner) {
        this.nameOfCommand = "/register";
        this.registerRequestRunner = registerRequestRunner;
    }

    @Override
    public String getName() {
        return this.nameOfCommand;
    }

    public String exec(Update update) {
        try {
            var result = registerRequestRunner.runRequest(update);
            return getAnswerOnRequest(result);
        } catch (HttpStatusCodeException error) {
            var result = ResponseEntity.status(error.getStatusCode()).body(error.getResponseBodyAsString());
            return getAnswerOnRequest(result);
        } catch (RestClientException error) {
            return "Сервис не доступен!";
        }
    }

    public String getAnswerOnRequest(ResponseEntity<String> response) {
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)) {
            return "Вы успешно зарегистрированы!";
        }
        return "Произошла ошибка.\n" + response.getBody();
    }
}
