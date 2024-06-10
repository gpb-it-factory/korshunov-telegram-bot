package com.gpb.minibank.service.commandMaker.commands.requestRunner;

import com.gpb.minibank.service.commandMaker.commands.dto.request.CreateUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public final class RegisterRequestRunner {

    private final RestTemplate restTemplate;
    private final String path;

    public RegisterRequestRunner(RestTemplateBuilder restTemplateBuilder,
                                 @Value("${bot.service.register.path}") String path) {
        this.restTemplate = restTemplateBuilder.build();
        this.path = path;
    }

    public ResponseEntity<String> runRequest(Update update) {
        var id = update.getMessage().getChatId();
        var userName = update.getMessage().getChat().getUserName();
        HttpEntity<CreateUserDTO> entity = new HttpEntity<>(new CreateUserDTO(id, userName));
        return restTemplate.postForEntity(path, entity, String.class);
    }
}
