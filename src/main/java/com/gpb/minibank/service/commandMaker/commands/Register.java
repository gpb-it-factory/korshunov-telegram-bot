package com.gpb.minibank.service.commandMaker.commands;

import com.gpb.minibank.service.commandMaker.dto.CreateUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Register implements Command {

    private final RestTemplate restTemplate;

    private final String path;

    public Register(RestTemplateBuilder restTemplateBuilder, @Value("${bot.service.register.path}") String path) {
        this.restTemplate = restTemplateBuilder.build();
        this.path = path;
    }

    public String exec(Update update) throws RestClientException {
        try {
            return runRequest(update);
        } catch (RestClientException error) {
            return "Что-то пошло не так!\nПопробуйте позже.";
        }
    }

    public String runRequest(Update update) {
        var id = update.getMessage().getChatId();
        HttpEntity<CreateUserDTO> entity = new HttpEntity<>(new CreateUserDTO(id));
        ResponseEntity<String> result = restTemplate.postForEntity(path, entity, String.class);
        return result.getBody();
    }
}
