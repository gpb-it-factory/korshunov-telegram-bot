package com.gpb.minibank.service.commandMaker.commands;

import com.gpb.minibank.service.commandMaker.dto.request.CreateUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Register implements Command {

    private final String path;

    public Register(@Value("${bot.service.register.path}") String path) {
        this.path = path;
    }

    public String exec(Update update) {
        try {
            var result = runRequest(update);
            return getAnswerOnRequest(result);
        } catch (HttpStatusCodeException error) {
            var result = ResponseEntity.status(error.getStatusCode()).body(error.getResponseBodyAsString());
            return getAnswerOnRequest(result);
        } catch (RestClientException error) {
            return "Сервис не доступен!";
        }
    }

    public ResponseEntity<String> runRequest(Update update) {
        var id = update.getMessage().getChatId();
        HttpEntity<CreateUserDTO> entity = new HttpEntity<>(new CreateUserDTO(id));
        return new RestTemplate().postForEntity(path, entity, String.class);
    }

    public String getAnswerOnRequest(ResponseEntity<String> response) {
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)) {
            return "Вы успешно зарегистрированы!";
        }
        return "Произошла ошибка.\n" + response.getBody();
    }
}
